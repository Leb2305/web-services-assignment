package musiclessonservice.service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import musiclessonservice.model.Booking;
import musiclessonservice.model.InstrumentGrades;
import musiclessonservice.model.LessonSlot;
import musiclessonservice.model.Student;
import musiclessonservice.model.Tutor;

@Component 
public class MusicLessonServiceImpl implements MusicLessonService {

    private Tutor tutor;

    private List<LessonSlot> allSlots;

    private Map<LessonSlot, Booking> bookings;

    private Set<LessonSlot> blockedSlots;

    private PupilTracker tracker;

    public MusicLessonServiceImpl (PupilTracker tracker){
        this.tracker = tracker;
        tutor = new Tutor("Eric Clapton");
        tutor.getInstruments().put("piano", new InstrumentGrades(3,7));
        tutor.getInstruments().put("drums", new InstrumentGrades(1,5));
        tutor.getInstruments().put("guitar", new InstrumentGrades(3,7));
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
        Student pupil = tracker.getPupil(matric);
        if(!isSlotAvailable(slot)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Slot is not available");
        }
        if(pupil == null || !pupil.getName().equals(name)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pupil not found or name doesn't match");
        }
        InstrumentGrades played = pupil.getInstruments().get(instrument);
        if(played == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pupil does not play this instrument");
        }
        if(!tutor.teaches(instrument, played.getGrade())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The tutor does not teach this instrument at the pupils grade");
        }
        if(pupil.getAge() < 16 && slot.isSixPmOrLater()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pupils under 16 can't book lessons from 6pm onwards");
        }
        for (Booking booking : bookings.values()) {
            if(booking.getMatric() == matric && booking.getSlot().getDay() == slot.getDay()){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Pupil has already booked a lesson on this day (One lesson per day)");
            }
        }
        Booking booked = new Booking(matric, name, instrument, slot);
        bookings.put(new LessonSlot(slot), new Booking(booked));
        return new Booking(booked);
        
    }

    @Override
    public List<Booking> getPupilBookings(int matric, String name) {
        List<Booking> results = new ArrayList<>();
        for (Booking booking : bookings.values()) {
            if(booking.getMatric() == matric && booking.getName().equals(name)){
                results.add(new Booking(booking));
            }
        }
        return results;
    }

    @Override
    public Booking cancelBooking(int matric, String name, DayOfWeek day) {
        Booking found = null;

        for (Booking booking : bookings.values()) {
            if(booking.getMatric() == matric && booking.getName().equals(name) && booking.getSlot().getDay() == day){
                found = booking;
            }
        }
        if(found == null){
            return null;
        }
        bookings.remove(found.getSlot());
        return found;

    }

    @Override
    public List<Booking> getBookingsForDay(DayOfWeek day) {
        List<Booking> result = new ArrayList<>();
        for (Booking booking : bookings.values()) {
            if(booking.getSlot().getDay() == day){
                result.add(new Booking(booking));
            }
        }
        return result;
    }

    @Override 
    public boolean blockASlot(LessonSlot slot){
        if(!allSlots.contains(slot)){
            return false;
        }
        blockedSlots.add(new LessonSlot(slot));
        return true;
    }

    @Override
    public boolean unblockASlot(LessonSlot slot) {
        if(!blockedSlots.contains(slot)){
            return false;
        }
        return blockedSlots.remove(slot);
    }

    @Override
    public boolean blockADay(DayOfWeek day) {
        boolean result = false;

        for (LessonSlot slots : allSlots) {
            if(slots.getDay() == day){
                blockedSlots.add(new LessonSlot(slots));
                result = true;
            }
        }
        return result;
    }

    @Override
    public boolean unblockADay(DayOfWeek day) {
         boolean result = false;

        for (LessonSlot lessonSlot : allSlots) {
            if(lessonSlot.getDay() == day){
                if(blockedSlots.remove(new LessonSlot(lessonSlot))){
                    result = true;
                }
            }
        }
        return result;
    }

    @Override
    public List<LessonSlot> getBlockedSlots() {
        List<LessonSlot> result = new ArrayList<>();
        for (LessonSlot lessonSlot : allSlots) {
            if(blockedSlots.contains(lessonSlot)){
                result.add(new LessonSlot(lessonSlot));
            }
        }
        return result;
        
    }

}