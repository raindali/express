

import org.expressme.persist.MappedBy;
import org.expressme.persist.Query;
import org.expressme.persist.Unique;
import org.expressme.persist.mapper.IntegerRowMapper;

public interface IdDao {
	@Unique
	@MappedBy(IntegerRowMapper.class)
	@Query("select LAST_INSERT_ID()")
	int queryId();
}
