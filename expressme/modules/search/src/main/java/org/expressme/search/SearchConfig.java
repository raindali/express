package org.expressme.search;

import java.io.File;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.expressme.search.converter.Converter;
import org.expressme.search.converter.ConverterFactory;

/**
 * Configuration for Lucene search engine.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class SearchConfig {

    public static final String PROP_INDEX_ANALYZER     = "index.analyzer";
    public static final String PROP_HIGHLIGHT_ANALYZER = "highlight.analyzer";
    public static final String PROP_HIGHLIGHT_PRE      = "highlight.pre";
    public static final String PROP_HIGHLIGHT_POST     = "highlight.post";
    public static final String PROP_INDEX_LOCATION     = "index.location";
    public static final String PROP_MERGE_FACTOR       = "merge.factor";
    public static final String PROP_SEARCHABLE_CLASSES = "searchable.classes";

    static final String DEFAULT_ANALYZER = StandardAnalyzer.class.getName();

    Set<Class<?>> classes = new HashSet<Class<?>>();
    String indexAnalyzer = DEFAULT_ANALYZER;
    String highlightAnalyzer = DEFAULT_ANALYZER;
    String highlightPre = "<b>";
    String highlightPost = "</b>";
    String indexLocation = null;
    int mergeFactor = 10;

    final Log log = LogFactory.getLog(getClass());

    public SearchConfig() {
    }

    /**
     * Set all settings from properties file. e.g.<br>
     * <pre>
     * # properties file:
     * index.location = C:/index_location/
     * searchable.classes = com.test.A, com.testB, com.testC
     * index.analyzer = org.apache.lucene.analysis.cn.ChineseAnalyzer
     * highlight.analyzer = org.apache.lucene.analysis.cn.ChineseAnalyzer
     * merge.factor = 10
     * highlight.pre = &lt;span class='hl'&gt;
     * highlight.post = &lt;/span&gt;
     * </pre>
     */
    public SearchConfig addProperties(Properties properties) {
        String v = null;
        v = properties.getProperty(PROP_INDEX_ANALYZER);
        if (v!=null)
            setIndexAnalyzer(v);
        v = properties.getProperty(PROP_HIGHLIGHT_ANALYZER);
        if (v!=null)
            setHighlightAnalyzer(v);
        v = properties.getProperty(PROP_HIGHLIGHT_PRE);
        if (v!=null)
            setHighlightPre(v);
        v = properties.getProperty(PROP_HIGHLIGHT_POST);
        if (v!=null)
            setHighlightPost(v);
        v = properties.getProperty(PROP_INDEX_LOCATION);
        if (v!=null)
            setIndexLocation(v);
        v = properties.getProperty(PROP_MERGE_FACTOR);
        if (v!=null)
            setMergeFactor(Integer.parseInt(v));
        v = properties.getProperty(PROP_SEARCHABLE_CLASSES);
        if (v!=null) {
            String[] cls = v.split("\\,");
            for (String c : cls) {
                if (c.trim().length()>0)
                    addClass(c);
            }
        }
        return this;
    }

    public String getIndexLocation() {
        return this.indexLocation;
    }

    /**
     * Set index location directory.
     * 
     * @throws SearchConfigException If specified directory cannot found or created.
     * 
     * @spring.property value="/WEB-INF/index"
     */
    public void setIndexLocation(String location) {
        File file = new File(notNull(location));
        if(file.isFile())
            throw new SearchConfigException("Must specify index location as a directory rather than a file: " + indexLocation);
        if(!file.isDirectory()) {
            if(!file.mkdirs())
                throw new SearchConfigException("Try to create index directory failed: " + indexLocation);
        }
        this.indexLocation = file.getPath();
    }

    public int getMergeFactor() {
        return this.mergeFactor;
    }

    /**
     * Set merge factor. Range: 2 to 40, default to 10. The larger factor is, 
     * the more memory is used during runtime.
     * 
     * @throws SearchConfigException If merge factor is less than 2 or more than 40.
     */
    public void setMergeFactor(int mergeFactor) {
        if(mergeFactor<2)
            throw new SearchConfigException("MergeFactor must be greeter than 2.");
        if(mergeFactor>40)
            log.warn("Set MergeFactor with large value may cause OutOfMemoryError during runtime: " + mergeFactor);
        this.mergeFactor = mergeFactor;
    }

    /**
     * Add a searchable class with annotation.
     */
    public SearchConfig addClass(String className) {
        try {
            return addClass(Class.forName(className));
        }
        catch(ClassNotFoundException e) {
            throw new SearchConfigException("Class not found: " + className, e);
        }
    }

    /**
     * Add a searchable class with annotation.
     */
    public SearchConfig addClass(Class<?> searchableClass) {
        classes.add(notNull(searchableClass));
        return this;
    }

    public Class<?>[] getSearchableClasses() {
        return classes.toArray(new Class<?>[classes.size()]);
    }

    /**
     * Add converters.
     */
    public SearchConfig addConverter(Class<?> clazz, Converter converter) {
        ConverterFactory.getInstance().register(clazz, converter);
        return this;
    }

    /**
     * Set searchable classes.
     * 
     * @spring.property list="org.expressme.search.entity.SearchableItem"
     */
    public void setSearchableClasses(Class<?>[] searchableClasses) {
        for (Class<?> clazz : searchableClasses)
            addClass(clazz);
    }

    public String getHighlightPre() {
        return this.highlightPre;
    }

    /**
     * Set highlight pre, default to "&lt;b&gt;".
     * 
     * @spring.property value="&lt;span class=&quot;hl&quot;&gt;"
     */
    public void setHighlightPre(String highlightPre) {
        this.highlightPre = notNull(highlightPre);
    }

    public String getHighlightPost() {
        return this.highlightPost;
    }

    /**
     * Set highlight post, default to "&lt;/b&gt;".
     * 
     * @spring.property value="&lt;/span>"
     */
    public void setHighlightPost(String highlightPost) {
        this.highlightPost = notNull(highlightPost);
    }

    public String getIndexAnalyzer() {
        return this.indexAnalyzer;
    }

    /**
     * Set analyzer for index. default to value of "StandardAnalyzer.class.getName()".
     * 
     * @spring.property value="org.apache.lucene.analysis.cn.ChineseAnalyzer"
     */
    public void setIndexAnalyzer(String indexAnalyzer) {
        this.indexAnalyzer = notNull(indexAnalyzer);
    }

    public String getHighlightAnalyzer() {
        return this.highlightAnalyzer;
    }

    /**
     * Set analyzer for highlight. default to value of "StandardAnalyzer.class.getName()".
     * 
     * @spring.property value="org.apache.lucene.analysis.cn.ChineseAnalyzer"
     */
    public void setHighlightAnalyzer(String highlightAnalyzer) {
        this.highlightAnalyzer = highlightAnalyzer;
    }

    <T> T notNull(T t) {
        if(t==null)
            throw new NullPointerException("Assert failed: Object reference is NULL.");
        return t;
    }

}
