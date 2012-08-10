package org.expressme.examples.typecho.dao;

import java.util.List;

import org.expressme.examples.typecho.entity.Users;
import org.expressme.examples.typecho.entity.mapper.UsersRowMapper;
import org.expressme.persist.MappedBy;
import org.expressme.persist.Param;
import org.expressme.persist.Query;
import org.expressme.persist.Unique;
import org.expressme.persist.Update;
import org.expressme.persist.mapper.IntegerRowMapper;

public interface UsersDao {

	String QUERY = "uid, name, password, mail, url, nick, created, activated, logged, groupname, authcode";

	@Unique
	@MappedBy(IntegerRowMapper.class)
	@Query("select count(*) from typecho_users where mail=:mail or name=:name or nick=:nick")
	int exist(@Param("mail")String mail, @Param("name")String name, @Param("nick")String nick);

	@Unique
	@MappedBy(IntegerRowMapper.class)
	@Query("select count(*) from typecho_users")
	int size();
	
	@Unique
	@MappedBy(UsersRowMapper.class)
	@Query("select " + QUERY + " from typecho_users where uid=:uid")
	Users queryByUid(@Param("uid") int uid);

	@MappedBy(UsersRowMapper.class)
	@Query("select " + QUERY + " from typecho_users")
	List<Users> queryAll();

	@Update("insert into typecho_users(username, password, mail, url, nick, created, activated, logged, group, authcode) values (:u.username, :u.password, :u.mail, :u.url, :u.nick, :u.created, :u.activated, :u.logged, :u.group, :u.authcode)")
	int insert(@Param("u") Users users);

	@Update("update typecho_users set username=:u.username, password=:u.password, mail=:u.mail, url=:u.url, nick=:u.nick, created=:u.created, activated=:u.activated, logged=:u.logged, group=:u.group, authcode=:u.authcode where uid=:u.uid")
	int update(@Param("u") Users users);

	@Update("delete from typecho_users  where uid = :uid")
	int delete(@Param("uid") String uid);
}
