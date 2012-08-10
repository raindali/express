package org.expressme.examples.typecho.dao;

import java.util.List;

import org.expressme.examples.typecho.entity.Options;
import org.expressme.examples.typecho.entity.mapper.OptionsRowMapper;
import org.expressme.persist.MappedBy;
import org.expressme.persist.Param;
import org.expressme.persist.Query;
import org.expressme.persist.Unique;
import org.expressme.persist.Update;
import org.expressme.persist.mapper.IntegerRowMapper;

public interface OptionsDao {

	String QUERY = "name, user, value";
	
	@Unique
	@MappedBy(IntegerRowMapper.class)
	@Query("select count(*) from typecho_options")
	int size();

	@Unique
	@MappedBy(OptionsRowMapper.class)
	@Query("select " + QUERY + " from typecho_options where name=:name and user=:user")
	Options queryByNameAndUser(@Param("name") String name, @Param("user") String user);
	
	@MappedBy(OptionsRowMapper.class)
	@Query("select " + QUERY + " from typecho_options")
	List<Options> queryAll();

	@Update("insert into typecho_options(name, user, value) values (:o.name, :o.user, :o.value)")
	int insert(@Param("o") Options options);

	@Update("update typecho_options set user=:o.user, value=:o.value where name=:o.name")
	int update(@Param("o") Options options);

	@Update("delete from typecho_options where name=:name")
	int delete(@Param("name") String name);
}
