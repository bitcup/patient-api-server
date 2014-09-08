package com.sawa.service;

import com.sawa.entity.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * User: omar
 */
public class PatientServiceImpl implements PatientService {

    private static final Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);

    private Map<String, Patient> patientsByEmail = new HashMap<String, Patient>();

    @Override
    public Patient getByEmail(String email) {
        return patientsByEmail.get(email);
    }

    public PatientServiceImpl() {
        Patient testPatient = new Patient();
        testPatient.setId(UUID.randomUUID().toString());
        testPatient.setName("Joe Patient");
        testPatient.setEmail("joe@test.com");
        testPatient.setPrivateAuthKey("1234567890");
        testPatient.setPassword("test");
        patientsByEmail.put(testPatient.getEmail(), testPatient);
        logger.info("test user created");
    }
}
