package musiclessonservice.model;

public class InstrumentGrades {

    private int grade;
    private int minGrade;
    private int maxGrade;

    public InstrumentGrades(){grade = 0; minGrade = 0; maxGrade = 0; }

    public InstrumentGrades(int grade){
        this.grade = grade;
    }

    public InstrumentGrades(int minGrade, int maxGrade){
        this.minGrade = minGrade;
        this.maxGrade = maxGrade;
    }

    public int getGrade() {return grade;}
    public void setGrade(int grade) {this.grade = grade;}

    public int getMinGrade() {return minGrade;}
    public void setMinGrade(int minGrade) {this.minGrade = minGrade;}

    public int getMaxGrade() {return maxGrade;}
    public void setMaxGrade(int maxGrade) {this.maxGrade = maxGrade;}

    public boolean includes (int grade){
        if(grade >= minGrade && grade <= maxGrade){
            return true;
        }
        else{
            return false;
        }
    }

}
