package ltdd1.teamvanphong.quanlychitieucanhan.Model;

public class IconItem {
    private int iconResId;
    private String name;
    private String resourceName;

    public IconItem(int iconResId, String name) {
        this.iconResId = iconResId;
        this.name = name;
        this.resourceName = resourceName;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getName() {
        return name;
    }

    public String getResourceName() {
        return resourceName;
    }
}
