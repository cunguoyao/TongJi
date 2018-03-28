package com.linkage.tongji.bean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cunguoyao on 2018/3/27.
 */

public class MenuBean implements Serializable {

    private int id;
    private int type;
    private String iconUrl;
    private String iconMsg;

    public static MenuBean parseFromJson(JSONObject jsonObj) {
        MenuBean menu = new MenuBean();
        menu.setId(jsonObj.optInt("id"));
        menu.setIconMsg(jsonObj.optString("iconMsg"));
        menu.setIconUrl(jsonObj.optString("iconUrl"));
        menu.setType(jsonObj.optInt("type"));
        return menu;
    }

    public static List<MenuBean> parseFromJson(JSONArray jsonArray) {
        List<MenuBean> menus = new ArrayList<>();
        if (jsonArray != null && jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                MenuBean talk = parseFromJson(jsonArray.optJSONObject(i));
                if (talk != null)
                    menus.add(talk);
            }
        }
        return menus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getIconMsg() {
        return iconMsg;
    }

    public void setIconMsg(String iconMsg) {
        this.iconMsg = iconMsg;
    }
}
