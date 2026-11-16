package musiclessonservice.service;

import musiclessonservice.model.Student;
import java.util.Map;
import java.util.HashMap;

public class FakePupilTracker implements PupilTracker{

    private Map<Integer, Student> pupils = new HashMap<>();

    public void addPupil(Student pupil){
        pupils.put(pupil.getMatric(), pupil);
    }

    @Override
    public Student getPupil(int matric) {
        return pupils.get(matric);
    }

}
