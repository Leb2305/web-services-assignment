package musiclessonservice.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import musiclessonservice.service.*;
import musiclessonservice.model.*;

@RestController
@CrossOrigin
public class MusicLessonController {

    private MusicLessonService mls;

    public MusicLessonController(MusicLessonService mls) {
        this.mls = mls;
    }


}
