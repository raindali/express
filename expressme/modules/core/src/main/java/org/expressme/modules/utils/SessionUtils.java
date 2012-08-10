package org.expressme.modules.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.expressme.modules.web.Identity;

public class SessionUtils {

    private static final String ATTR_VIEWER = "__attr_viewer__";
    private static final String ATTR_LAST_URL = "__attr_last_url__";

    /**
     * Get identity of viewer, or null if viewer is not sign on yet.
     */
    public static Identity getViewer(HttpSession session) {
        return (Identity) session.getAttribute(ATTR_VIEWER);
    }

    /**
     * Set identity of viewer into http session.
     */
    public static void setViewer(HttpSession session, Identity viewer) {
        session.setAttribute(ATTR_VIEWER, viewer);
    }

    /**
     * Remove identity of viewer from http session.
     */
    public static void removeViewer(HttpSession session) {
        session.removeAttribute(ATTR_VIEWER);
    }

    public static String getLastUrl(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String url = (String) session.getAttribute(ATTR_LAST_URL);
        if (url!=null)
            return url;
        StringBuilder sb = new StringBuilder(128);
        sb.append(request.getScheme()).append("://").append(request.getServerName());
        int port = request.getServerPort();
        if (port!=80)
            sb.append(':').append(port);
        sb.append('/');
        session.removeAttribute(ATTR_LAST_URL);
        return sb.toString();
    }

    public static void setLastUrl(HttpSession session, String url) {
        session.setAttribute(ATTR_LAST_URL, url);
    }
}
