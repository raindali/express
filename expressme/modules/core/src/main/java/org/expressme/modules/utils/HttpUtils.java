package org.expressme.modules.utils;

import javax.servlet.http.HttpServletRequest;

public abstract class HttpUtils {

    public static String getIP(HttpServletRequest request) {
        return request.getRemoteAddr();
    }

    public static String getLastURL(HttpServletRequest request) {
        return "";
    }

    public static String getRefererURL(HttpServletRequest request) {
        return request.getHeader("Referer");
    }

    public static String getRedirectURL(HttpServletRequest request) {
        String url = request.getParameter("redirect");
        if (url!=null)
            return url;
        return "/";
    }

    /**
     * Get host URL like "http://host-name:port" but without path.
     */
    public static String getHostURL(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder(128);
        sb.append(request.getScheme()).append("://").append(request.getServerName());
        int port = request.getServerPort();
        if (port!=80)
            sb.append(':').append(port);
        return sb.toString();
    }

    public static String getFullURL(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder(128);
        sb.append(request.getScheme()).append("://").append(request.getServerName());
        int port = request.getServerPort();
        if (port!=80)
            sb.append(':').append(port);
        sb.append(request.getRequestURI());
        String query = request.getQueryString();
        if (query!=null)
            sb.append('?').append(query);
        return sb.toString();
    }
}
