package com.sy.appletree.info;

/**
 * Created by 郁智良
 * on 2016/11/8 0008.
 * des url常量类
 */

public class AppleTreeUrl {
    //根URL
    public static String sRootUrl = "http://120.24.234.123/pingguoshu/";
//    public static String sRootUrl = "http://192.168.0.124.8080/pingguoshu/";

    //检查手机号码是否可用
    public static class MobileCheck {
        public static String PROTOCOL = "user_app_checkMobile.action?";
        public static String PARAMS_MOBILE = "mobile";

    }

    //发送验证码
    public static class SendValCode {
        public static String PROTOCOL = "user_app_sendValCode.action?";
        public static String PARAMS_MOBILE = "mobile";

    }

    //校验验证码
    public static class ValCode {
        public static String PROTOCOL = "user_app_checkValCode.action?";
        public static String PARAMS_VAI_CODE = "valCode";
    }

    //登录
    public static class Loging {
        public static String PROTOCOL = "login_app_login.action?";
        public static String PARAMS_USERNAME = "userName";
        public static String PARAMS_PASSWORD = "password";
    }

    //课程
    public static class Curriulum {
        public static String PROTOCOL = "course_app_getCoursePackage.action?";
        //查询的限制条件，不带这个参数代表全部查询
        public static String PARAMS_SEARCH_VAL = "searchVal";
    }

    //添加课程包
    public static class AddCoursePackage {
        public static String PROTOCOL = "course_app_addCoursePackage.action?";
        public static String PARAMS_NAME = "name";
        public static String PARAMS_VERSION = "version";//教材版本
        public static String PARAMS_USUBJECT = "subject";//科目
        public static String PARAMS_USE_GRADE = "useGrade";//年级

    }


}
