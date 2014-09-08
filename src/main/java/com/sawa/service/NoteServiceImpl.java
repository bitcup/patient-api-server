package com.sawa.service;

import com.sawa.entity.Note;
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
public class NoteServiceImpl implements NoteService {

    private static final Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

    private Map<String, List<Note>> notesByPractitionerId = new HashMap<String, List<Note>>();

    @Override
    public void saveNote(Note note) throws Exception {
        final String practitionerId = note.getPractitioner().getId();
        if (notesByPractitionerId.get(practitionerId) == null) {
            notesByPractitionerId.put(practitionerId, new ArrayList<Note>());
        }
        notesByPractitionerId.get(practitionerId).add(note);
        logger.info("saved note: " + note);
    }

    @Override
    public List<Note> getNotes(Practitioner practitioner) throws Exception {
        return notesByPractitionerId.get(practitioner.getId());
    }

    @Override
    public Note getNoteById(Patient loggedInPatient, String id) throws Exception {
        for (String pId : notesByPractitionerId.keySet()) {
            for (Note n : notesByPractitionerId.get(pId)) {
                if (n.getId().equals(id) && n.getPractitioner().getCare().getPatient().getId().equals(loggedInPatient.getId())) {
                    return n;
                }
            }
        }
        return null;
    }

    @Override
    public void updateNote(Patient loggedInPatient, Note note) throws Exception {
        Note toUpdate = getNoteById(loggedInPatient, note.getId());
        if (toUpdate != null) {
            toUpdate.setContent(note.getContent());
        } else {
            throw new Exception("cannot find Note to update");
        }
    }

    @Override
    public void deleteNote(Patient loggedInPatient, String id) throws Exception {
        Note toDelete = null;
        for (String pId : notesByPractitionerId.keySet()) {
            for (Note n : notesByPractitionerId.get(pId)) {
                if (n.getId().equals(id) && n.getPractitioner().getCare().getPatient().getId().equals(loggedInPatient.getId())) {
                    toDelete = n;
                    break;
                }
            }
            if (toDelete != null) {
                notesByPractitionerId.get(pId).remove(toDelete);
                return;
            }
        }
    }
}
