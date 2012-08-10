package org.expressme.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.index.LogMergePolicy;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopDocsCollector;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;
import org.expressme.common.Page;
import org.expressme.search.mapper.DocumentMapper;

/**
 * Implementation of searcher for a searchable type.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 * 
 * @param <T> Type of searchable bean.
 */
public class SearcherImpl<T> implements Searcher<T> {

    private final Log log = LogFactory.getLog(getClass());

    private Directory directory;
    private Analyzer analyzer;
    private Formatter formatter;
    private DocumentMapper<T> documentMapper;

    private IndexSearcher indexSearcher = null;
    private IndexWriter indexWriter = null;
    private MaxFieldLength maxFieldLength = MaxFieldLength.UNLIMITED;
    private SyncPolicy syncPolicy = new VersionSyncPolicy();

    private int mergeFactor = LogMergePolicy.DEFAULT_MERGE_FACTOR;
    private int maxKeywords = DEFAULT_MAX_KEYWORDS;
    private int maxNumHits = DEFAULT_MAX_NUMHITS;

    ////////////////////////////////////////////////////////////////////////////

    public SearcherImpl() {
    }

    ////////////////////////////////////////////////////////////////////////////

    public void setDirectory(Directory directory) {
        this.directory = directory;
    }

    public void setAnalyzer(Analyzer analyzer) {
        this.analyzer = analyzer;
    }

    public void setFormatter(Formatter formatter) {
        this.formatter = formatter;
    }

    public void setDocumentMapper(DocumentMapper<T> documentMapper) {
        this.documentMapper = documentMapper;
    }

    public void setSyncPolicy(SyncPolicy syncPolicy) {
        this.syncPolicy = syncPolicy;
    }

    public void setMergeFactor(int mergeFactor) {
        this.mergeFactor = mergeFactor;
    }

    public void setMaxKeywords(int maxKeywords) {
        this.maxKeywords = maxKeywords;
    }

    public void setMaxFieldLength(int maxFieldLength) {
        this.maxFieldLength = new MaxFieldLength(maxFieldLength);
    }

    ////////////////////////////////////////////////////////////////////////////

    /**
     * Init searcher. Thead-safe: NO.
     */
    public void init() {
        // check index:
        try {
            if ( ! IndexReader.indexExists(directory)) {
                log.info("Index is not exist. Try to create an EMPTY index.");
                new IndexWriter(directory, analyzer, true, maxFieldLength).close();
//                IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_33, analyzer);
//                conf.setOpenMode(OpenMode.CREATE);
//                new IndexWriter(directory, conf).close();
            }
            else {
                if (IndexWriter.isLocked(directory)) {
                    log.warn("Directory is locked! Try unlock!");
                    IndexWriter.unlock(directory);
                }
            }
            indexWriter = new IndexWriter(directory, analyzer, false, maxFieldLength);
            indexWriter.setMergeFactor(mergeFactor);
            indexSearcher = newIndexSearcher();
            log.info("Searcher init ok.");
        }
        catch (Exception e) {
            log.error("Check index failed.", e);
            throw new SearchConfigException("Check index failed.", e);
        }
    }

    /**
     * Close searcher.
     */
    public synchronized void close() {
        log.info("Destroy searcher: " + this);
        try {
            indexWriter.close();
        }
        catch (Exception e) {
            log.error("Close index modifier failed.", e);
        }
        finally {
            indexWriter = null;
        }
        IndexSearcher old = this.indexSearcher;
        this.indexSearcher = null;
        closeIndexSearcher(old);
    }

    public synchronized void index(T t) {
        try {
            indexWriter.addDocument(documentMapper.object2Document(t), analyzer);
            indexWriter.commit();
            syncPolicy.markModified();
        }
        catch (Exception e) {
            log.error("Index failed.", e);
            throw new IndexException("Index failed.", e);
        }
    }

    public void unindex(T t) {
        _unindex(new Term(documentMapper.getId(), documentMapper.getIdValue(t)));
    }

    public void unindex(String field, String value) {
        _unindex(new Term(field, value));
    }

    synchronized void _unindex(Term term) {
        try {
            indexWriter.deleteDocuments(term);
            indexWriter.commit();
            syncPolicy.markModified();
        }
        catch (Exception e) {
            log.error("Unindex failed.", e);
            throw new IndexException("Unindex failed.", e);
        }
    }

    public synchronized void reindex(T t) {
        try {
            indexWriter.updateDocument(new Term(documentMapper.getId(), documentMapper.getIdValue(t)), documentMapper.object2Document(t), analyzer);
            indexWriter.commit();
            syncPolicy.markModified();
        }
        catch (Exception e) {
            log.error("Reindex failed.", e);
            throw new IndexException("Reindex failed.", e);
        }
    }
    
