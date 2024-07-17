package ltdd1.teamvanphong.quanlychitieucanhan.Model;

public class IncomeExpenseModel_khai {
    private int incomeExpenseId;
    private String type;
    private String amount;
    private String date;
    private String note;
    private int userId;
    private int categoryId;

    public int getIncomeExpenseId() {
        return incomeExpenseId;
    }

    public void setIncomeExpenseId(int incomeExpenseId) {
        this.incomeExpenseId = incomeExpenseId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
