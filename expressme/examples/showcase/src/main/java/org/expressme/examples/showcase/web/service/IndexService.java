package org.expressme.examples.showcase.web.service;

import org.expressme.examples.showcase.domains.entity.User;
import org.expressme.webwind.annotations.Service;

@Service
public class IndexService {
	public void index() {
		new User().create();
	}
}
