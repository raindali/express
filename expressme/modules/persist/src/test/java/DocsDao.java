

import java.util.List;

import org.expressme.persist.FirstResult;
import org.expressme.persist.MappedBy;
import org.expressme.persist.MaxResults;
import org.expressme.persist.Param;
import org.expressme.persist.Query;
import org.expressme.persist.Unique;
import org.expressme.persist.Update;
import org.expressme.persist.mapper.BeanRowMapper;

public interface DocsDao extends IdDao {
	static final String GOODS_ALL = "g.id, g.goodsId, g.userId, g.subject, g.docUrl, g.suffix, g.state, g.locks, g.addtime";

	@Unique
	@Query("select " + GOODS_ALL + " from Docs g where g.id=:id")
	int queryId(@Param("id") int id);

	@Unique
	@Query("select " + GOODS_ALL + " from Docs g where g.id=:id")
	Docs queryById(@Param("id") int id);

	@Query("select " + GOODS_ALL + " from Docs g where g.goodsId=:id")
	List<Docs> queryByGoodsId(@Param("id") int id);

	@Update("insert into Docs (goodsId, userId, subject, docUrl, suffix, state, locks) values (:g.goodsId, :g.userId, :g.subject, :g.docUrl, :g.suffix, :g.state, :g.locks)")
	int createDocs(@Param("g") Docs docs);

	@Update("update Docs set goodsId=:g.goodsId, userId=:g.userId, subject=:g.subject, docUrl=:g.docUrl, suffix=:g.suffix, state=:g.state, locks=:g.locks where id=:g.id")
	int updateDocs(@Param("g") Docs docs);

	@Update("update Docs set docUrl=:url, suffix=:suffix where id=:id")
	int updateDocs4Url(@Param("url") String url, @Param("suffix") String suffix, @Param("id") int id);

	@Update("delete from Docs where id=:g.id")
	int deleteDocs(@Param("g") Docs docs);

	@Query("select " + GOODS_ALL + " from Docs g where g.subject like :subject")
	List<Docs> queryByArgs(@FirstResult int firstResult, @MaxResults int maxResults, @Param("subject") String subject);
}
