package org.expressme.examples.typecho.dao;

import java.util.List;

import org.expressme.examples.typecho.entity.Relationships;
import org.expressme.examples.typecho.entity.mapper.RelationshipsRowMapper;
import org.expressme.persist.MappedBy;
import org.expressme.persist.Param;
import org.expressme.persist.Query;
import org.expressme.persist.Unique;
import org.expressme.persist.Update;
import org.expressme.persist.mapper.IntegerRowMapper;

public interface RelationshipsDao {

	@Unique
	@MappedBy(IntegerRowMapper.class)
	@Query("select count(*) from typecho_relationships")
	int size();

	@Unique
	@MappedBy(IntegerRowMapper.class)
	@Query("select count(*) from typecho_relationships where cid=:cid and mid=:mid")
	int exist(@Param("cid") int cid, @Param("mid") int mid);

	@MappedBy(RelationshipsRowMapper.class)
	@Query("select cid, mid from typecho_relationships where cid=:cid")
	List<Relationships> queryByCid(@Param("cid") int cid);

	@MappedBy(RelationshipsRowMapper.class)
	@Query("select cid, mid from typecho_relationships where mid=:mid")
	List<Relationships> queryByMid(@Param("mid") int mid);

	@MappedBy(RelationshipsRowMapper.class)
	@Query("select cid, mid from typecho_relationships")
	List<Relationships> queryAll();

	@Update("insert into typecho_relationships(cid, mid) values (:r.cid, :r.mid)")
	int insert(@Param("r") Relationships relationships);

	@Update("delete from typecho_relationships where cid=:r.cid and mid=:r.mid")
	int delete(@Param("r") Relationships relationships);

	@Update("delete from typecho_relationships where cid=:cid")
	int deleteByCid(@Param("cid") int cid);

	@Update("delete from typecho_relationships where mid=:mid")
	int deleteByMid(@Param("mid") int mid);
}
