package musiclessonservice.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TutorTest {

    @Test 
    public void teachesWithPianoRange(){
        Tutor tutor = new Tutor("Bob Dylan");
        InstrumentGrades pianoRange = new InstrumentGrades(3,7);

        tutor.getInstruments().put("piano", pianoRange);

        assertFalse(tutor.teaches("piano", 2));
        assertTrue(tutor.teaches("piano", 3));
        assertTrue(tutor.teaches("piano", 4));
        assertTrue(tutor.teaches("piano", 5));
        assertTrue(tutor.teaches("piano", 6));
        assertTrue(tutor.teaches("piano", 7));
        assertFalse(tutor.teaches("piano", 8));
        assertFalse(tutor.teaches("trumpet", 5));

    }


}
