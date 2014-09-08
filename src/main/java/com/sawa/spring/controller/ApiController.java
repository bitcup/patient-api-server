package com.sawa.spring.controller;

import com.sawa.Utils;
import com.sawa.entity.Care;
import com.sawa.entity.Note;
import com.sawa.entity.Patient;
import com.sawa.entity.Practitioner;
import com.sawa.service.CareService;
import com.sawa.service.NoteService;
import com.sawa.service.PractitionerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

// todo: breakup into multiple controllers

@Controller
@RequestMapping(value = {"api"})
public class ApiController {

    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private CareService careService;

    @Autowired
    private PractitionerService practitionerService;

    @Autowired
    private NoteService noteService;

    // -------------------------------------------
    // CARE
    // -------------------------------------------
    @RequestMapping(value = "/v1.0/cares", method = RequestMethod.POST)
    @ResponseBody
    public void createCare(@RequestBody Care care, HttpServletRequest request) throws Exception {
        // populate id and Patient objects
        care.setId(UUID.randomUUID().toString());
        Patient loggedInPatient = getPatient(request);
        care.setPatient(loggedInPatient);
        logger.info("inside createCare - care to save: " + care);
        careService.saveCare(care);
    }

    @RequestMapping(value = {"/v1.0/cares"}, method = RequestMethod.GET)
    @ResponseBody
    public List<Care> getCareList(HttpServletRequest request) throws Exception {
        Patient loggedInPatient = getPatient(request);
        logger.info("inside getCareList - patient: " + loggedInPatient);
        return careService.getCares(loggedInPatient);
    }

