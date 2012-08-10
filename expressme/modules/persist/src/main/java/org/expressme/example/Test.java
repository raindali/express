package org.expressme.example;

import java.lang.reflect.Method;

import org.expressme.simplejdbc.Db;
import org.expressme.simplejdbc.JdbcTemplate;
import org.expressme.simplejdbc.NamingStrategy;

public class Test {
	@org.junit.Test
	public void test() {
		Method[] methods = User.class.getMethods();
		for (int i = 0; i < methods.length; i++) {
			System.out.println(methods[i].toString() + methods[i].getDeclaringClass());
		}
		for (char c : "".toCharArray()) {
			System.out.println((int)c);
		}
		System.out.println("user_name".replace('_', '\0'));
		Db db = new Db();
		db.setJdbcTemplate(new JdbcTemplate());
		db.setNamingStrategy(NamingStrategy.underlinedNamingStrategy);
		UserDto dto = new UserDto();
		dto.setDb(db);
		dto.create();
//		User user = new User();
//		user.setDb(db);
//		user.create();
		db.create(dto);
		db.updateProperties(dto, "name");
		db.executeUpdate("update User set name =? where id in (?)", new int[]{1, 2, 3});
	}
}
