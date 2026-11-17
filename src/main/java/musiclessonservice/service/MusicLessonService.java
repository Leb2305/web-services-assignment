package musiclessonservice.service;

import musiclessonservice.model.*;
import java.time.DayOfWeek;
import java.util.List;

public interface MusicLessonService {

    Tutor getTutor();

    default boolean isSlotAvailable(LessonSlot slot){
        return getAvailableSlots().contains(slot);
    }
    
    List<LessonSlot> getAvailableSlots();

    Booking makeBooking(int matric, String name, String instrument, LessonSlot slot);

    List<Booking> getPupilBookings(int matric, String name);

    Booking cancelBooking(int matric, String name, DayOfWeek day);

    List<Booking> getBookingsForDay(DayOfWeek day);

    boolean blockASlot(LessonSlot slot);

    boolean unblockASlot(LessonSlot slot);

    boolean blockADay(DayOfWeek day);

    boolean unblockADay(DayOfWeek day);

    List<LessonSlot> getBlockedSlots();

}