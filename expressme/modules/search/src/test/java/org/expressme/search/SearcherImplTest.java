//package org.expressme.search;
//
//import static org.junit.Assert.*;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.List;
//
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
//import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
//import org.apache.lucene.store.RAMDirectory;
//import org.apache.lucene.util.Version;
//import org.expressme.search.mapper.DocumentMapper;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//public class SearcherImplTest {
//
//    private SearcherImpl<SearchableBean> searcher = null;
//
//    @Before
//    public void setUp() throws Exception {
//        searcher = new SearcherImpl<SearchableBean>();
//        searcher.setAnalyzer(new StandardAnalyzer(Version.LUCENE_31));
//        searcher.setDirectory(new RAMDirectory());
//        searcher.setDocumentMapper(new DocumentMapper<SearchableBean>(SearchableBean.class));
//        searcher.setFormatter(new SimpleHTMLFormatter("<b>", "</b>"));
//        searcher.init();
//        SearchableBean[] beans = loadSearchableBeans();
//        for (SearchableBean bean : beans) {
//            searcher.index(bean);
//        }
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        searcher.close();
//    }
//
//    @Test
//    public void testUnindexById() throws Exception {
//        List<SearchableBean> list = searcher.search("google", 0, 10);
//        assertFalse(list.isEmpty());
//        for (SearchableBean bean : list) {
//            assertTrue(bean.contains("google"));
//            searcher.unindex(bean);
//        }
//        // search again:
//        list = searcher.search("google", 0, 10);
//        assertTrue(list.isEmpty());
//    }
//
//    @Test
//    public void testUnindexByToken() throws Exception {
//        List<SearchableBean> list = searcher.search("google", 0, 10);
//        assertFalse(list.isEmpty());
//        for (SearchableBean bean : list) {
//            assertTrue(bean.contains("google"));
//            searcher.unindex("id", bean.getId());
//        }
//        // search again:
//        list = searcher.search("google", 0, 10);
//        assertTrue(list.isEmpty());
//    }
//
//    @Test
//    public void testReindex() throws Exception {
//        List<SearchableBean> list = searcher.search("google", 0, 10);
//        assertFalse(list.isEmpty());
//        for (SearchableBean bean : list) {
//            System.out.println(bean);
//            assertTrue(bean.contains("google"));
//            bean.setTitle(bean.getTitle().toLowerCase().replace("google", "microsoft"));
//            bean.setContent(bean.getContent().toLowerCase().replace("google", "microsoft"));
//            searcher.reindex(bean);
//        }
//        // search again:
//        assertTrue(searcher.search("google", 0, 10).isEmpty());
//        // search new keyword:
//        List<SearchableBean> list2 = searcher.search("microsoft", 0, 10);
//        assertFalse(list2.isEmpty());
//        assertTrue(list2.size()==list.size());
//    }
//
//    @Test
//    public void testSearchWithHighlighted() {
//        List<SearchableBean> list = searcher.search("google", 0, 10, true);
//        assertFalse(list.isEmpty());
//        for (SearchableBean bean : list) {
//            assertTrue(bean.contains("<b>google</b>"));
//        }
//    }
//
//    @Test
//    public void testSearchInProperties() {
//        List<SearchableBean> list = searcher.search("google", new String[] { "title", "content" }, 0, 10);
//        assertFalse(list.isEmpty());
//        for (SearchableBean bean : list) {
//            assertTrue(bean.getTitle().toLowerCase().indexOf("google")!=(-1)
//                    || bean.getContent().toLowerCase().indexOf("google")!=(-1));
//        }
//    }
//
//    @Test
//    public void testSearchNotAnalyzedProperty() {
//        assertTrue(searcher.search("reserved", 0, 10).isEmpty());
//    }
//
//    SearchableBean[] loadSearchableBeans() throws IOException {
//        final int MAX = 5;
//        SearchableBean[] beans = new SearchableBean[MAX];
//        for (int i=0; i<MAX; i++) {
//            SearchableBean bean = new SearchableBean();
//            InputStream input = getClass().getClassLoader().getResourceAsStream(SearchableBean.class.getName().replace('.', '/') + i + ".txt");
//            BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
//            bean.setId(reader.readLine());
//            bean.setTitle(reader.readLine());
//            bean.setAuthor(reader.readLine());
//            bean.setContent(reader.readLine());
//            bean.setReserved(reader.readLine());
//            reader.close();
//            beans[i] = bean;
//        }
//        return beans;
//    }
//}
