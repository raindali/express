package com.javaeye.i2534;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import org.expressme.persist.Dao;

import com.javaeye.i2534.cnd.CndDao;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		BaseDao<TestVO> dao = new BaseDao<TestVO>() {
//
//			public Class<TestVO> getVOType() {
//				return TestVO.class;
//			}
//
//			public Connection getConnection() {
//				return ConnectionPool.getConnection();
//			}
//
//		};

		System.out.println(ClassUtils.convertName("addThisTimeDay"));;
		System.out.println(JdbcGenerator.update(new Cnd(User.class).column("name", "mail").where(Clause.to("name", OP.EQ, ""))));
		System.out.println(JdbcGenerator.fetch(new Cnd(User.class).column("name", "mail").where(Clause.to("name", OP.EQ, "").and("mail", OP.LIKE, "%@%"))));
		System.out.println(Clause.to("name", OP.EQ, "%d").and("nick", OP.EQ, "%x").or("mail", OP.LIKE, "%@2.3"));
		Cnd cnd = new Cnd(User.class).where(Clause.to("id", OP.EQ, "1"))
				.and(Clause.to("name", OP.EQ, "%").and("name", OP.EQ, "%")).or(Clause.to("nick", OP.LIKE, "mail%"))
				.or(Clause.to("mail", OP.EQ, "1")).limit(1, 20);
		System.out.println(cnd.toString());
		//		List<TestVO> list = dao.query(new Cnd(dao.getVOType()).where("time",
		//				OP.LESS_EQ, new Date()).and("flag", OP.EQ, '*').orderBy(
		//				"biaoShi", false).orderBy("time", true));
		//		for (TestVO vo : list) {
		//			System.out.println(vo.getBiaoShi());
		//		}
		//		CndDao cndDao = null;
		//		cndDao.delete(new Cnd(Test.class));
		//		select id,name,age,nick,mail from user where id = 1 or (name like 'ddd%' and nick != 'good') and mail = '1@2.3'
		//		where(OP.toSql(id, OP.LIKE , 1)).or(OP.toSql("name", OP.LIKE, "ddd%"), clause2).and();
		//		where().or(clause.to(id, OP.LIKE, 1), clause.to(…));
		//		select id,name,age,nick,mail from user where (name like 'ddd%' and nick != 'good') and mail = '1@2.3'
		
		Dao.autoCommit.set(false);
		User u = new Dao().createDao(User.class, null);
		u.getClass();
	}

}
