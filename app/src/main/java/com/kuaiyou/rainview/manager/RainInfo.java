package com.kuaiyou.rainview.manager;

import android.util.Size;

import java.util.LinkedList;
import java.util.List;

public class RainInfo {
    public static final int RAIN_TYPE_DEFAULT = 0;
    public static final int RAIN_TYPE_NET = 1;

    private Size size;

    private RainInfo previous;

    public List<int[]> satisfactionArea;

    private int id;

    private Boolean isHead = false;

    private int left;

    private int top;

    private int[] range = new int[2];

    private int speed = 3;

    public RainInfo(Size size, int id) {
        this.size = size;
        this.id = id;
        satisfactionArea = new LinkedList<>();
    }

    public void addSatisfactionArea(int[] area) {
        satisfactionArea.add(area);
    }

    public void addSatisfactionArea(List list) {
        satisfactionArea.addAll(list);
    }

    public int[] getRainSection() {
        return range;
    }

    public void setRainSection(int x, int y) {
        range[0] = x;
        range[1] = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Size getSize() {
        return size;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public RainInfo getPrevious() {
        return previous;
    }

    public void setPrevious(RainInfo previous) {
        this.previous = previous;
    }

    public Boolean getHead() {
        return isHead;
    }

    public void setHead(Boolean head) {
        isHead = head;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
