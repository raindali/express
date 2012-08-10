package org.expressme.modules.web.security;

import org.expressme.modules.web.Identity;
import org.expressme.modules.web.SignOnException;
import org.expressme.modules.web.SignOnRequiredException;

/**
 * Bind user identity with ThreadLocal.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public abstract class SecurityContext {

    private SecurityContext() {}

    private static final ThreadLocal<Identity> identityThreadLocal = new ThreadLocal<Identity>();

    public static Identity getIdentity() {
        return identityThreadLocal.get();
    }

    public static Identity getRequiredIdentity() {
        Identity identity = identityThreadLocal.get();
        if (identity==null)
            throw new SignOnRequiredException();
        return identity;
    }

    public static void setIdentity(Identity identity) {
        identityThreadLocal.set(identity);
    }

    public static void removeIdentity() {
        identityThreadLocal.remove();
    }

    public static void assertUser(String userId) {
        Identity identity = getRequiredIdentity();
        if (! identity.getId().equals(userId))
            throw new SignOnException();
    }

    public static void assertAdmin() {
        Identity identity = getRequiredIdentity();
        if (! identity.isAdmin())
            throw new SignOnException();
    }
}
