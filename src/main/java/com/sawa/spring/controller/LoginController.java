package com.sawa.spring.controller;

import com.sawa.entity.Patient;
import com.sawa.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private PatientService patientService;

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    @ResponseBody
    public Patient login(@RequestBody Patient user, HttpServletResponse response) throws Exception {
        // simulate delay to test UI loader/spinner
        //Thread.sleep(2000);
        logger.info("inside login - user: " + user);
        Patient patient = patientService.getByEmail(user.getEmail());
        if (patient != null) {
            return patient;
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return null;
    }
}
