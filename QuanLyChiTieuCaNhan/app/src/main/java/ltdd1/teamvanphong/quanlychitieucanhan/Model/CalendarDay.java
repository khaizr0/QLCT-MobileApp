package ltdd1.teamvanphong.quanlychitieucanhan.Model;

public class CalendarDay {
    private String day;
    private String income;
    private String expense;

    public CalendarDay(String day, String income, String expense) {
        this.day = day;
        this.income = income;
        this.expense = expense;
    }

    public String getDay() { return day; }
    public void setDay(String day) { this.day = day; }

    public String getIncome() { return income; }
    public void setIncome(String income) { this.income = income; }

    public String getExpense() { return expense; }
    public void setExpense(String expense) { this.expense = expense; }
}
