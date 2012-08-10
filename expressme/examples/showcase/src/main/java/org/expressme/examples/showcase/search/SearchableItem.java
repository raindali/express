package org.expressme.examples.showcase.search;

import org.expressme.examples.showcase.entity.CityBus;
import org.expressme.search.Index;
import org.expressme.search.SearchableId;
import org.expressme.search.SearchableProperty;
import org.expressme.search.Store;

/**
 * Search for entity of 'Item'.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class SearchableItem {

    private String id;
    private String title;
    private String content;

    public SearchableItem() {}

    public SearchableItem(CityBus item) {
        this.id = item.getId();
        this.title = item.getName();
        this.content = item.getRoute();
    }

    @SearchableId
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    @SearchableProperty(index=Index.ANALYZED, store=Store.YES, boost=5.0f)
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    @SearchableProperty(index=Index.ANALYZED, store=Store.YES)
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

}
