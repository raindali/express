package org.expressme.search;

public class SearchableBean {

    private String id;
    private String title;
    private String author;
    private String content;
    private String reserved;

    @SearchableId
    public String getId() {  return id; }
    public void setId(String id) { this.id = id; }

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

    @Override
    public String toString() {
        return new StringBuilder(2048)
                .append("SearchableBean\n[Id] ").append(id)
                .append("\n[Title] ").append(title)
                .append("\n[Author] ").append(author)
                .append("\n[Content] ").append(content)
                .append("\n[Reserved] ").append(reserved)
                .toString();
    }

    public boolean contains(String key) {
        key = key.toLowerCase();
        return title.toLowerCase().indexOf(key)!=(-1)
                || author.toLowerCase().indexOf(key)!=(-1)
                || content.toLowerCase().indexOf(key)!=(-1);
    }
}
