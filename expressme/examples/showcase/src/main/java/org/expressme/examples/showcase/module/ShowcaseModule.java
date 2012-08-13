package org.expressme.examples.showcase.module;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.FSDirectory;
import org.expressme.examples.showcase.search.NameConstants;
import org.expressme.examples.showcase.search.SearchFacade;
import org.expressme.examples.showcase.search.SearchableItem;
import org.expressme.examples.showcase.web.interceptor.BindAndValidatorInterceptor;
import org.expressme.examples.showcase.web.interceptor.IdentityInterceptor;
import org.expressme.examples.showcase.web.manager.FetchIdentityImpl;
import org.expressme.modules.persist.DaoAccessor;
import org.expressme.modules.persist.DaoProxyFactory;
import org.expressme.modules.utils.ContextUtils;
import org.expressme.modules.web.WebScanner;
import org.expressme.modules.web.security.CookieIdentityManager;
import org.expressme.modules.web.security.FetchIdentity;
import org.expressme.modules.web.security.IdentityManager;
import org.expressme.persist.DaoFactory;
import org.expressme.persist.dialect.MySQLDialect;
import org.expressme.search.Searcher;
import org.expressme.search.SearcherImpl;
import org.expressme.search.mapper.DocumentMapper;
import org.expressme.simplejdbc.datasource.DriverManagerDataSource;
import org.expressme.simplejdbc.datasource.TransactionManager;
import org.expressme.webwind.guice.ServletContextAware;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;

/**
 * Guice module for IoC, with AppEngine detection.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class ShowcaseModule implements Module, ServletContextAware {
	private final Log log = LogFactory.getLog(getClass());
	private ServletContext servletContext;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public void configure(Binder binder) {
		binder.bind(ServletContext.class).toInstance(this.servletContext);
		configureDao(binder);
		configureWeb(binder);
		configureSearch(binder);
	}

	void configureSearch(Binder binder) {
		binder.bind(SearchFacade.class).asEagerSingleton();
		binder.bind(new TypeLiteral<Searcher<SearchableItem>>() {
		}).toInstance(createSearcherImpl());
	}

	Searcher<SearchableItem> createSearcherImpl() {
		SearcherImpl<SearchableItem> s = new SearcherImpl<SearchableItem>();
		Properties properties = new Properties();
		s.setAnalyzer(getAnalyzer(properties));
		s.setDirectory(getDirectory(servletContext));
		s.setDocumentMapper(new DocumentMapper<SearchableItem>(SearchableItem.class));
		s.setFormatter(new SimpleHTMLFormatter(properties.getProperty(NameConstants.SEARCH_INTERNAL_HIGHLIGHTER_PREFIX,
				NameConstants.SEARCH_INTERNAL_HIGHLIGHTER_PREFIX_DEFAULT), properties.getProperty(
				NameConstants.SEARCH_INTERNAL_HIGHLIGHTER_SUFFIX,
				NameConstants.SEARCH_INTERNAL_HIGHLIGHTER_SUFFIX_DEFAULT)));
		s.init();
		return s;
	}

	Analyzer getAnalyzer(Properties properties) {
		String className = properties.getProperty(NameConstants.SEARCH_INTERNAL_ANALYZER,
				NameConstants.SEARCH_INTERNAL_ANALYZER_DEFAULT);
		try {
			return (Analyzer) Class.forName(className).newInstance();
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	FSDirectory getDirectory(ServletContext servletContext) {
		try {
			File dir = new File(servletContext.getRealPath("/WEB-INF/search"));
			if (!dir.isDirectory()) {
				if (!dir.mkdirs())
					throw new IOException("Cannot create dir: " + dir.getPath());
			}
			return FSDirectory.open(new File(dir.getPath()));
		} catch (IOException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	void configureDao(Binder binder) {
		// bind dao
		String driverClass = ContextUtils.getProperty("jdbc.driverClass");
		String url = ContextUtils.getProperty("jdbc.url");
		String username = ContextUtils.getProperty("jdbc.username");
		String password = ContextUtils.getProperty("jdbc.password");
		DataSource dataSource = new DriverManagerDataSource(driverClass, url, username, password);
		TransactionManager txManager = new TransactionManager(dataSource);
		DaoFactory daoFactory = new DaoFactory(new MySQLDialect());
		binder.bind(TransactionManager.class).toInstance(txManager);
		binder.bind(DaoFactory.class).toInstance(daoFactory);
		binder.bind(DaoProxyFactory.class).asEagerSingleton();
		binder.bind(DaoAccessor.class).asEagerSingleton();
	}

	void configureWeb(Binder binder) {
		// bind web:
		binder.bind(FetchIdentity.class).to(FetchIdentityImpl.class).asEagerSingleton();
		binder.bind(IdentityManager.class).to(CookieIdentityManager.class).asEagerSingleton();
		binder.bind(IdentityInterceptor.class).asEagerSingleton();
		binder.bind(BindAndValidatorInterceptor.class).asEagerSingleton();
//		WebScanner scanner = new WebScanner();
		WebScanner.scanning(binder, "org.expressme.examples.showcase.web");
	}
}
