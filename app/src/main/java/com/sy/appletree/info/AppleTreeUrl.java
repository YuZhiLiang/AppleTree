package com.sy.appletree.info;

/**
 * Created by 郁智良
 * on 2016/11/8 0008.
 * des url常量类
 */

public final class AppleTreeUrl {
    //根URL
    public static final String sRootUrl = "http://120.24.234.123/pingguoshu/";
    public static String sSession = "jession";
//    public static String sRootUrl = "http://192.168.0.124.8080/pingguoshu/";

    //检查手机号码是否可用
    public static final class MobileCheck {
        public static final String PROTOCOL = "user_app_checkMobile.action?";
        public static final String PARAMS_MOBILE = "mobile";

    }

    //发送验证码
    public static final class SendValCode {
        public final static String PROTOCOL = "user_app_sendValCode.action?";
        public final static String PARAMS_MOBILE = "mobile";

    }

    //校验验证码
    public static final class ValCode {
        public final static String PROTOCOL = "user_app_checkValCode.action?";
        public final static String PARAMS_VAI_CODE = "valCode";
    }

    //注册
    public static final class Register {
        public static final String PROTOCOL = "user_app_regUser.action?";
        public final static String PARAMS_E_MAIL = "email=";
        public final static String PARAMS_SEX = "sex=";
        public final static String PARAMS_MOBLIE = "mobile=";
        public final static String PARAMS_USER_NAME = "username=";
        public final static String PARAMS_SCHOOL_ID = "schoolId=";
        public final static String PARAMS_AREA_ID = "areaId=";
    }

    //登录
    public static final class Loging {
        public final static String PROTOCOL = "login_app_login.action?";
        public final static String PARAMS_USERNAME = "userName";
        public final static String PARAMS_PASSWORD = "password";
    }

    //查询课程
    public static class GetCoursePackage {
        public final static String PROTOCOL = "course_app_getCoursePackage.action?";
        //查询的限制条件，不带这个参数代表全部查询
        public final static String PARAMS_SEARCH_VAL = "searchVal";
    }

    //查询智库
    public static final class GetWisomLibList {
        public static final String PROTOCOL = "wisomLib_app_listWisomLib.action?";
        public static final String PARAMS_SEARCH_VAL = "searchVal";
    }

    //添加课程包
    public static final class AddCoursePackage {
        public final static String PROTOCOL = "course_app_addCoursePackage.action?";
        public final static String PARAMS_NAME = "name";
        public final static String PARAMS_VERSION = "version";//教材版本
        public final static String PARAMS_USUBJECT = "subject";//科目
        public final static String PARAMS_USE_GRADE = "useGrade";//年级
    }

    //删除课程包
    public static final class DelteCoursePackage {
        public static final String PROTOCOL = "course_app_deleteCoursePackage.action?";
        public static final String PARAMS_COURSE_PKG_ID = "coursePackageId";
    }

    //拿到课程包下得小课程
    public static final class GetCourse {
        public static final String PROTOCOL = "course_app_getCourse.action?";
        public static final String PARAMS_COURSE_PKG_ID = "coursePackageId";//要查询得小课程所属得课程包的ID
    }

    //添加课程包下的小课程
    public static final class AddCourse {
        public static final String PROTOCOL = "course_app_addCourse.action?";
        public static final String PARAMS_COURSE_PACKAGE_ID = "coursePackageId";//要添加到的课程包的ID
        public static final String PARAMS_NAME = "name";//小课程名称
        public static final String PARAMS_TARGET = "target";//小课程的目标
    }

    //拿到小课程的任务列表
    public static final class GetTaskList {
        public static final String PROTOCOL = "course_app_getTaskList.action?";
        public static final String PARAMS_COURSE_ID = "courseId";
    }

    //添加小课程的任务
    public static final class AddTask {
        public static final String PROTOCOL = "course_app_addTask.action?";
        public static final String PARAMS_COURSE_ID = "courseId";//小课程ID
        public static final String PARAMS_NAME = "name";//任务标题
        public static final String PARAMS_CONTENT = "content";//任务内容
    }

    //修改小课程的任务
    public static final class EditTask {
        public static final String PROTOCOL = "course_app_editTask.action?";
        public static final String PARAMS_TASK_ID = "taskId";//小课程ID
        public static final String PARAMS_NAME = "name";//任务标题
        public static final String PARAMS_CONTENT = "content";//任务内容
    }

    //发表到智库
    public static final class ReleaseToWisomLib {
        public static final String PROTOCOL = "course_app_releaseToWisomLib.action?";
        public static final String PARAMS_COURSE_PKG_ID = "coursePackageId";
    }

    //拿到省
    public static final class GetPrinceList {
        public static final String PROTOCOL = "login_app_getPrince.action";
    }

    //拿到市
    public static final class GetCityList {
        public static final String PROTOCOL = "login_app_getCity.action?";
        public static final String PARAMS_PRINCE_ID = "princeId=";
    }

    //拿到区
    public static final class GetAreaList {
        public static final String PROTOCOL = "login_app_getAreaList.action?";
        public static final String PARAMS_AREA_ID = "areaId=";
    }

    //拿到学校列表
    public static final class GetSchoolList {
        public static final String PROTOCOL = "login_app_getSchoolList.action?";
        public static final String PARAMS_AREA_ID = "areaId=";
    }

}