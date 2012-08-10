package org.expressme.modules.utils.html;

public interface HtmlHandler {

    boolean onText(String html);

    boolean onComment(String comment);

    boolean onStartTag(String tagName, String html);

    boolean onEndTag(String tagName, String html);

    boolean onEmptyTag(String tagName, String html);

}
