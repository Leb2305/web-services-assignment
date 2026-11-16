package musiclessonservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import musiclessonservice.model.LessonSlot;

public class MusicLessonServiceImplTest {

    @Test 
    public void weekHas62Slots(){
        MusicLessonServiceImpl service = new MusicLessonServiceImpl();

        assertEquals(62, service.getAvailableSlots().size());
    }

    @Test
    public void slotStartTimesAndStopTimes(){
        MusicLessonServiceImpl service = new MusicLessonServiceImpl();
        
        assertTrue(service.getAvailableSlots().contains(new LessonSlot(DayOfWeek.MONDAY,LocalTime.of(16, 00))));
        assertTrue(service.getAvailableSlots().contains(new LessonSlot(DayOfWeek.MONDAY,LocalTime.of(19, 30))));
        assertFalse(service.getAvailableSlots().contains(new LessonSlot(DayOfWeek.MONDAY,LocalTime.of(20, 00))));
        assertFalse(service.getAvailableSlots().contains(new LessonSlot(DayOfWeek.MONDAY,LocalTime.of(15, 30))));
        assertTrue(service.getAvailableSlots().contains(new LessonSlot(DayOfWeek.SATURDAY,LocalTime.of(9, 00))));
        assertFalse(service.getAvailableSlots().contains(new LessonSlot(DayOfWeek.SUNDAY,LocalTime.of(14, 00))));

    }
}
