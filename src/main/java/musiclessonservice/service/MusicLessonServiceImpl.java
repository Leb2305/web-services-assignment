package musiclessonservice.service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Component;

import musiclessonservice.model.Booking;
import musiclessonservice.model.GradeRange;
import musiclessonservice.model.LessonSlot;
import musiclessonservice.model.Tutor;

@Component 
public class MusicLessonServiceImpl implements MusicLessonService {

    private Tutor tutor;

    private List<LessonSlot> allSlots;

    private Map<LessonSlot, Booking> bookings;

    private Set<LessonSlot> blockedSlots;

    public MusicLessonServiceImpl (){
        tutor = new Tutor("Eric Clapton");
        tutor.getInstruments().put("piano", new GradeRange(1,6));
        tutor.getInstruments().put("drums", new GradeRange(1,5));
        tutor.getInstruments().put("e guitar", new GradeRange(3,7));
        allSlots = new ArrayList<>();
        for(int i = 1; i <= 5; i++){
            addSlotsForDay(DayOfWeek.of(i), LocalTime.of(16,00));
        }
        addSlotsForDay(DayOfWeek.SATURDAY, LocalTime.of(9,00));
        bookings = new HashMap<>();
        blockedSlots = new HashSet<>();
    }

    private void addSlotsForDay(DayOfWeek day, LocalTime startTime){
        LocalTime time = startTime;
        while(time.isBefore(LocalTime.of(20,00))){
            allSlots.add(new LessonSlot(day,time));
            time = time.plusMinutes(30);
        }
    }

    @Override
    public Tutor getTutor() {
        return tutor;
    }

    @Override
    public List<LessonSlot> getAvailableSlots() {
        List<LessonSlot> result = new ArrayList<>();
        for (LessonSlot lessonSlot : allSlots) {
            if (!blockedSlots.contains(lessonSlot) && !bookings.containsKey(lessonSlot)) {
                result.add(new LessonSlot(lessonSlot));
            }
        }
        return result;
    }

    @Override
    public Booking makeBooking(int matric, String name, String instrument, LessonSlot slot) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'makeBooking'");
    }

    @Override
    public List<Booking> getPupilBookings(int matric, String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPupilBookings'");
    }

    @Override
    public void cancelBooking(int matric, String name, DayOfWeek day) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancelBooking'");
    }

    @Override
    public List<Booking> getBookingsForDay(DayOfWeek day) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBookingsForDay'");
    }

}