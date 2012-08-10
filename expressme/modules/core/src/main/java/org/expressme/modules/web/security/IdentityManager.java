package org.expressme.modules.web.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.expressme.modules.web.Identity;

/**
 * IdentityManager used to get user's identity from HTTP request or HTTP session.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public interface IdentityManager {

    public Identity getIdentity(HttpServletRequest request, HttpServletResponse response);

    public Identity getRequiredIdentity(HttpServletRequest request, HttpServletResponse response);

    public void storeIdentity(HttpServletRequest request, HttpServletResponse response, Identity identity);

    public void removeIdentity(HttpServletRequest request, HttpServletResponse response);

    public String getSignOnURL(HttpServletRequest request, HttpServletResponse response);

    public String getSignOutURL(HttpServletRequest request, HttpServletResponse response);

}
