package musiclessonservice.controller;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import musiclessonservice.model.*;
import musiclessonservice.service.*;

@RestController
@CrossOrigin
public class MusicLessonTutorController {

    private MusicLessonService mls;

    public MusicLessonTutorController(MusicLessonService mls) {
        this.mls = mls;
    }

    @GetMapping("/tutor/bookings")
    public List<Booking> getBookingsForDay(@RequestParam DayOfWeek day) {
        return mls.getBookingsForDay(day);
    }

    @PostMapping("/tutor/blockSlot")
    @ResponseStatus(HttpStatus.CREATED)
    public void blockLessonSlots(@RequestBody LessonSlot slot) {
        if (!mls.blockASlot(slot)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such lesson slot exists");
        }
    }

    @DeleteMapping("/tutor/unblockSlot")
    public void unblockLessonSlots(@RequestBody LessonSlot slot) {
        if (!mls.unblockASlot(slot)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such lesson slot is blocked");
        }
    }

    @PostMapping("/tutor/blockDay")
    @ResponseStatus(HttpStatus.CREATED)
    public void blockDay(@RequestParam DayOfWeek day) {
        if (!mls.blockADay(day)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No lesson slots exist on this day");
        }
    }

    @DeleteMapping("/tutor/unblockDay")
    public void unblockDay(@RequestParam DayOfWeek day){
        if(!mls.unblockADay(day)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No blocked slots on that day");
        }
    }

    @GetMapping("/tutor/getBlockedSlots")
    public List<LessonSlot> getBlockedLessonSlots(){
        return mls.getBlockedSlots();
    }
}
