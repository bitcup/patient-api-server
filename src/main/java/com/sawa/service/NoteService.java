package com.sawa.service;

import com.sawa.entity.Note;
import com.sawa.entity.Patient;
import com.sawa.entity.Practitioner;

import java.util.List;

/**
 * User: omar
 */
public interface NoteService {

    public void saveNote(Note note) throws Exception;

    public List<Note> getNotes(Practitioner practitioner) throws Exception;

    public Note getNoteById(Patient loggedInPatient, String id) throws Exception;

    public void updateNote(Patient loggedInPatient, Note note) throws Exception;

    public void deleteNote(Patient loggedInPatient, String id) throws Exception;

}
