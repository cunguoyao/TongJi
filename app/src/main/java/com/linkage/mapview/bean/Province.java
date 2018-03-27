package com.linkage.mapview.bean;

import android.graphics.Path;

import java.util.List;

/**
 * Created by Vmmet on 2016/7/29.
 */
public class Province {
    private String name;
    private List<Path> listpath;
    private int linecolor;

    public int getLinecolor() {
        return linecolor;
    }

    public void setLinecolor(int linecolor) {
        this.linecolor = linecolor;
    }

    public List<Path> getListpath() {
        return listpath;
    }
    public void setListpath(List<Path> listpath) {
        this.listpath = listpath;
    }
    private List<Lasso> pathLasso;
    public List<Lasso> getPathLasso() {
        return pathLasso;
    }
    public void setPathLasso(List<Lasso> pathLasso) {
        this.pathLasso = pathLasso;
    }
    private int color;
    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
