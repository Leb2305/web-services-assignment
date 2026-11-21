package musiclessonservice.model;

public class Booking {

    private int matric;
    private String name;
    private String instrument;
    private LessonSlot slot;

    public Booking(){
        matric = 0; name = "_"; instrument = "_"; slot = new LessonSlot(); 
    }

    public Booking(int matric, String name, String instrument, LessonSlot slot){
        this.matric = matric;
        this.name = name;
        this.instrument = instrument;
        this.slot = new LessonSlot(slot);
    }

    public Booking(Booking that){
        this.matric = that.matric;
        this.name = that.name;
        this.instrument = that.instrument;
    }

    public int getMatric() {return matric;}
    public void setMatric(int matric) {this.matric = matric;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getInstrument() {return instrument;}
    public void setInstrument(String instrument) {this.instrument = instrument;}

    public LessonSlot getSlot() {return slot;}
    public void setSlot(LessonSlot slot) {this.slot = slot;}

}
