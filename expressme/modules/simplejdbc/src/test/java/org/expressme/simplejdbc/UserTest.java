package org.expressme.simplejdbc;

import org.expressme.simplejdbc.core.Db;
import org.expressme.simplejdbc.core.DbHolder;
import org.expressme.simplejdbc.core.JdbcTemplate;
import org.junit.Test;

public class UserTest {

	@Test
	public void test() {
		Db db = new Db();
		db.setJdbcTemplate(new JdbcTemplate());
		UserModel user = new UserModel();
		new DbHolder().setDb(db);
		user = DbHolder.getDb().selectById(UserModel.class, 1);
		user.setId(89);
//		user.create();
//		user.deleteById();
//		user.updateEntity();
//		user.updateProperties("name", "nick");
//		user.queryForLimitedList("select * from user where name like ?", 1, 2, new Object[]{"%m%"});
	}
}
