package com.xaeport.crossborder.tools;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取客户端ip地址
 * Created by Administrator on 2017/4/10.
 */
public class GetIpAddr {
    public static String getRemoteIpAdd(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.contains(","))
            ip = ip.split(",")[0];
        return ip;
    }
}
