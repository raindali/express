package org.expressme.examples.showcase.dao;

import org.expressme.persist.Query;

public interface TestUserDao {

	@Query("select count(id) from test_user")
	int query();

	@Query("select count(id) from test_user")
	int queryName();
}
