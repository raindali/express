package org.expressme.examples.showcase.web;

/**
 * Store setting key.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public interface Settings {

    static final String SITE_BASIC = "site.basic";
    static final String SITE_BASIC_OWNER = "siteOwner";
    static final String SITE_BASIC_TITLE = "siteTitle";
    static final String SITE_BASIC_SUBTITLE = "siteSubTitle";

    static final String DEFAULT_SITE_OWNER = "ExpressMe";
    static final String DEFAULT_SITE_TITLE = "ExpressMe";
    static final String DEFAULT_SITE_SUBTITLE = "Express Yourself";

    static final String SITE_FORMAT = "site.format";
    static final String SITE_FORMAT_POSTS = "formatPosts";
    static final String SITE_FORMAT_TIMEZONE = "formatTimeZone";
    static final String SITE_FORMAT_LOCALE = "formatLocale";
    static final String SITE_FORMAT_DATE = "formatDate";
    static final String SITE_FORMAT_TIME = "formatTime";

    static final int DEFAULT_FORMAT_POSTS = 10;

    static final String SITE_COMMENTS = "site.comments";
    static final String SITE_COMMENTS_SHOW = "commentsShow";
    static final String SITE_COMMENTS_ALLOW = "commentsAllow";

    static final String[] VALID_COMMENTS_SHOW = { "true", "false" };
    static final String[] VALID_COMMENTS_ALLOW = { "everyone", "users", "none" };

    static final String SITE_FEED = "site.feed";
    static final String SITE_FEED_POSTS = "feedPosts";
    static final String SITE_FEED_SHOW = "feedShow";
    static final String SITE_FEED_PROXY = "feedProxy";

    static final String[] VALID_FEED_POSTS = { "10", "20", "30", "50" };
    static final String[] VALID_FEED_SHOW = { "full", "abstract" };

    static final int DEFAULT_FEED_POSTS = 20;
}
