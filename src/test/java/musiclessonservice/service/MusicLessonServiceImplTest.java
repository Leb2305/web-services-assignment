package musiclessonservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.DayOfWeek;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;
import musiclessonservice.model.InstrumentGrades;
import musiclessonservice.model.LessonSlot;
import musiclessonservice.model.Student;

public class MusicLessonServiceImplTest {

    FakePupilTracker tracker = new FakePupilTracker();

    @Test
    public void weekHas62Slots() {
        MusicLessonServiceImpl service = new MusicLessonServiceImpl(tracker);

        assertEquals(62, service.getAvailableSlots().size());
    }

    @Test
    public void slotStartTimesAndStopTimes() {
        MusicLessonServiceImpl service = new MusicLessonServiceImpl(tracker);

        assertTrue(service.getAvailableSlots().contains(new LessonSlot(DayOfWeek.MONDAY, LocalTime.of(16, 00))));
        assertTrue(service.getAvailableSlots().contains(new LessonSlot(DayOfWeek.MONDAY, LocalTime.of(19, 30))));
        assertFalse(service.getAvailableSlots().contains(new LessonSlot(DayOfWeek.MONDAY, LocalTime.of(20, 00))));
        assertFalse(service.getAvailableSlots().contains(new LessonSlot(DayOfWeek.MONDAY, LocalTime.of(15, 30))));
        assertTrue(service.getAvailableSlots().contains(new LessonSlot(DayOfWeek.SATURDAY, LocalTime.of(9, 00))));
        assertFalse(service.getAvailableSlots().contains(new LessonSlot(DayOfWeek.SUNDAY, LocalTime.of(14, 00))));

    }

    @Test
    public void bookingSlotWorks() {
        MusicLessonServiceImpl service = new MusicLessonServiceImpl(tracker);

        Student test = new Student(1001, "Test Pupil", 16);
        test.getInstruments().put("piano", new InstrumentGrades(5));
        tracker.addPupil(test);

        service.makeBooking(1001, "Test Pupil", "piano", new LessonSlot(DayOfWeek.MONDAY, LocalTime.of(16, 00)));
        assertEquals(61, service.getAvailableSlots().size());
        assertEquals(1, service.getPupilBookings(1001, "Test Pupil").size());
        assertEquals("piano", service.getPupilBookings(1001, "Test Pupil").get(0).getInstrument());
        assertEquals(new LessonSlot(DayOfWeek.MONDAY, LocalTime.of(16, 00)),
                service.getPupilBookings(1001, "Test Pupil").get(0).getSlot());
        assertThrows(ResponseStatusException.class, () -> service.makeBooking(1001, "Test Pupil", "piano",
                new LessonSlot(DayOfWeek.MONDAY, LocalTime.of(16, 00))));
        assertThrows(ResponseStatusException.class, () -> service.makeBooking(1001, "Test Pupil", "piano",
                new LessonSlot(DayOfWeek.MONDAY, LocalTime.of(17, 00))));
        service.cancelBooking(1001, "Test Pupil", DayOfWeek.MONDAY);
        assertEquals(0, service.getPupilBookings(1001, "Test Pupil").size());
    }

    @Test
    public void tutorTests(){
        MusicLessonServiceImpl service = new MusicLessonServiceImpl(tracker);

        Student test = new Student(1001, "Test Pupil", 16);
        test.getInstruments().put("piano", new InstrumentGrades(5));
        tracker.addPupil(test);

        service.makeBooking(1001, "Test Pupil", "piano", new LessonSlot(DayOfWeek.TUESDAY, LocalTime.of(16, 00)));
        service.makeBooking(1001, "Test Pupil", "piano", new LessonSlot(DayOfWeek.THURSDAY, LocalTime.of(16, 00)));
        assertEquals(1, service.getBookingsForDay(DayOfWeek.TUESDAY).size());
        assertEquals(1, service.getBookingsForDay(DayOfWeek.THURSDAY).size());

        service.blockADay(DayOfWeek.FRIDAY);
        assertEquals(8, service.getBlockedSlots().size());
        service.unblockADay(DayOfWeek.FRIDAY);
        assertEquals(0, service.getBlockedSlots().size());

        service.blockASlot(new LessonSlot(DayOfWeek.MONDAY, LocalTime.of(17, 00)));
        assertEquals(59, service.getAvailableSlots().size());
        assertEquals(1, service.getBlockedSlots().size());
        service.unblockASlot(new LessonSlot(DayOfWeek.MONDAY, LocalTime.of(17, 00)));
        assertEquals(60, service.getAvailableSlots().size());
        assertEquals(0, service.getBlockedSlots().size());

    }
}
