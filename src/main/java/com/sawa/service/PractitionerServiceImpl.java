package com.sawa.service;

import com.sawa.entity.Care;
import com.sawa.entity.Patient;
import com.sawa.entity.Practitioner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: omar
 */
public class PractitionerServiceImpl implements PractitionerService {

    private static final Logger logger = LoggerFactory.getLogger(PractitionerServiceImpl.class);

    private Map<String, List<Practitioner>> practitionersByCareId = new HashMap<String, List<Practitioner>>();

    @Override
    public void savePractitioner(Practitioner practitioner) throws Exception {
        final String careId = practitioner.getCare().getId();
        if (practitionersByCareId.get(careId) == null) {
            practitionersByCareId.put(careId, new ArrayList<Practitioner>());
        }
        practitionersByCareId.get(careId).add(practitioner);
        logger.info("saved practitioner: " + practitioner);
    }

    @Override
    public List<Practitioner> getPractitioners(Care care) throws Exception {
        return practitionersByCareId.get(care.getId());
    }

    @Override
    public Practitioner getPractitionerById(Patient loggedIn, String id) throws Exception {
        for (String cId : practitionersByCareId.keySet()) {
            for (Practitioner practitioner : practitionersByCareId.get(cId)) {
                if (practitioner.getId().equals(id) &&
                        practitioner.getCare().getPatient().getId().equals(loggedIn.getId())) {
                    return practitioner;
                }
            }
        }
        return null;
    }

    @Override
    public void updatePractitioner(Patient loggedInPatient, Practitioner practitioner) throws Exception {
        Practitioner toUpdate = getPractitionerById(loggedInPatient, practitioner.getId());
        if (toUpdate != null) {
            toUpdate.setName(practitioner.getName());
            toUpdate.setPhone(practitioner.getPhone());
            toUpdate.setEmail(practitioner.getEmail());
            toUpdate.setSpecialty(practitioner.getSpecialty());
        } else {
            throw new Exception("cannot find Practitioner to update");
        }
    }

    @Override
    public void deletePractitioner(Patient loggedInPatient, String id) throws Exception {
        Practitioner toDelete = null;
        for (String cId : practitionersByCareId.keySet()) {
            for (Practitioner practitioner : practitionersByCareId.get(cId)) {
                if (practitioner.getId().equals(id) &&
                        practitioner.getCare().getPatient().getId().equals(loggedInPatient.getId())) {
                    toDelete = practitioner;
                    break;
                }
            }
            if (toDelete != null) {
                practitionersByCareId.get(cId).remove(toDelete);
                return;
            }
        }
    }

    @Override
    public List<Practitioner> getPractitionersForPatient(Patient loggedInPatient) throws Exception {
        List<Practitioner> list = new ArrayList<Practitioner>();
        for (String cId : practitionersByCareId.keySet()) {
            for (Practitioner p : practitionersByCareId.get(cId)) {
                if (p.getCare().getPatient().getId().equals(loggedInPatient.getId())) {
                    list.add(p);
                }
            }
        }
        return list;
    }
}
