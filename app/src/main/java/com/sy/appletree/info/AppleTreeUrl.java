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

    //设置用户头像
    public static final class ChangePhoto {
        public final static String PROTOCOL = "fileUpload_app_changePhoto.action?";
        public final static String PARAMS_FILE = "file=";
    }

    //设置用户名称
    public static final class ChangeName {
        public final static String PROTOCOL = "user_app_changeName.action?";
        public final static String PARAMS_NAME = "name=";
    }

    //设置用户性别
    public static final class ChangeSex {
        public final static String PROTOCOL = "user_app_changeSex.action?";
        public final static String PARAMS_SEX= "sex=";
    }

    //设置用户邮箱
    public static final class Changemail {
        public final static String PROTOCOL = "user_app_changeEmail.action?";
        public final static String PARAMS_EMAIL= "email=";
    }

    //设置用户手机号
    public static final class ChangMobile {
        public final static String PROTOCOL = "user_app_changeMobile.action?";
        public final static String PARAMS_MOBILE= "mobile=";
    }

    //设置用户手机号
    public static final class ChangPassword {
        public final static String PROTOCOL = "user_app_changePassword.action?";
        public final static String PARAMS_OLD_PASS_WORD= "oldPassword=";
        public final static String PARAMS_PASS_WORD= "password=";
    }

    //查询课程
    public static class GetCoursePackage {
        public final static String PROTOCOL = "course_app_getCoursePackage.action?";
        //查询的限制条件，不带这个参数代表全部查询
        public final static String PARAMS_SEARCH_VAL = "searchVal";
    }

    //查询收藏列表
    public static final class ListCollect {
        public static final String PROTOCOL = "collect_app_listCollect.action?";
    }

    //查询智库
    public static final class GetWisomLibList {
        public static final String PROTOCOL = "wisomLib_app_listWisomLib.action?";
        public static final String PARAMS_SEARCH_VAL = "searchVal";
    }

    //收藏智库
    public static final class CollectWisomLib {
        public static final String PROTOCOL = "wisomLib_app_collectWisomLib.action?";
        public static final String PARAMS_COURSE_PKG_ID = "coursePackageId=";
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

    //查询任务的详细信息
    public static final class GetTaskDetailInfo {
        public static final String PROTOCOL = "course_app_getTaskDetailInfo.action?";
        public static final String PARAMS_AREA_ID = "taskId=";
    }

    //查询所有评价指标
    public static final class GetEvalPointList {
        public static final String PROTOCOL = "evaPoint_app_getEvalPointList.action?";
    }

    //查询班级列表
    public static final class GetSchoolClass {
        public static final String PROTOCOL = "schoolClass_app_getSchoolClass.action?";
        public static final String PARAMS_SCHOOL_ID = "schoolId=";
        public static final String PARAMS_TYPE = "type=";
    }

    //加入班级
    public static final class JointClass {
        public static final String PROTOCOL = "schoolClass_app_jointClass.action?";
        public static final String PARAMS_CLASS_ID = "classId=";
    }

    //检查班级是否存在
    public static final class CheckHaveClass {
        public static final String PROTOCOL = "schoolClass_app_checkHaveClass.action?";
        public static final String PARAMS_NAME = "name=";
        public static final String PARAMS_SCHOOL_ID = "schoolId=";
    }

    //创建班级
    public static final class CrateClass {
        public static final String PROTOCOL = "schoolClass_app_createClass.action?";
        public static final String PROTOCOL_CLASS_IS_HAVE = "schoolClass_app_checkHaveClass.action?";
        public static final String PARAMS_SCHOOL_ID = "schoolId=";
        public static final String PARAMS_TYPE = "type=";
        public static final String PARAMS_GRADE = "grade=";
        public static final String PARAMS_BAN_JI = "banji=";
        public static final String PARAMS_NAME = "name=";
    }

    //查询当前用户加入的所有班级简要信息
    public static final class ClassSummary {
        public static final String PROTOCOL = "schoolClass_app_getClassSummary.action?";
    }

    //查询班级的所有学生
    public static final class GetStudents {
        public static final String PROTOCOL = "schoolClass_app_getStudents.action?";
        public static final String PARAMS_CLASS_ID = "classId=";
    }

    //获取学生信息
    public static final class GetStudentInfo {
        public static final String PROTOCOL = "schoolClass_app_getStudentInfo.action?";
        public static final String PARAMS_STUDENT_ID = "studentId=";
    }

    //退出班级
    public static final class QuitClass {
        public static final String PROTOCOL = "schoolClass_app_quitClass.action?";
        public static final String PARAMS_CLASS_ID = "classId=";
    }

    //添加学生
    public static final class AddStudent {
        public static final String PROTOCOL = "schoolClass_app_addStudent.action?";
        public static final String PARAMS_CLASS_ID = "classId=";
        public static final String PARAMS_STUDENT_NUM = "studentNumber=";
        public static final String PARAMS_NAME = "name=";
        public static final String PARAMS_MOBILE = "mobile=";
    }

    //添加学生
    public static final class UpdateStudent {
        public static final String PROTOCOL = "schoolClass_app_updateStudent.action?";
        public static final String PARAMS_STUDENT_ID = "studentId=";//ID
        public static final String PARAMS_STUDENT_NUM = "studentNumber=";//学号
        public static final String PARAMS_NAME = "name=";
        public static final String PARAMS_CLASS_ID = "classId=";
        public static final String PARAMS_MOBILE = "mobile=";
    }

    //查询班级分组方案
    public static final class GetClassGroup {
        public static final String PROTOCOL = "group_app_getClassGroup.action?";
        public static final String PARAMS_CLASS_ID = "classId=";

    }

    //查询班级分组方案
    public static final class DeleteCollect {
        public static final String PROTOCOL = "collect_app_deleteCollect.action?";
        public static final String PARAMS_ID = "id=";
    }


}