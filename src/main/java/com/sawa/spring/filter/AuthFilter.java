package com.sawa.spring.filter;

import com.sawa.Utils;
import com.sawa.entity.Patient;
import com.sawa.service.PatientService;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("authFilter")
public class AuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Autowired
    private PatientService patientService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (request.getMethod().equalsIgnoreCase("OPTIONS") || request.getRequestURI().equals("/login")) {
            logger.info("ignoring auth");
            filterChain.doFilter(request, response);
        } else {
            String[] info = Utils.getPatientInfoFromRequest(request);
            if (info != null) {
                String email = info[0];
                String signature = info[1];
                // lookup patient by email
                Patient patient = getPatient(email);
                if (patient != null && patient.getPrivateAuthKey() != null) {
                    logger.info("patient is: " + patient.getName() + " - private key: " + patient.getPrivateAuthKey());
                    // calculate signature
                    // todo: include POST/PUT payload in signature calculation
                    String uri = request.getRequestURI();
                    logger.info("uri: " + uri);
                    String calculatedSig = hmacSha1(uri, patient.getPrivateAuthKey());
                    if (calculatedSig.equals(signature)) {
                        logger.info("SUCCESS - calculatedSig: " + calculatedSig + " matches signature");
                        request.setAttribute(Utils.SAWA_PATIENT_ATTR, patient);
                        filterChain.doFilter(request, response);
                    } else {
                        logger.info("FAILURE - calculatedSig (" + calculatedSig + ") does not match passed" +
                                " signature (" + signature + ") for request: " + request.getRequestURI());
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    }
                }
            } else {
                logger.info("unauthorized request: " + request.toString());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
    }

    private Patient getPatient(String userId) {
        final Patient patient = patientService.getByEmail(userId);
        if (patient != null) {
            return patient;
        }
        return null;
    }

    public static String hmacSha1(String value, String key) {
        try {
            // Get an hmac_sha1 key from the raw key bytes
            byte[] keyBytes = key.getBytes();
            SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");

            // Get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);

            // Compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(value.getBytes());

            // Convert raw bytes to Hex
            byte[] hexBytes = new Hex().encode(rawHmac);

            //  Covert array of Hex bytes to a String
            return new String(hexBytes, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
