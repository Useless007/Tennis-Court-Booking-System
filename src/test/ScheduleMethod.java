package test;

public class ScheduleMethod {
    private String DaysandHoursStrings;
    private int SumOfDays;
    private String DayOfWeekStrings;
    
    public ScheduleMethod() {}

    public void setDaysandHoursStrings(String n) {
        this.DaysandHoursStrings = n;
    }

    public String getDaysandHoursStrings() {
        return this.DaysandHoursStrings;
    }

    public String getHourstString(String DaysandHoursStrings) {
        String day = DaysandHoursStrings;
        return day;
    }

    public void setSumofDays(int days) {
        this.SumOfDays = days;
    }

    public int getSumofDays() {
        return this.SumOfDays;
    }

    public void setDayOfWeekStrings(String n) {
        this.DayOfWeekStrings = n;
    }

    public String getDayOfWeekStrings() {
        return this.DayOfWeekStrings;
    }

    
}