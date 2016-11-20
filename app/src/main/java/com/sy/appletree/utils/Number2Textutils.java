package com.sy.appletree.utils;

/**
 * Created by 郁智良
 * on 2016/11/20 0020.
 * des
 */

public class Number2Textutils {

    public static String paraseVersion(String version) {
        return paraseVersion(Integer.valueOf(version));
    }

    public static String paraseVersion(int version) {
        String versionText = null;
        switch (version) {
            case 1:
                versionText = "人教版";
                break;
            case 2:
                versionText = "苏教版";
                break;
            case 3:
                versionText = "北师大版";
                break;
            case 4:
                versionText = "湘教版";
                break;
            case 5:
                versionText = "长春版";
                break;
            case 6:
                versionText = "西师大版";
                break;
            case 7:
                versionText = "沪教版";
                break;
            case 8:
                versionText = "河南豫版";
                break;
            case 9:
                versionText = "自定义版";
                break;
        }
        return versionText;
    }

    public static String paraseGrade(String grade) {
        return paraseGrade(Integer.valueOf(grade));
    }

    public static String paraseGrade(int grade) {
        String gradeText = null;
        switch (grade) {
            case 1:
                gradeText = "小学一年级";
                break;
            case 2:
                gradeText = "小学二年级";
                break;
            case 3:
                gradeText = "小学三年级";
                break;
            case 4:
                gradeText = "小学四年级";
                break;
            case 5:
                gradeText = "小学五年级";
                break;
            case 6:
                gradeText = "小学六年级";
                break;
            case 7:
                gradeText = "初中七年级";
                break;
            case 8:
                gradeText = "初中八年级";
                break;
            case 9:
                gradeText = "初中九年级";
                break;
            case 10:
                gradeText = "高中一年级";
                break;
            case 11:
                gradeText = "高中二年级";
                break;
            case 12:
                gradeText = "高中三年级";
                break;
        }
        return gradeText;
    }

    public static String paraseSubject(String subject) {
        return paraseSubject(Integer.valueOf(subject));
    }

    public static String paraseSubject(int subject) {
        String subjectText = null;
        switch (subject) {
            case 1:
                subjectText = "语文";
                break;
            case 2:
                subjectText = "数学";
                break;
            case 3:
                subjectText = "英语";
                break;
            case 4:
                subjectText = "思想品德";
                break;
            case 5:
                subjectText = "科学";
                break;
            case 6:
                subjectText = "化学";
                break;
            case 7:
                subjectText = "物理";
                break;
            case 8:
                subjectText = "政治";
                break;
        }
        return subjectText;
    }

}
