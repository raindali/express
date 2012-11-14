package org.expressme.persist.common;

import java.util.Collections;
import java.util.List;

/**
 * Page object is used to store and fetch page information.
 * 
 * @author xuefeng
 */
public class Page<T> {

    public static final int DEFAULT_PAGE_SIZE = 20;

    private int pageIndex;
    private int pageSize;
    private int totalCount;
    private int pageCount;
    private List<T> list;
    /**
     * Specify page index (starts from 1) and page size.
     * 
     * @param pageIndex Starts from 1.
     * @param pageSize How many items a page can display.
     */
    public Page(int pageIndex, int pageSize) {
        // check:
        if(pageIndex<1 || pageSize<1 || pageSize>100)
            throw new IllegalArgumentException();
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.list = Collections.emptyList();
    }

    /**
     * Specify page index (starts from 1) and default page size (20).
     * 
     * @param pageIndex Starts from 1.
     */
    public Page(int pageIndex) {
        this(pageIndex, DEFAULT_PAGE_SIZE);
    }

    /**
     * Get page index.
     */
    public int getPageIndex() { return pageIndex; }

    /**
     * Get page size.
     */
    public int getPageSize() { return pageSize; }

    /**
     * Get how many pages.
     */
    public int getPageCount() { return pageCount; }

    /**
     * Get how many items in total.
     */
    public int getTotalCount() { return totalCount; }

    /**
     * Get index of the first item in this page.
     */
    public int getFirstResult() { return (pageIndex - 1) * pageSize; }

    /**
     * Get index of the last item in this page.
     */
    public int getLastResult() {
        if(totalCount==0)
            return 0;
        int last = (pageIndex) * pageSize-1;
        return last>=totalCount ? totalCount-1 : last;
    }

    /**
     * Has previous page?
     */
    public boolean getHasPrevious() { return pageIndex>1; }

    /**
     * Has next page?
     */
    public boolean getHasNext() { return pageIndex<pageCount; }

    /**
     * Set a total count of items.
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        pageCount = totalCount / pageSize + (totalCount % pageSize == 0 ? 0 : 1);
        // adjust pageIndex:
        if(totalCount==0) {
            if(pageIndex!=1)
                throw new IndexOutOfBoundsException("Page index out of range.");
        }
        else {
            if(pageIndex>pageCount)
                throw new IndexOutOfBoundsException("Page index out of range.");
        }
    }

    /**
     * Is there any items? It is equal of getTotalCount()==0.
     */
    public boolean isEmpty() {
        return totalCount==0;
    }

	/**
	 * @param list the list to set
	 */
	public void setList(List<T> list) {
		this.list = list;
	}

	/**
	 * @return the list
	 */
	public List<T> getList() {
		return list;
	}

}
