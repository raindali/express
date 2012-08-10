package org.expressme.examples.typecho.dao;

import java.util.List;

import org.expressme.examples.typecho.entity.Contents;
import org.expressme.examples.typecho.entity.mapper.ContentsRowMapper;
import org.expressme.persist.MappedBy;
import org.expressme.persist.Param;
import org.expressme.persist.Query;
import org.expressme.persist.Unique;
import org.expressme.persist.Update;
import org.expressme.persist.mapper.IntegerRowMapper;

public interface ContentsDao {

	String QUERY = "cid, title, slug, created, modified, text, orders, authorid, template, type, status, password, comments, allowcomment, allowping, allowfeed, parent";

	@Unique
	@MappedBy(IntegerRowMapper.class)
	@Query("select count(*) from typecho_contents")
	int querySize();

	@Unique
	@MappedBy(ContentsRowMapper.class)
	@Query("select " + QUERY + " from typecho_contents where cid=:cid")
	Contents queryByCid(@Param("cid") String cid);

	@MappedBy(ContentsRowMapper.class)
	@Query("select " + QUERY + " from typecho_contents where type=:type and status=:status and created<:created order by orders asc")
	List<Contents> queryContents(@Param("type") String type, @Param("status") String status, @Param("created") int created);
	
	@MappedBy(ContentsRowMapper.class)
	@Query("select " + QUERY + " from typecho_contents where type=:type and status=:status and created<:created order by created desc")
	List<Contents> queryPostRecent(@Param("type") String type, @Param("status") String status, @Param("created") int created);

	@MappedBy(ContentsRowMapper.class)
	@Query("select " + QUERY + " from typecho_contents")
	List<Contents> queryAll();

	@Update("insert into typecho_contents(title, slug, created, modified, text, orders, authorid, template, type, status, password, comments, allowcomment, allowping, allowfeed, parent) values (:c.title, :c.slug, :c.created, :c.modified, :c.text, :c.orders, :c.authorid, :c.template, :c.type, :c.status, :c.password, :c.comments, :c.allowcomment, :c.allowping, :c.allowfeed, :c.parent)")
	int insert(@Param("c") Contents contents);

	@Update("update typecho_contents set title=:c.title, slug=:c.slug, created=:c.created, modified=:c.modified, text=:c.text, orders=:c.orders, authorid=:c.authorid, template=:c.template, type=:c.type, status=:c.status, password=:c.password, comments=:c.comments, allowcomment=:c.allowcomment, allowping=:c.allowping, allowfeed=:c.allowfeed, parent=:c.parent where cid=:c.cid")
	int update(@Param("c") Contents contents);

	@Update("delete from typecho_contents where cid=:cid")
	int delete(@Param("cid") String cid);
}
