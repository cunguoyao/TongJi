package com.linkage.tongji.app;

/**
 * Created by cunguoyao on 2018/3/28.
 */

public class Urls {

    public static final int pageSize = 10;

    public static final String web = "http://221.130.6.210:9822";
    public static final String api = web + "/report-client/api";

    public static final String version = api + "/version/update";
    public static final String login = api + "/login/login";
    public static final String resetPass = api + "/login/resetPass";
    public static final String indexReportList = api + "/report/getIndexReportList";
    public static final String menuList = api + "/skip/getSkipAddress";
}
