package musiclessonservice.service;

import org.junit.jupiter.api.Test;
import musiclessonservice.model.Student;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PupilTrackerImplTest {

    @Test 
    public void checkImplWorks(){
        PupilTrackerImpl tracker = new PupilTrackerImpl();
        Student newStudent = tracker.getPupil(875029);
        assertNotNull(newStudent);
        assertEquals(newStudent.getName(), "Brian Stewart");
    }

}
