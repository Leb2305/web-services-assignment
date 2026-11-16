package musiclessonservice.model;

import java.util.HashMap;
import java.util.Map;

public class Student {

    private int matric;
    private String name;
    private int age;
    private Map<String, InstrumentGrades> instruments = new HashMap<>();

    public Student(){ matric = 0; name = "_"; age = 0;}

    public Student(int matric, String name, int age){
        this.matric = matric;
        this.name = name;
        this.age = age;
        
    }

    public int getMatric() {return matric;}
    public void setMatric(int matric) {this.matric = matric;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public int getAge() {return age;}
    public void setAge(int age) {this.age = age;}

    public Map<String, InstrumentGrades> getInstruments() {return instruments;}
    public void setInstruments(Map<String, InstrumentGrades>instruments) {this.instruments = instruments;}

    
}