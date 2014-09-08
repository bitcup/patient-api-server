package com.sawa.service;

import com.sawa.entity.Patient;

/**
 * User: omar
 */
public interface PatientService {

    public Patient getByEmail(String email);
}
