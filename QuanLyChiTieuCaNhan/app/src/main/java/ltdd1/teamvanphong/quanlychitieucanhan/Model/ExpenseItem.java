package ltdd1.teamvanphong.quanlychitieucanhan.Model;

public class ExpenseItem {
    private String categoryName;
    private String amountSpent;
    private String iconName;

    public ExpenseItem(String categoryName, String amountSpent, String iconName) {
        this.categoryName = categoryName;
        this.amountSpent = amountSpent;
        this.iconName = iconName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getAmountSpent() {
        return amountSpent;
    }

    public void setAmountSpent(String amountSpent) {
        this.amountSpent = amountSpent;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }
}
