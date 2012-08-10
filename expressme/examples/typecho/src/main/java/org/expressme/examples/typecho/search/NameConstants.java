package org.expressme.examples.typecho.search;

public interface NameConstants {

    static final String SEARCH_INTERNAL_ANALYZER = "search.internal.analyzer";
//    static final String SEARCH_INTERNAL_ANALYZER_DEFAULT = "org.apache.lucene.analysis.standard.StadardAnalyzer";
    static final String SEARCH_INTERNAL_ANALYZER_DEFAULT = "org.wltea.analyzer.lucene.IKAnalyzer";

    static final String SEARCH_INTERNAL_HIGHLIGHTER_PREFIX = "search.internal.highlighter.prefix";
    static final String SEARCH_INTERNAL_HIGHLIGHTER_PREFIX_DEFAULT = "<span class=\"hl\">";

    static final String SEARCH_INTERNAL_HIGHLIGHTER_SUFFIX = "search.internal.highlighter.suffix";
    static final String SEARCH_INTERNAL_HIGHLIGHTER_SUFFIX_DEFAULT = "</span>";

}
