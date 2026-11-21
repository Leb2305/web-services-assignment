package musiclessonservice.service;

import org.junit.jupiter.api.Test;
import musiclessonservice.model.Student;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Disabled;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PupilTrackerImplTest {

   @Disabled 
    @Test 
    public void checkImplWorks(){
       PupilTrackerImpl tracker = new PupilTrackerImpl();
       Student newStudent = tracker.getPupil(440019);
       assertNotNull(newStudent);
       assertEquals("Kimberly Scott",newStudent.getName());
    }

}
