package ltdd1.teamvanphong.quanlychitieucanhan.Model;

public class IncomeExpenseModel_tien {
    private int incomeExpenseId;
    private int type; //0 chi tiêu, 1 thu nhập
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

    public int getType() {
        return type;
    }

    public void setType(int type) { this.type = type;}

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
