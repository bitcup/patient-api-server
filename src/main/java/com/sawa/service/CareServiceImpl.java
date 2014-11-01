package com.sawa.service;

import com.google.common.collect.Lists;
import com.sawa.entity.Care;
import com.sawa.entity.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * User: omar
 */
public class CareServiceImpl implements CareService {

    private static final Logger logger = LoggerFactory.getLogger(CareServiceImpl.class);

    private Map<String, List<Care>> caresByPatientId = new HashMap<String, List<Care>>();

    public CareServiceImpl() {
        Care care = new Care();
        care.setId("111");
        care.setFacilityName("MGH");
        care.setLocation("Boston");
        care.setReason("Heart attack");
        care.setStart(new Date());
        care.setEnd(new Date());
        care.setOvernight(true);
        Patient patient = new Patient();
        patient.setId("abc-123");
        patient.setName("Joe");
        care.setPatient(patient);
        List<Care> init = Lists.newArrayList(care);
        caresByPatientId.put("abc-123", init);
    }

    @Override
    public void saveCare(Care care) throws Exception {
        final String patientId = care.getPatient().getId();
        if (caresByPatientId.get(patientId) == null) {
            caresByPatientId.put(patientId, new ArrayList<Care>());
        }
        caresByPatientId.get(patientId).add(care);
        logger.info("saved care: " + care);
    }

    @Override
    public List<Care> getCares(Patient loggedInPatient) throws Exception {
        return caresByPatientId.get(loggedInPatient.getId());
    }

    @Override
    public Care getCare(Patient loggedInPatient, String id) throws Exception {
        List<Care> patientCares = caresByPatientId.get(loggedInPatient.getId());
        for (Care care : patientCares) {
            if (care.getId().equals(id)) {
                return care;
            }
        }
        return null;
    }

    @Override
    public void updateCare(Patient loggedInPatient, Care care) throws Exception {
        List<Care> patientCares = getCares(loggedInPatient);
        Care toUpdate = null;
        for (Care c: patientCares) {
            if (c.getId().equals(care.getId())) {
                toUpdate = c;
                break;
            }
        }
        if (toUpdate != null) {
            toUpdate.setStart(care.getStart());
            toUpdate.setEnd(care.getEnd());
            toUpdate.setFacilityName(care.getFacilityName());
            toUpdate.setNote(care.getNote());
            toUpdate.setReason(care.getReason());
            toUpdate.setLocation(care.getLocation());
            toUpdate.setOvernight(care.isOvernight());
        } else {
            throw new Exception("cannot find Care to update");
        }
    }

    @Override
    public void deleteCare(Patient loggedInPatient, String id) throws Exception {
        List<Care> patientCares = getCares(loggedInPatient);
        Care toDelete = null;
        for (Care care : patientCares) {
            if (care.getId().equals(id)) {
                toDelete = care;
            }
        }
        if (toDelete != null) {
            patientCares.remove(toDelete);
        }
    }

}
