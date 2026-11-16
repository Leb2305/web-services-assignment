package musiclessonservice.model;

import java.util.HashMap;
import java.util.Map;

public class Tutor {

    private String name;
    private Map<String, InstrumentGrades> instruments = new HashMap<>();

    public Tutor() {name = "_";}

    public Tutor(String name) {this.name = name;}

    public Tutor(Tutor that) {this.name = that.name;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public Map<String, InstrumentGrades> getInstruments() {return instruments;}
    public void setInstruments(Map<String,InstrumentGrades>instruments) {this.instruments = instruments;}

    public boolean teaches(String instrument, int grade){

        InstrumentGrades range = getInstruments().get(instrument);

        if (range == null){
            return false;
        }
        return range.includes(grade);
    }


}