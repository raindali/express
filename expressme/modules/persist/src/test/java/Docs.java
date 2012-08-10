/**
 * 
 */


import java.util.Date;

/**
 * @author xiaoli
 * 商品信息数据表
 */
public class Docs extends AbstractId {
	private int goodsId;
	private int userId;
	private String subject;
	private String docUrl;
	// 文档后缀
	private String suffix;
	private int state;
	private int locks;
	private Date addtime;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDocUrl() {
		return docUrl;
	}

	public void setDocUrl(String docUrl) {
		this.docUrl = docUrl;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getLocks() {
		return locks;
	}

	public void setLocks(int lock) {
		this.locks = lock;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
}