    Page<T> processResult(Query query, int firstResult, int maxResults, boolean highlighted) {
    	TopDocsCollector<?> collector = TopScoreDocCollector.create(maxNumHits, false);
        try {
            indexSearcher.search(query, collector);
            TopDocs topDocs = collector.topDocs();
            int count = topDocs.totalHits;
            Page<T> page = new Page<T>(firstResult, maxResults);
            page.setTotalCount(count);
			if (count == 0) {
				List<T> list = Collections.emptyList();
				page.setList(list);
				return page;
			}
            ScoreDoc[] docs = topDocs.scoreDocs;
            List<T> list = new ArrayList<T>(maxResults);
            int endResult = firstResult + maxResults;
            if (endResult>count)
                endResult = count;
            Highlighter hl = highlighted ? new Highlighter(formatter, new QueryScorer(query)) : null;
            for(int i=firstResult; i<endResult; i++) {
                Document doc = indexSearcher.doc(docs[i].doc);
                T t = documentMapper.documentToObject(doc, analyzer, hl);
                list.add(t);
            }
            page.setList(list);
            return page;
        }
        catch (IOException e) {
            throw new SearchException(e);
        } catch (InvalidTokenOffsetsException e) {
        	throw new SearchException(e);
		}
    }

    /**
     * Do search with version check.
     */
    Page<T> doSearch(Query query, int firstResult, int maxResults, boolean highlighted) {
        if ( ! syncPolicy.isSync()) {
            synchronized (syncPolicy) {
                if ( ! syncPolicy.isSync()) {
                    IndexSearcher old = this.indexSearcher;
                    this.indexSearcher = newIndexSearcher();
                    closeIndexSearcher(old);
                    syncPolicy.markSynchronized();
                }
            }
        }
        return processResult(query, firstResult, maxResults, highlighted);
    }

    protected Query createQuery(String[] qs, String[] properties) {
        int max = qs.length > maxKeywords ? maxKeywords : qs.length;
        if(properties==null)
            properties = documentMapper.getSearchableProperties();
        BooleanQuery query = new BooleanQuery();
        try {
            for (int i=0; i<max; i++) {
                String q = qs[i];
                query.add(MultiFieldQueryParser.parse(Version.LUCENE_31, q, properties, documentMapper.getOccurs(properties.length), analyzer), Occur.SHOULD);
            }
        }
        catch (ParseException pe) {
            throw new SearchException(pe);
        }
        return query;
    }

    void checkPage(int firstResult, int maxResults) {
        if (firstResult<0 || maxResults<1)
            throw new OutOfRangeException();
    }

    public List<T> search(String q, String[] properties, int firstResult, int maxResults) {
        return search(q, properties, firstResult, maxResults, false);
    }

    public List<T> search(String q, String[] properties, int firstResult, int maxResults, boolean highlighted) {
        checkPage(firstResult, maxResults);
        String[] qs = q.trim().split("[ ]+");
        Query query = createQuery(qs, properties);
        return doSearch(query, firstResult, maxResults, highlighted).getList();
    }

    public List<T> search(String field, String value, int firstResult, int maxResults) {
        return search(field, value, firstResult, maxResults, false);
    }

    public List<T> search(String field, String value, int firstResult, int maxResults, boolean highlighted) {
        checkPage(firstResult, maxResults);
        Query query = new TermQuery(new Term(field, value));
        return doSearch(query, firstResult, maxResults, highlighted).getList();
    }

    ////////////////////////////////////////////////////////////////////////////

    /**
     * Open new index seacher. Thread-safe: NO.
     */
    IndexSearcher newIndexSearcher() {
        try {
            return new IndexSearcher(directory);
        }
        catch (Exception e) {
            log.error("Open index searcher failed.", e);
            throw new SearchException("Cannot open index searcher.");
        }
    }

    /**
     * Close index searcher. Thread-safe: NO.
     */
    void closeIndexSearcher(IndexSearcher searcher) {
        try {
            searcher.close();
        }
        catch(Exception e) {
            log.error("Close index searcher failed.", e);
            throw new SearchException("Cannot close index searcher.");
        }
    }

	@Override
	public void _index(T t) {
        try {
            indexWriter.addDocument(documentMapper.object2Document(t), analyzer);
        }
        catch (Exception e) {
            log.error("Index failed.", e);
            throw new IndexException("Index failed.", e);
        }
    }

	@Override
	public void _commit() {
        try {
            indexWriter.commit();
            syncPolicy.markModified();
        }
        catch (Exception e) {
            log.error("Commit failed.", e);
            throw new IndexException("Commit failed.", e);
        }
    }
	
	public List<T> _search(String q, String[] properties, Page<T> page) {
		return _search(q, properties, page, false);
	}

	public List<T> _search(String q, String[] properties, Page<T> page, boolean highlighted) {
		String[] qs = q.trim().split("[ ]+");
		Query query = createQuery(qs, properties);
		return _search(query, page, highlighted);
	}

	public List<T> _search(String field, String value, Page<T> page) {
		return _search(field, value, page, false);
	}

	public List<T> _search(String field, String value, Page<T> page, boolean highlighted) {
		Query query = new TermQuery(new Term(field, value));
		return _search(query, page, highlighted);
	}

	@Override
	public List<T> _search(Query query, Page<T> page) {
		return _search(query, page, false);
	}

	@Override
	public List<T> _search(Query query, Page<T> page, boolean highlighted) {
		Page<T> result = doSearch(query, page.getFirstResult(), page.getPageSize(), highlighted);
		page.setList(result.getList());
		page.setTotalCount(page.getTotalCount());
		return page.getList();
	}
}
