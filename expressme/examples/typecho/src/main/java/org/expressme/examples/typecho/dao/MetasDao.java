package org.expressme.examples.typecho.dao;

import java.util.List;

import org.expressme.examples.typecho.entity.Metas;
import org.expressme.examples.typecho.entity.mapper.MetasRowMapper;
import org.expressme.persist.MappedBy;
import org.expressme.persist.Param;
import org.expressme.persist.Query;
import org.expressme.persist.Unique;
import org.expressme.persist.Update;
import org.expressme.persist.mapper.IntegerRowMapper;

public interface MetasDao {

	String QUERY = "mid, name, slug, type, description, counts, orders";

	@Unique
	@MappedBy(IntegerRowMapper.class)
	@Query("select count(*) from typecho_metas")
	int querySize();

	@Unique
	@MappedBy(MetasRowMapper.class)
	@Query("select " + QUERY + " from typecho_metas where mid=:mid")
	Metas queryByMid(@Param("mid") String mid);

	@MappedBy(MetasRowMapper.class)
	@Query("select " + QUERY + " from typecho_metas")
	List<Metas> queryAll();

	@Update("insert into typecho_metas(name, slug, type, description, counts, orders) values (:m.name, :m.slug, :m.type, :m.description, :m.counts, :m.orders)")
	int insert(@Param("m") Metas metas);

	@Update("update typecho_metas set name=:m.name, slug=:m.slug, type=:m.type, description=:m.description, counts=:m.counts, orders=:m.orders where mid=:m.mid")
	int update(@Param("m") Metas metas);

	@Update("delete from typecho_metas where mid=:mid")
	int delete(@Param("mid") String mid);
}
