package org.expressme.examples.typecho.dao;

import java.util.List;

import org.expressme.examples.typecho.entity.Comments;
import org.expressme.examples.typecho.entity.mapper.CommentsRowMapper;
import org.expressme.persist.MappedBy;
import org.expressme.persist.Param;
import org.expressme.persist.Query;
import org.expressme.persist.Unique;
import org.expressme.persist.Update;
import org.expressme.persist.mapper.IntegerRowMapper;

public interface CommentsDao {

	String QUERY = "coid, cid, created, author, authorid, ownerid, mail, url, ip, agent, text, type, status, parent";

	@Unique
	@MappedBy(IntegerRowMapper.class)
	@Query("select count(*) from typecho_comments")
	int querySize();

	@Unique
	@MappedBy(CommentsRowMapper.class)
	@Query("select " + QUERY + " from typecho_comments where coid=:coid")
	Comments queryByCoid(@Param("coid") String coid);

	@MappedBy(CommentsRowMapper.class)
	@Query("select " + QUERY + " from typecho_comments where cid=:cid")
	List<Comments> queryByCid(@Param("cid") String cid);

	@MappedBy(CommentsRowMapper.class)
	@Query("select " + QUERY + " from typecho_comments")
	List<Comments> queryAll();

	@Update("insert into typecho_comments(cid, created, author, authorid, ownerid, mail, url, ip, agent, text, type, status, parent) values (:c.cid, :c.created, :c.author, :c.authorid, :c.ownerid, :c.mail, :c.url, :c.ip, :c.agent, :c.text, :c.type, :c.status, :c.parent)")
	int insert(@Param("c") Comments comments);

	@Update("update typecho_comments set cid=:c.cid, created=:c.created, author=:c.author, authorid=:c.authorid, ownerid=:c.ownerid, mail=:c.mail, url=:c.url, ip=:c.ip, agent=:c.agent, text=:c.text, type=:c.type, status=:c.status, parent=:c.parent where coid=:c.coid")
	int update(@Param("c") Comments comments);

	@Update("delete from typecho_comments where coid=:coid")
	int deleteByCoid(@Param("coid") String coid);

	@Update("delete from typecho_comments where cid=:cid")
	int deleteByCid(@Param("cid") String cid);
}
