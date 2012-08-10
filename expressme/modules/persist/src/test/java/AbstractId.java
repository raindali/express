

import java.io.Serializable;

/**
 * Base class for all entity to provide ID.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
@SuppressWarnings("serial")
public abstract class AbstractId implements Serializable {
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