    @RequestMapping(value = {"/v1.0/cares/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    public Care getCare(@PathVariable String id, HttpServletRequest request) throws Exception {
        Patient loggedInPatient = getPatient(request);
        logger.info("inside getCare - care id: " + id + " - patient: " + loggedInPatient);
        return careService.getCare(loggedInPatient, id);
    }

    @RequestMapping(value = "/v1.0/cares", method = RequestMethod.PUT)
    @ResponseBody
    public void updateCare(@RequestBody Care care, HttpServletRequest request) throws Exception {
        logger.info("inside updateCare - care to update: " + care);
        careService.updateCare(getPatient(request), care);
    }

    @RequestMapping(value = {"/v1.0/cares/{id}"}, method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteCare(@PathVariable String id, HttpServletRequest request) throws Exception {
        Patient loggedInPatient = getPatient(request);
        logger.info("inside deleteCare - care id: " + id + " - patient: " + loggedInPatient);
        careService.deleteCare(loggedInPatient, id);
    }

    // -------------------------------------------
    // PRACTITIONER
    // -------------------------------------------
    @RequestMapping(value = "/v1.0/practitioners/care/{id}", method = RequestMethod.POST)
    @ResponseBody
    public void createPractitioner(@RequestBody Practitioner practitioner,
                                   @PathVariable String id, HttpServletRequest request) throws Exception {
        // populate id and Care objects
        practitioner.setId(UUID.randomUUID().toString());
        Care care = getCare(id, request);
        practitioner.setCare(care);
        logger.info("inside createPractitioner - practitioner to save: " + practitioner);
        practitionerService.savePractitioner(practitioner);
    }

    @RequestMapping(value = {"/v1.0/practitioners/care/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    public List<Practitioner> getPractitionerList(@PathVariable String id, HttpServletRequest request) throws Exception {
        Care care = getCare(id, request);
        logger.info("inside getPractitionerList - care: " + care);
        return practitionerService.getPractitioners(care);
    }

    @RequestMapping(value = {"/v1.0/practitioners/{pId}"}, method = RequestMethod.GET)
    @ResponseBody
    public Practitioner getPractitioner(@PathVariable String pId,
                                        HttpServletRequest request) throws Exception {
        Patient loggedInPatient = getPatient(request);
        logger.info("inside getPractitioner - pId: " + pId + " - patient: " + loggedInPatient);
        return practitionerService.getPractitionerById(loggedInPatient, pId);
    }

    @RequestMapping(value = "/v1.0/practitioners", method = RequestMethod.PUT)
    @ResponseBody
    public void updatePractitioner(@RequestBody Practitioner practitioner, HttpServletRequest request) throws Exception {
        Patient loggedInPatient = getPatient(request);
        logger.info("inside updatePractitioner - practitioner: " + practitioner + " - patient: " + loggedInPatient);
        practitionerService.updatePractitioner(loggedInPatient, practitioner);
    }

    @RequestMapping(value = {"/v1.0/practitioners/{id}"}, method = RequestMethod.DELETE)
    @ResponseBody
    public void deletePractitioner(@PathVariable String id, HttpServletRequest request) throws Exception {
        Patient loggedInPatient = getPatient(request);
        logger.info("inside deletePractitioner - id: " + id + " - patient: " + loggedInPatient);
        practitionerService.deletePractitioner(loggedInPatient, id);
    }

    @RequestMapping(value = {"/v1.0/practitioners"}, method = RequestMethod.GET)
    @ResponseBody
    public List<Practitioner> getPractitionersForPatient(HttpServletRequest request) throws Exception {
        Patient loggedInPatient = getPatient(request);
        logger.info("inside getPractitionersForPatient - patient: " + loggedInPatient);
        return practitionerService.getPractitionersForPatient(loggedInPatient);
    }

    // -------------------------------------------
    // NOTE
    // -------------------------------------------
    @RequestMapping(value = "/v1.0/notes/practitioner/{id}", method = RequestMethod.POST)
    @ResponseBody
    public void createNote(@RequestBody Note note,
                           @PathVariable String id, HttpServletRequest request) throws Exception {
        // populate id and Care objects
        note.setId(UUID.randomUUID().toString());
        Patient loggedInPatient = getPatient(request);
        Practitioner practitioner = practitionerService.getPractitionerById(loggedInPatient, id);
        note.setPractitioner(practitioner);
        logger.info("inside createNote - patient: " + loggedInPatient + " - note: " + note);
        noteService.saveNote(note);
    }

    @RequestMapping(value = {"/v1.0/notes/practitioner/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    public List<Note> getNoteList(@PathVariable String id, HttpServletRequest request) throws Exception {
        Patient loggedInPatient = getPatient(request);
        Practitioner practitioner = practitionerService.getPractitionerById(loggedInPatient, id);
        logger.info("inside getNoteList - patient: " + loggedInPatient + " - practitioner: " + practitioner);
        return noteService.getNotes(practitioner);
    }

    @RequestMapping(value = {"/v1.0/notes/{nId}"}, method = RequestMethod.GET)
    @ResponseBody
    public Note getNote(@PathVariable String nId, HttpServletRequest request) throws Exception {
        Patient loggedInPatient = getPatient(request);
        logger.info("inside getNote - nId: " + nId + ", patient: " + loggedInPatient);
        return noteService.getNoteById(loggedInPatient, nId);
    }

    @RequestMapping(value = "/v1.0/notes", method = RequestMethod.PUT)
    @ResponseBody
    public void updateNote(@RequestBody Note note, HttpServletRequest request) throws Exception {
        Patient loggedInPatient = getPatient(request);
        logger.info("inside updateNote - note: " + note + ", patient: " + loggedInPatient);
        noteService.updateNote(loggedInPatient, note);
    }

    @RequestMapping(value = {"/v1.0/notes/{id}"}, method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteNote(@PathVariable String id, HttpServletRequest request) throws Exception {
        Patient loggedInPatient = getPatient(request);
        logger.info("inside deleteNote - id: " + id + " - patient: " + loggedInPatient);
        noteService.deleteNote(loggedInPatient, id);
    }

    private Patient getPatient(HttpServletRequest request) {
        return (Patient) request.getAttribute(Utils.SAWA_PATIENT_ATTR);
    }

}
