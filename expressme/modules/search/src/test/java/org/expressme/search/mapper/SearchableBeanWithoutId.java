package org.expressme.search.mapper;

import org.expressme.search.Index;
import org.expressme.search.SearchableProperty;
import org.expressme.search.Store;

public class SearchableBeanWithoutId {

    private String title;
    private String author;
    private String content;
    private String reserved;

    @SearchableProperty(index=Index.ANALYZED, store=Store.YES)
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    @SearchableProperty(index=Index.ANALYZED, store=Store.YES)
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    @SearchableProperty(index=Index.ANALYZED, store=Store.YES)
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    @SearchableProperty(index=Index.NO, store=Store.YES)
    public String getReserved() { return reserved; }
    public void setReserved(String reserved) { this.reserved = reserved; }

}
