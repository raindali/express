package org.expressme.search;

import java.util.List;

import org.apache.lucene.search.Query;
import org.expressme.common.Page;

/**
 * Searcher interface used to search in object's searchable fields.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public interface Searcher<T> {

    static int DEFAULT_MAX_KEYWORDS = 10;
    
    static int DEFAULT_MAX_NUMHITS = 500;

    /**
     * Init the searcher.
     */
    void init();

    /**
     * Close the searcher.
     */
    void close();

    /**
     * Search keywords in specified properties.
     * 
     * @param q Keywords represent as string and seperated by space.
     * @param properties Properties to search.
     * @param firstResult First result index, starts from 0.
     * @param maxResults Max results per page.
     * @return A List of strong-type of T.
     */
    List<T> search(String q, String[] properties, int firstResult, int maxResult);

    /**
     * Search keywords in specified properties with highlighted results.
     * 
     * @param q Keywords represent as string and seperated by space.
     * @param properties Properties to search.
     * @param firstResult First result index, starts from 0.
     * @param maxResults Max results per page.
     * @param highlighted If highlight search text.
     * @return A List of strong-type of T.
     */
    List<T> search(String q, String[] properties, int firstResult, int maxResult, boolean highlighted);

    /**
     * Search using term by given key-value pair.
     * 
     * @param clazz Searchable bean type.
     * @param field Field to search.
     * @param value Field value to search.
     * @param firstResult First result index, starts from 0.
     * @param maxResults Max results per page.
     * @return A List of strong-type of T.
     */
    List<T> search(String field, String value, int firstResult, int maxResult);

    /**
     * Search using term by given key-value pair with highlighted results.
     * 
     * @param field Field to search.
     * @param value Field value to search.
     * @param firstResult First result index, starts from 0.
     * @param maxResults Max results per page.
     * @param highlighted If highlight search text.
     * @return A List of strong-type of T.
     */
    List<T> search(String field, String value, int firstResult, int maxResult, boolean highlighted);

    /**
     * Search keywords in specified properties.
     * 
     * @param q Keywords represent as string and seperated by space.
     * @param properties Properties to search.
	 * @param page Page object is used to store and fetch page information.
     * @return A List of strong-type of T.
     */
    List<T> _search(String q, String[] properties, Page<T> page);

    /**
     * Search keywords in specified properties with highlighted results.
     * 
     * @param q Keywords represent as string and seperated by space.
     * @param properties Properties to search.
	 * @param page Page object is used to store and fetch page information.
     * @param highlighted If highlight search text.
     * @return A List of strong-type of T.
     */
    List<T> _search(String q, String[] properties, Page<T> page, boolean highlighted);

    /**
     * Search using term by given key-value pair.
     * 
     * @param clazz Searchable bean type.
     * @param field Field to search.
     * @param value Field value to search.
	 * @param page Page object is used to store and fetch page information.
     * @return A List of strong-type of T.
     */
    List<T> _search(String field, String value, Page<T> page);

    /**
     * Search using term by given key-value pair with highlighted results.
     * 
     * @param field Field to search.
     * @param value Field value to search.
	 * @param page Page object is used to store and fetch page information.
     * @param highlighted If highlight search text.
     * @return A List of strong-type of T.
     */
    List<T> _search(String field, String value, Page<T> page, boolean highlighted);

    /**
     * Search using term by given query and page with highlighted results.
     * 
     * @param query Query to search.
     * @param page Page object is used to store and fetch page information.
     * @param highlighted If highlight search text.
     * @return A List of strong-type of T.
     */
    List<T> _search(Query query, Page<T> page);
    /**
     * Search using term by given query and page with highlighted results.
     * 
     * @param query Query to search.
     * @param page Page object is used to store and fetch page information.
     * @param highlighted If highlight search text.
     * @return A List of strong-type of T.
     */
    List<T> _search(Query query, Page<T> page, boolean highlighted);

    /**
     * Index an object.
     * 
     * @param t Searchable bean object instance.
     */
    void index(T t);

    /**
     * Unindex an object. The object can only provides property with @SearchableId.
     * 
     * @param t Searchable bean object instance.
     */
    void unindex(T t);

    /**
     * Unindex objects by given field-value pair.
     * 
     * @param field Object field name.
     * @param value Object field value.
     */
    void unindex(String field, String value);

    /**
     * Reindex an object.
     * 
     * @param t Searchable bean object instance.
     */
    void reindex(T t);
    
    /**
     * Index an object(For Batch).
     * 
     * @param t Searchable bean object instance.
     */
    void _index(T t);

    /**
     * Commit bean object instance.
     */   
    void _commit();

}
