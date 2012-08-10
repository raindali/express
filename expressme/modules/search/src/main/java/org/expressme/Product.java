package org.expressme;

import org.expressme.search.Index;
import org.expressme.search.SearchableProperty;
import org.expressme.search.Store;

public class Product {

	private int id;
	private String name;
	@SearchableProperty(index=Index.NOT_ANALYZED, boost=1.0f, store=Store.YES)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
