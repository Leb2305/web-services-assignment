package musiclessonservice.model;

import java.time.*;

public class LessonSlot {

    private DayOfWeek day;
    private LocalTime startTime;

    public LessonSlot(){
        day = DayOfWeek.MONDAY;
        startTime = LocalTime.of(16, 00);
    }

    public LessonSlot(DayOfWeek day, LocalTime startTime){
        this.day = day;
        this.startTime = startTime;
    }

    public DayOfWeek getDay(){return day;}
    public void setDay(DayOfWeek day) {this.day = day;}

    public LocalTime getStartTime(){return startTime;}
    public void setStartTime(LocalTime startTime) {this.startTime = startTime;}

    public boolean isSixPmOrLater(){

        if(!startTime.isBefore(LocalTime.of(18, 00))){
            return true;
        }
        else 
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((day == null) ? 0 : day.hashCode());
        result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LessonSlot other = (LessonSlot) obj;
        if (day != other.day)
            return false;
        if (startTime == null) {
            if (other.startTime != null)
                return false;
        } else if (!startTime.equals(other.startTime))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "LessonSlot [day=" + day + ", startTime=" + startTime + "]";
    }
}
