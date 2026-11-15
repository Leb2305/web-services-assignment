package musiclessonservice.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.*;



public class LessonSlotTest {

    @Test
    public void sixPmCheck(){
        LessonSlot slot1 = new LessonSlot(DayOfWeek.TUESDAY, LocalTime.of(17,30));
        LessonSlot slot2 = new LessonSlot(DayOfWeek.TUESDAY, LocalTime.of(18,00));
        LessonSlot slot3 = new LessonSlot(DayOfWeek.TUESDAY, LocalTime.of(18,30));

        assertFalse(slot1.isSixPmOrLater());
        assertTrue(slot2.isSixPmOrLater());
        assertTrue(slot3.isSixPmOrLater());
    }

    @Test 
    public void sameDayAndTimeAreEqual(){
        LessonSlot slot1 = new LessonSlot(DayOfWeek.MONDAY, LocalTime.of(16, 00));
        LessonSlot slot2 = new LessonSlot(DayOfWeek.MONDAY, LocalTime.of(16, 00));
        LessonSlot slot3 = new LessonSlot(DayOfWeek.TUESDAY, LocalTime.of(16, 00));
        LessonSlot slot4 = new LessonSlot(DayOfWeek.MONDAY, LocalTime.of(13, 30));

        assertEquals(slot1,slot2);
        assertNotEquals(slot1, slot4);
        assertNotEquals(slot1, slot3);
        assertNotEquals(slot2, slot3);

    }

}
