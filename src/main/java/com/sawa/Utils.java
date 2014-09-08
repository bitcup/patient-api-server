package com.sawa;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * User: omar
 */
public abstract class Utils {

    public static final String SAWA_PATIENT_ATTR = "sawa.patient";
    private static final String SAWA_AUTH_HEADER_PREFIX = "SAWA ";

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    public static String[] getPatientInfoFromRequest(HttpServletRequest request) {
        String[] info = null;
        String auth = request.getHeader("Authorization");
        if (logger.isTraceEnabled()) {
            logger.trace("'Authorization' header: " + auth);
        }
        if (auth != null && auth.length() > 0 && auth.startsWith(SAWA_AUTH_HEADER_PREFIX)) {
            String idAndSig = auth.substring(SAWA_AUTH_HEADER_PREFIX.length());
            if (logger.isTraceEnabled()) {
                logger.trace("idAndSig: " + idAndSig);
            }
            String[] pieces = StringUtils.split(idAndSig, ":");
            if (pieces.length == 2) {
                String userId = pieces[0];
                String signature = pieces[1];
                if (userId != null && userId.length() > 0 && signature != null && signature.length() > 0) {
                    if (logger.isTraceEnabled()) {
                        logger.trace("userId: " + userId);
                        logger.trace("signature: " + signature);
                    }
                    info = new String[2];
                    info[0] = userId;
                    info[1] = signature;
                }
            }
        }
        return info;
    }

}
