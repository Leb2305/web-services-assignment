package musiclessonservice.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import musiclessonservice.service.*;
import musiclessonservice.model.*;

@RestController
@CrossOrigin
public class MusicLessonPupilController {

    private MusicLessonService mls;

    public MusicLessonPupilController(MusicLessonService mls) {
        this.mls = mls;
    }

    @GetMapping("/tutorInfo")
    public Tutor getTutor() {
        Tutor tutor = mls.getTutor();
        if (tutor == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No tutor was found");
        }
        return tutor;
    }

    @GetMapping("/listSlots")
    public List<LessonSlot> getAvailableSlots() {
        return mls.getAvailableSlots();
    }

    @PostMapping("/booking")
    @ResponseStatus(HttpStatus.CREATED)
    public void makeBooking(@RequestBody Booking booking){
        mls.makeBooking(booking.getMatric(), booking.getName(), booking.getInstrument(), booking.getSlot());

    }

    @GetMapping("/booking")
    public List<Booking> getPupilBookings(@RequestParam int matric, @RequestParam String name){
        return mls.getPupilBookings(matric, name);
    }

    @DeleteMapping("/booking")
        public Booking cancelBooking(@RequestParam int matric, @RequestParam String name, @RequestParam DayOfWeek day){
            Booking canceled = mls.cancelBooking(matric, name, day);
            if(canceled == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No booking found for the pupil on that day");
            }
            return canceled;
    }

}
