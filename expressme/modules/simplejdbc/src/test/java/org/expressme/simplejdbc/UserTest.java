package org.expressme.simplejdbc;

import org.expressme.simplejdbc.core.Db;
import org.expressme.simplejdbc.core.DbHolder;
import org.expressme.simplejdbc.core.JdbcTemplate;
import org.expressme.simplejdbc.criteria.Criteria;
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
		Criteria._select(User.class, new String[] { "name", "password" })._where("name like ?", "%n")._order("ID DESC")
				._limit(2)._offset(4);
		Criteria._delete(User.class)._where("name like ?", "%n");
		Criteria._update(User.class, new String[] { "name", "password" }, new String[] {})._where("name like ?", "%n");
		Criteria._insert(User.class, new String[] { "name", "password" }, new String[] {});
	}
}
