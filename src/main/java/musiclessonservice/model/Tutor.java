package musiclessonservice.model;

import java.util.HashMap;
import java.util.Map;

public class Tutor {

    private String name;
    private Map<String,GradeRange> instruments = new HashMap<>();

    public Tutor() {name = "_";}

    public Tutor(String name) {this.name = name;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public Map<String, GradeRange> getInstruments() {return instruments;}
    public void setInstruments(Map<String, GradeRange>instruments) {this.instruments = instruments;}

    public boolean teaches(String instrument, int grade){

        GradeRange range = getInstruments().get(instrument);

        if (range == null){
            return false;
        }
        return range.includes(grade);
    }


}