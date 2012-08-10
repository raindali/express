package org.expressme.examples.showcase.search;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.expressme.search.Searcher;
import org.expressme.webwind.Destroyable;

import com.google.inject.Inject;

/**
 * Search facade.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class SearchFacade implements Destroyable {

    private final Log log = LogFactory.getLog(getClass());

    @Inject Searcher<SearchableItem> searcher;

    /**
     * Destroy internal searcher when application stop.
     */
    public void destroy() {
        searcher.close();
    }

    public List<SearchableItem> search(String q, int first, int max) {
        return searcher.search(q, first, max, true);
    }

    public void index(SearchableItem item) {
        log.info("Index item: " + item.getTitle());
        searcher.index(item);
    }

    public void unindex(String id) {
        log.info("Unindex item: " + id);
        searcher.unindex("id", id);
    }

    public void reindex(SearchableItem item) {
        log.info("Reindex item: " + item.getTitle());
        searcher.reindex(item);
    }

}
