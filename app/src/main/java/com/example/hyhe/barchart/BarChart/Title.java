package com.example.hyhe.barchart.BarChart;

/**
 * desc:
 * author: hyhe
 * email: hyhe@anjuke.com
 * date: 16-4-13
 */
class Title{
    String text;
    int color;
    int selectedColor;

    public Title(){}

    public Title(String text, int color) {
        this.text = text;
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
    }
}
