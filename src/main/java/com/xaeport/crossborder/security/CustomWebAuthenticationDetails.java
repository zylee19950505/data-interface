package com.xaeport.crossborder.security;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xcp on 2017/8/1.
 */
public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {
    private static final long serialVersionUID = 6975601077710753878L;
    private final String fillCode;
    private final String authCode;

    public CustomWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        fillCode = request.getParameter("fillCode");
        authCode = (String) request.getSession().getAttribute("authCode");
        request.getSession().removeAttribute("authCode");
    }

    public String getFillCode() {
        return this.fillCode;
    }

    public String getAuthCode() {
        return this.authCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append("; fillCode: ").append(this.getFillCode());
        sb.append(super.toString()).append("; authCode: ").append(this.getAuthCode());
        return sb.toString();
    }
}
