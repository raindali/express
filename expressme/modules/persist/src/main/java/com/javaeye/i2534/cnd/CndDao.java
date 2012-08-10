package com.javaeye.i2534.cnd;

import java.util.List;

import org.expressme.persist.Query;
import org.expressme.persist.Update;
import org.expressme.persist.mapper.RowMapper;

import com.javaeye.i2534.Cnd;

public interface CndDao {
	@Update
	<T> int insert(T t);

	@Update
	<T> int delete(T t);

	@Update
	<T> int update(T t);

	@Update
	<T> int delete(Cnd cnd);

	@Update
	<T> int update(T t, Cnd cnd);

	@Query
	<T> List<T> query(Cnd cnd, RowMapper<T> rowMapper);
}
