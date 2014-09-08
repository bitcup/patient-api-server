package com.sawa.service;

import com.sawa.entity.Care;
import com.sawa.entity.Patient;

import java.util.List;

/**
 * User: omar
 */
public interface CareService {

    public void saveCare(Care care) throws Exception;

    public List<Care> getCares(Patient loggedInPatient) throws Exception;

    public Care getCare(Patient loggedInPatient, String id) throws Exception;

    public void updateCare(Patient loggedInPatient, Care care) throws Exception;

    public void deleteCare(Patient loggedInPatient, String id) throws Exception;
}
