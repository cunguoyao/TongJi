package com.linkage.tongji.bean;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class MsgBean {



	public  long id;

	public String title;

	public String time;

	public String content;

	public String url;


	public static MsgBean parseFromJson(JSONObject jsonObj) {
		MsgBean talk = new MsgBean();
		talk.setId(jsonObj.optLong("msgId"));
		talk.setContent(jsonObj.optString("content"));
		talk.setTitle(jsonObj.optString("title"));
		talk.setUrl(jsonObj.optString("url"));
		talk.setTime(jsonObj.optString("time"));
		return talk;
	}

	public static List<MsgBean> parseFromJson(JSONArray jsonArray) {
		List<MsgBean> talks = new ArrayList<MsgBean>();
		if (jsonArray != null && jsonArray.length() > 0) {
			for (int i = 0; i < jsonArray.length(); i++) {
				MsgBean talk = parseFromJson(jsonArray.optJSONObject(i));
				if (talk != null)
					talks.add(talk);
			}
		}
		return talks;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
