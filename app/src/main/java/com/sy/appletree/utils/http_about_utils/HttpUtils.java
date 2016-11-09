package com.sy.appletree.utils.http_about_utils;

import java.util.Map;

/**
 * 创建者     伍碧林
 * 创建时间   2016/7/8 11:24
 * 描述	      ${TODO}
 *
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-07-08 11:33:58 +0800 (星期五, 08 七月 2016) $
 * 更新描述   ${TODO}
 */
public class HttpUtils {
    public static String getUrlParamsByMap(Map<String, Object> map) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }
}
