package com.sawa.service;

import com.sawa.entity.Care;
import com.sawa.entity.Patient;
import com.sawa.entity.Practitioner;

import java.util.List;

/**
 * User: omar
 */
public interface PractitionerService {

    public void savePractitioner(Practitioner practitioner) throws Exception;

    public List<Practitioner> getPractitioners(Care care) throws Exception;

    public Practitioner getPractitionerById(Patient loggedInPatient, String id) throws Exception;

    public void updatePractitioner(Patient loggedInPatient, Practitioner practitioner) throws Exception;

    public void deletePractitioner(Patient loggedInPatient, String id) throws Exception;

    public List<Practitioner> getPractitionersForPatient(Patient loggedInPatient) throws Exception;

}
