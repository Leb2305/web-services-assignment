package musiclessonservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.DayOfWeek;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
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
    }

    @Test
    public void bookingSameDayandSameSlot(){
        MusicLessonServiceImpl service = new MusicLessonServiceImpl(tracker);

        Student test = new Student(1001, "Test Pupil", 16);
        test.getInstruments().put("piano", new InstrumentGrades(5));
        tracker.addPupil(test);

        service.makeBooking(1001, "Test Pupil", "piano", new LessonSlot(DayOfWeek.MONDAY, LocalTime.of(16, 00)));

        assertEquals(new LessonSlot(DayOfWeek.MONDAY, LocalTime.of(16, 00)),
                service.getPupilBookings(1001, "Test Pupil").get(0).getSlot());
        ResponseStatusException response1 = assertThrows(ResponseStatusException.class, () -> service.makeBooking(1001, "Test Pupil", "piano",
                new LessonSlot(DayOfWeek.MONDAY, LocalTime.of(16, 00))));
        assertEquals(HttpStatus.CONFLICT, response1.getStatusCode());
        ResponseStatusException response2 = assertThrows(ResponseStatusException.class, () -> service.makeBooking(1001, "Test Pupil", "piano",
                new LessonSlot(DayOfWeek.MONDAY, LocalTime.of(17, 00))));
        assertEquals(HttpStatus.CONFLICT, response2.getStatusCode());
    }

    @Test 
    public void bookingLessonPast6pmTooYoung(){
        MusicLessonServiceImpl service = new MusicLessonServiceImpl(tracker);

        Student test = new Student(1001, "Test Pupil", 12);
        test.getInstruments().put("piano", new InstrumentGrades(3));
        tracker.addPupil(test);

        Student test2 = new Student(1002, "New Pupil", 17);
        test2.getInstruments().put("piano", new InstrumentGrades(6));
        tracker.addPupil(test2);

        service.makeBooking(1001, "Test Pupil", "piano", new LessonSlot(DayOfWeek.FRIDAY, LocalTime.of(16, 00)));
        assertEquals(1, service.getBookingsForDay(DayOfWeek.FRIDAY).size());

        ResponseStatusException response1 = assertThrows(ResponseStatusException.class, () -> service.makeBooking(1001, "Test Pupil", "piano", new LessonSlot(DayOfWeek.SATURDAY, LocalTime.of(18, 00))));
        assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode());
        ResponseStatusException response2 = assertThrows(ResponseStatusException.class, () -> service.makeBooking(1001, "Test Pupil", "piano", new LessonSlot(DayOfWeek.SATURDAY, LocalTime.of(18, 30))));
        assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
        ResponseStatusException response3 = assertThrows(ResponseStatusException.class, () -> service.makeBooking(1001, "Test Pupil", "piano", new LessonSlot(DayOfWeek.SATURDAY, LocalTime.of(19, 00))));
        assertEquals(HttpStatus.BAD_REQUEST, response3.getStatusCode());
        ResponseStatusException response4 = assertThrows(ResponseStatusException.class, () -> service.makeBooking(1001, "Test Pupil", "piano", new LessonSlot(DayOfWeek.SATURDAY, LocalTime.of(19, 30))));
        assertEquals(HttpStatus.BAD_REQUEST, response4.getStatusCode());
        ResponseStatusException response5 = assertThrows(ResponseStatusException.class, () -> service.makeBooking(1001, "Test Pupil", "piano", new LessonSlot(DayOfWeek.SATURDAY, LocalTime.of(20, 00))));
        assertEquals(HttpStatus.CONFLICT, response5.getStatusCode());

        service.makeBooking(1002, "New Pupil", "piano", new LessonSlot(DayOfWeek.SATURDAY, LocalTime.of(18, 00)));
        assertEquals(1, service.getBookingsForDay(DayOfWeek.SATURDAY).size());
    }

    @Test 
    public void bookingWithInsufficientGrade(){
        MusicLessonServiceImpl service = new MusicLessonServiceImpl(tracker);

        Student test = new Student(1001, "Test Pupil", 17);
        test.getInstruments().put("piano", new InstrumentGrades(2));
        tracker.addPupil(test);

        Student test2 = new Student(1002, "Other Pupil", 11);
        test.getInstruments().put("piano", new InstrumentGrades(8));
        tracker.addPupil(test2);

        ResponseStatusException response1 = assertThrows(ResponseStatusException.class, () -> service.makeBooking(1001, "Test Pupil", "piano", new LessonSlot(DayOfWeek.FRIDAY, LocalTime.of(16, 00))));
        assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode());
        ResponseStatusException response2 = assertThrows(ResponseStatusException.class, () -> service.makeBooking(1002, "Other Pupil", "piano", new LessonSlot(DayOfWeek.FRIDAY, LocalTime.of(16, 00))));
        assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
    }

    @Test
    public void bookingWithInstrumentTutorDoesntTeach(){
        MusicLessonServiceImpl service = new MusicLessonServiceImpl(tracker);

        Student test = new Student(1001, "Test Pupil", 17);
        test.getInstruments().put("keytar", new InstrumentGrades(2));
        tracker.addPupil(test);

        ResponseStatusException response = assertThrows(ResponseStatusException.class, () -> service.makeBooking(1001, "Test Pupil", "keytar", new LessonSlot(DayOfWeek.FRIDAY, LocalTime.of(16, 00))));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test 
    public void bookingWithWrongMatricRightName(){
        MusicLessonServiceImpl service = new MusicLessonServiceImpl(tracker);

        Student test = new Student(1001, "Test Pupil", 17);
        test.getInstruments().put("piano", new InstrumentGrades(2));
        tracker.addPupil(test);

        ResponseStatusException response = assertThrows(ResponseStatusException.class, () -> service.makeBooking(1000, "Test Pupil", "piano", new LessonSlot(DayOfWeek.FRIDAY, LocalTime.of(17, 00))));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void bookingWithRightMatricWrongName(){
        MusicLessonServiceImpl service = new MusicLessonServiceImpl(tracker);

        Student test = new Student(1001, "Test Pupil", 17);
        test.getInstruments().put("piano", new InstrumentGrades(2));
        tracker.addPupil(test);

        ResponseStatusException response = assertThrows(ResponseStatusException.class, () -> service.makeBooking(1001, "John Doe", "piano", new LessonSlot(DayOfWeek.FRIDAY, LocalTime.of(16, 30))));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void cancelingBookedLesson(){
        MusicLessonServiceImpl service = new MusicLessonServiceImpl(tracker);

        Student test = new Student(1001, "Test Pupil", 16);
        test.getInstruments().put("piano", new InstrumentGrades(5));
        tracker.addPupil(test);

        service.makeBooking(1001, "Test Pupil", "piano", new LessonSlot(DayOfWeek.MONDAY, LocalTime.of(16, 00)));

        service.cancelBooking(1001, "Test Pupil", DayOfWeek.MONDAY);
        assertEquals(0, service.getPupilBookings(1001, "Test Pupil").size());
        assertEquals(62, service.getAvailableSlots().size());

        assertNull(service.cancelBooking(1001, "Test Pupil", DayOfWeek.MONDAY));
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

        service.blockADay(DayOfWeek.TUESDAY);
        assertEquals(1, service.getBookingsForDay(DayOfWeek.TUESDAY).size());
        assertEquals(2, service.getPupilBookings(1001, "Test Pupil").size());
        service.unblockADay(DayOfWeek.TUESDAY);

        service.blockASlot(new LessonSlot(DayOfWeek.THURSDAY, LocalTime.of(16, 00)));
        assertEquals(1, service.getBlockedSlots().size());
        assertEquals(2, service.getPupilBookings(1001, "Test Pupil").size());
        service.unblockASlot(new LessonSlot(DayOfWeek.THURSDAY, LocalTime.of(16, 00)));

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
