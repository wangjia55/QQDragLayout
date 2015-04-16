package com.jacob.qq.draylayout.bean;

/**
 * Created by jacob-wj on 2015/4/16.
 */
public class ListItemBean {
    private int icon;

    private String title;

    public ListItemBean(int icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "icon=" + icon +
                ", title='" + title + '\'' +
                '}';
    }
}
