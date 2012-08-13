package org.expressme.examples.showcase.entity;

import java.util.Date;

import org.expressme.simplejdbc.core.ActiveRecord;

/**
 * Abstract base class for auditable entities. Stores the audition values in
 * persistent fields.
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractAuditable extends ActiveRecord {

	private int createdBy;
	private Date createdDate;
	private int lastModifiedBy;
	private Date lastModifiedDate;

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(final int createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(final Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(final int lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(final Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
}
