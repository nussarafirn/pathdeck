package edu.tacoma.uw.finalproject.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

public class NoteTest {
    Note note = new Note("", "Emily", "user",
            "1234567890", "E@uw.edu", "08/17/2020", "Tacoma");
    //Note(String mNoteId, String mNoteWho, String username, String mNotePhone, String mNoteEmail, String mNoteDate, String mNoteLocation)
    @Test
    public void testNoteConstructor(){
        assertNotNull(new Note("", "Emily", "user",
                "1234567890", "E@uw.edu", "08/17/2020", "Tacoma"));

    }

    @Test
    public void testBadNoteConstructor(){
        try{
            new Note("", "Emily", "",
                    "12345678900", "E@uw.edu", "08/17/2020", "Tacoma");
            new Note("", "Emily", "",
                    "1234567890", "Euw.edu", "08/17/2020", "Tacoma");
            new Note("", "", "user",
                    "1234567890", "E@uw.edu", "08/17/2020", "Tacoma");
            new Note("", "Emily", "user",
                    "12345678900", "E@uw.edu", "08/17/2020", "");



        }catch (IllegalArgumentException e){

        }

    }

    @Test
    public void parseNoteJson() {
    }

    @Test
    public void testGetNoteId() {
        assertEquals("", note.getNoteId());
    }

    @Test
    public void testGetNoteWho() {
        assertEquals("emily", note.getNoteWho());
    }

    @Test
    public void testGetUsername() {
        assertEquals("user", note.getUsername());
    }

    @Test
    public void testGetNotePhone() {
        assertEquals("1234567890", note.getNotePhone());
    }

    @Test
    public void testGetNoteEmail() {
        assertEquals("E@uw.edu", note.getNoteEmail());
        assertNotEquals("e@uw.edu", note.getNoteEmail());

    }

    @Test
    public void testGetNoteDate() {
        assertEquals("08/18/2020", note.getNoteDate());
        assertNotEquals("08/18/2020", note.getNoteDate());
    }

    @Test
    public void testGetNoteLocation() {
        assertEquals("Tacoma", note.getNoteLocation());
        assertNotEquals("tacoma", note.getNoteLocation());

    }

    @Test
    public void testSetNoteId() {
        note.setNoteId("1");
        assertEquals("1", note.getNoteId());
    }

    @Test
    public void testNullNoteWho() {
        try{
            note.setNoteWho("");
        }catch (IllegalArgumentException e){
            String message = "name cannot be empty";
            assertEquals(message, e.getMessage());
        }
    }
    @Test
    public void testSetNoteWho() {
        note.setNoteWho("Kieu");
        assertEquals("Kieu", note.getNoteWho());
        note.setNoteWho("K");
        assertEquals("K", note.getNoteWho());
        note.setNoteWho("K@#");
        assertEquals("K@#", note.getNoteWho());
        note.setNoteWho("K124");
        assertEquals("K123", note.getNoteWho());
    }

    @Test
    public void testInvalidSetPhone() {
        List<String> test = new ArrayList<String>();
        test.add("206-333-7890");
        test.add("20633378900");
        test.add("2063337");
        test.add("(206)-333-7890");
        for (String each : test){
            try{
                note.setNotePhone(each);

            }catch (IllegalArgumentException e){
                String message = "Enter 10 digits number format XXXXXXXXXX";
                assertEquals(message, e.getMessage());
            }
        }

    }
    public void testValidSetPhone() {
        note.setNotePhone("5555666784");
        assertEquals("5555666784", note.getNotePhone());

    }

    @Test
    public void testInvalidNoteEmail() {
        try{
            note.setNotePhone("K.uw.edu");

        }catch (IllegalArgumentException e){
            String message = "Invalid Email";
            assertEquals(message, e.getMessage());
        }
    }
    @Test
    public void testValidNoteEmail() {
        List<String> test = new ArrayList<String>();
        test.add("uw@gmail.com");
        test.add("uw123@gmail.com");
        test.add("u.w!@gmail.com");
        test.add("uwwwww12345!#@gmail.com");
        test.add("uwww-ww12345!#@gmail.com");
        for(String each : test){
            note.setNoteEmail(each);
            assertEquals(each, note.getNoteEmail());
        }
    }
    @Test
    public void testSetNoteDate() {
        note.setNoteDate("08-18-2020");
        assertEquals("08-18-2020", note.getNoteDate());
    }

    @Test
    public void testInvalidSetLocation() {
        try {
            note.setNoteLocation("");
        }catch (IllegalArgumentException e){
            String message = "location cannot be empty";
            assertEquals(message, note.getNoteLocation());
        }
    }

    @Test
    public void testValidSetLocation() {
        List<String> test = new ArrayList<String>();
        test.add("Seattle");
        test.add("11522 SE 253rd");
        test.add("Forever21");
        for(String each : test){
            note.setNoteEmail(each);
            assertEquals(each, note.getNoteEmail());
        }
    }
}