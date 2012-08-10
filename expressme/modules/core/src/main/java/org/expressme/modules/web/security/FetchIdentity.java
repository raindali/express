package org.expressme.modules.web.security;

import org.expressme.modules.web.Identity;

public interface FetchIdentity {
	Identity fetch(String id);
}
