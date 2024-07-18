package ltdd1.teamvanphong.quanlychitieucanhan.Model;

public class IconItem {
    private int iconResId;
    private String name;

    public IconItem(int iconResId, String name) {
        this.iconResId = iconResId;
        this.name = name;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getName() {
        return name;
    }
}
