package org.expressme.modules.web.security;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.expressme.modules.utils.HashUtils;
import org.expressme.modules.utils.HttpUtils;
import org.expressme.modules.web.Identity;
import org.expressme.modules.web.SignOnRequiredException;

import com.google.inject.Inject;

/**
 * IdentityManager based on Cookie.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class CookieIdentityManager implements IdentityManager {

    private static final String IDENTITY_COOKIE = "_sys_cookie_id_store_";

    private static final Cookie CLEAN_COOKIE;

    private static final long EXPIRES_TIME = 1000L * 3600L * 24L * 365L;

    @Inject FetchIdentity fetchIdentity;

    static {
        CLEAN_COOKIE = new Cookie(IDENTITY_COOKIE, "x");
        CLEAN_COOKIE.setMaxAge(0);
        CLEAN_COOKIE.setPath("/");
    }

    public Identity getIdentity(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies==null)
            return null;
        String id_cookie = null;
        for (Cookie cookie : cookies) {
            if (IDENTITY_COOKIE.equals(cookie.getName())) {
                id_cookie = cookie.getValue();
                break;
            }
        }
        if (id_cookie==null)
            return null;
        // parse id_cookie: userid_expires_hash
        try {
            String[] ss = id_cookie.split("\\_");
            if (ss.length!=3)
                throw new IllegalArgumentException("Clean invalid cookie.");
            String id = ss[0];
            long expires = Long.parseLong(ss[1]);
            if (System.currentTimeMillis()>=expires)
                throw new IllegalArgumentException("Clean expired cookie.");
            Identity identity = fetchIdentity.fetch(id);
            String hash = calcHash(id, identity.getPassword(), expires);
            if (hash.equals(ss[2])) {
                // valid cookie:
                return identity;
            }
            throw new IllegalArgumentException("Clean bad cookie.");
        }
        catch (Exception e) {
            response.addCookie(CLEAN_COOKIE);
            return null;
        }
    }

    public Identity getRequiredIdentity(HttpServletRequest request, HttpServletResponse response) {
        Identity identity = getIdentity(request, response);
        if (identity==null)
            throw new SignOnRequiredException();
        return identity;
    }

    public void storeIdentity(HttpServletRequest request, HttpServletResponse response, Identity identity) {
        long expires = System.currentTimeMillis() + EXPIRES_TIME;
        StringBuilder sb = new StringBuilder();
        sb.append(identity.getId())
          .append('_')
          .append(expires)
          .append('_')
          .append(calcHash(identity.getId(), identity.getPassword(), expires));
        Cookie cookie = new Cookie(IDENTITY_COOKIE, sb.toString());
        cookie.setMaxAge((int)(expires / 1000));
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public void removeIdentity(HttpServletRequest request, HttpServletResponse response) {
        response.addCookie(CLEAN_COOKIE);
    }

    String calcHash(String id, String password, long expires) {
        return HashUtils.toMD5(id + password + expires);
    }

    public String getSignOnURL(HttpServletRequest request, HttpServletResponse response) {
        return "/auth/signon";
    }

    public String getSignOutURL(HttpServletRequest request, HttpServletResponse response) {
        return "/auth/signout?redirect=" + HttpUtils.getRefererURL(request);
    }
}
