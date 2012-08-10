package org.expressme.modules.web.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.expressme.modules.utils.HttpUtils;
import org.expressme.modules.web.Identity;
import org.expressme.modules.web.SignOnRequiredException;

/**
 * IdentityManager based on HTTP session.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class SessionIdentityManager implements IdentityManager {

    private static final String IDENTITY_SESSION_KEY = "_sys_session_id_store_";

    public Identity getIdentity(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        return (Identity) session.getAttribute(IDENTITY_SESSION_KEY);
    }

    public Identity getRequiredIdentity(HttpServletRequest request, HttpServletResponse response) {
        Identity identity = getIdentity(request, response);
        if (identity==null)
            throw new SignOnRequiredException();
        return identity;
    }

    public void storeIdentity(HttpServletRequest request, HttpServletResponse response, Identity identity) {
        request.getSession().setAttribute(IDENTITY_SESSION_KEY, identity);
    }

    public void removeIdentity(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute(IDENTITY_SESSION_KEY);
    }

    public String getSignOnURL(HttpServletRequest request, HttpServletResponse response) {
        return "/auth/signon";
    }

    public String getSignOutURL(HttpServletRequest request, HttpServletResponse response) {
        return "/auth/signout?redirect=" + HttpUtils.getRefererURL(request);
    }
}
