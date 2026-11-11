package musiclessonservice.model;

public class GradeRange {

    private int minGrade;
    private int maxGrade;

    public GradeRange(){
        minGrade = 0;
        maxGrade = 0;
    }

    public int getMinGrade() {return minGrade;}
    public void setMinGrade(int minGrade) {this.minGrade = minGrade;}
    
    public int getMaxGrade() {return maxGrade;}
    public void setMaxGrade(int maxGrade) {this.maxGrade = maxGrade;}

    public boolean includes(int grade){
        if (grade >= minGrade && grade <= maxGrade){
            return true;
        }
        else{
            return false;
        }
    }

}
