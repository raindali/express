package org.expressme.modules.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;

import org.expressme.modules.utils.html.HtmlHandler;
import org.expressme.modules.utils.html.HtmlReader;

/**
 * Utils for html operations.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class HtmlUtils {

    public static String encodeJsString(String text) {
        if (text==null || text.length()==0)
            return "";
        StringBuilder sb = new StringBuilder(text.length() + 20);
        boolean changed = false;
        for (int i=0; i<text.length(); i++) {
            char c = text.charAt(i);
            switch (c) {
            case '\"':
                changed = true;
                sb.append("\\\"");
                break;
            case '\'':
                changed = true;
                sb.append("\\\'");
                break;
            case '\\':
                changed = true;
                sb.append("\\\\");
                break;
            case '\r':
                changed = true;
                sb.append("\\r");
                break;
            case '\n':
                changed = true;
                sb.append("\\n");
                break;
            default:
                sb.append(c);
            }
        }
        return changed ? sb.toString() : text;
    }

    public static String encodeHtml(String text) {
        if (text==null || text.length()==0)
            return "";
        StringBuilder sb = new StringBuilder(text.length() + 20);
        boolean changed = false;
        for (int i=0; i<text.length(); i++) {
            char c = text.charAt(i);
            switch (c) {
            case '\"':
                changed = true;
                sb.append("&quot;");
                break;
            case '<':
                changed = true;
                sb.append("&lt;");
                break;
            case '>':
                changed = true;
                sb.append("&gt;");
                break;
            case '&':
                changed = true;
                sb.append("&amp;");
                break;
            default:
                sb.append(c);
            }
        }
        return changed ? sb.toString() : text;
    }

    /**
     * Remove all script and ActiveX tags.
     * @param html
     * @return Safe html.
     */
    public static String safeHtml(String html) {
        if(html==null)
            return "";
        int count = 0;
        StringBuilder original = new StringBuilder(html);
        StringBuilder lowercase = new StringBuilder(html.toLowerCase());
        for (;;) {
            if (removeTag(original, lowercase, "<script", "</script>"))
                count++;
            else
                break;
        }
        for (;;) {
            if (removeTag(original, lowercase, "<object", "</object>"))
                count++;
            else
                break;
        }
        for (;;) {
            if (removeTag(original, lowercase, "<iframe", "</iframe>"))
                count++;
            else
                break;
        }
        if(count==0) // no tags found, just return the original String!
            return html;
        return original.toString();
    }

    static boolean removeTag(StringBuilder original, StringBuilder lowercase, String startTag, String endTag) {
        int start = lowercase.indexOf(startTag);
        if (start==(-1))
            return false;
        int from = start + startTag.length();
        int end = lowercase.indexOf(endTag, from);
        if (end==(-1)) {
            end = lowercase.indexOf("/>", from);
            endTag = "/>";
        }
        if (end==(-1)) {
            // do not find end tag, so delete from start to end of string:
            lowercase.delete(start, lowercase.length());
            original.delete(start, original.length());
        }
        else {
            // delete from <start tag> to <end tag>:
            lowercase.delete(start, end + endTag.length());
            original.delete(start, end + endTag.length());
        }
        return true;
    }

    public static String extractSummary(String html, final int maxLength) {
        if (html==null)
            return "";
        if (html.length()<=maxLength)
            return html;
        final StringBuilder buffer = new StringBuilder(maxLength);
        final Stack<String> stack = new Stack<String>();
        new HtmlReader(html).parse(
                new HtmlHandler() {

                    int sizeLeft = maxLength;

                    public boolean onComment(String comment) {
                        return true;
                    }

                    public boolean onEmptyTag(String tagName, String html) {
                        if (html.length()<=sizeLeft) {
                            buffer.append(html);
                            sizeLeft -= html.length();
                            return true;
                        }
                        return false;
                    }

                    public boolean onEndTag(String tagName, String html) {
                        // check stack:
                        String top = stack.pop();
                        if (top.equals(tagName)) {
                            buffer.append("</").append(top).append('>');
                            // we do not reduce var 'sizeLeft' because it is 
                            // already reduced when onStartTag()!
                            return true;
                        }
                        // not match with stack, so fill all and end:
                        buffer.append("</").append(top).append('>');
                        while (!stack.isEmpty()) {
                            buffer.append("</").append(stack.pop()).append('>');
                        }
                        return false;
                    }

                    public boolean onStartTag(String tagName, String html) {
                        // we must reduce the length of end tag:
                        int endTagLength = tagName.length() + 3;
                        if (html.length() + endTagLength <= sizeLeft) {
                            buffer.append(html);
                            sizeLeft = sizeLeft - html.length() - endTagLength;
                            stack.push(tagName);
                            return true;
                        }
                        return false;
                    }

                    public boolean onText(String html) {
                        if (html.length()<=sizeLeft) {
                            buffer.append(html);
                            sizeLeft -= html.length();
                            return true;
                        }
                        buffer.append(html.substring(0, sizeLeft));
                        return false;
                    }
                }
        );
        // now fill all end tags:
        while (!stack.isEmpty()) {
            buffer.append("</").append(stack.pop()).append('>');
        }
        return buffer.toString();
    }

    public static String text2html(String text) {
        if (text==null)
            return "";
        StringBuilder sb = new StringBuilder(text.length() + 32);
        StringTokenizer st = new StringTokenizer(text, "\n");
        while (st.hasMoreTokens()) {
            String line = st.nextToken().trim();
            if (line.length()==0)
                continue;
            sb.append("<p>").append(encodeHtml(line)).append("</p>");
        }
        return sb.toString();
    }

    public static String html2text(String html) {
        if (html==null)
            return "";
        final StringBuilder buffer = new StringBuilder(html.length());
        new HtmlReader(html).parse(
                new HtmlHandler() {
                    boolean isPre = false;

                    public boolean onComment(String comment) {
                        return true;
                    }

                    public boolean onEmptyTag(String tagName, String html) {
                        if ("pre".equals(tagName))
                            return true;
                        return onStartTag(tagName, html);
                    }

                    public boolean onEndTag(String tagName, String html) {
                        if ("pre".equals(tagName))
                            isPre = false;
                        return true;
                    }

                    public boolean onStartTag(String tagName, String html) {
                        if ("pre".equals(tagName))
                            isPre = true;
                        else if (TAGS_NEED_APPEND_LINE.contains(tagName))
                            buffer.append('\n');
                        return true;
                    }

                    public boolean onText(String html) {
                        buffer.append(decodeHtml(html, isPre));
                        return true;
                    }
                }
        );
        return buffer.toString();
    }

    static String decodeHtml(String html, final boolean isPre) {
        char[] cs = html.toCharArray();
        StringBuilder buffer = new StringBuilder(cs.length);
        int start = 0;
        boolean continueSpace = false;
        char current, next;
        for(;;) {
            if(start>=cs.length)
                break;
            current = cs[start]; // read current char
            if(start+1<cs.length) // and next char
                next = cs[start+1];
            else
                next = '\0';
            if(current==' ') {
                if(isPre || !continueSpace)
                    buffer = buffer.append(' ');
                continueSpace = true;
                // continue loop:
                start++;
                continue;
            }
            // not ' ', so:
            if(current=='\r' && next=='\n') {
                if(isPre)
                    buffer = buffer.append('\n');
                // continue loop:
                start+=2;
                continue;
            }
            if(current=='\n' || current=='\r') {
                if(isPre)
                    buffer = buffer.append('\n');
                // continue loop:
                start++;
                continue;
            }
            // cannot continue space:
            continueSpace = false;
            if(current=='&') {
                // maybe special char:
                int length = readUtil(cs, start, ';', 10);
                if(length==(-1)) { // just '&':
                    buffer = buffer.append('&');
                    // continue loop:
                    start++;
                    continue;
                }
                else { // check if special character:
                    String spec = new String(cs, start, length);
                    String specChar = SPECIAL_CHARS.get(spec);
                    if(specChar!=null) { // special chars!
                        buffer = buffer.append(specChar);
                        // continue loop:
                        start+=length;
                        continue;
                    }
                    else { // check if like '&#1234;':
                        if(next=='#' && cs[start+length-1]==';') { // maybe a char
                            String num = new String(cs, start+2, length-3);
                            try {
                                int code = Integer.parseInt(num);
                                if(code>0 && code<65536) { // this is a special char:
                                    buffer = buffer.append((char)code);
                                    // continue loop:
                                    start+=length;
                                    continue;
                                }
                            }
                            catch(Exception e) {}
                            // just normal char:
                            buffer = buffer.append("&#");
                            // continue loop:
                            start+=2;
                            continue;
                        }
                        else { // just '&':
                            buffer = buffer.append('&');
                            // continue loop:
                            start++;
                            continue;
                        }
                    }
                }
            }
            else { // just a normal char!
                buffer = buffer.append(current);
                // continue loop:
                start++;
                continue;
            }
        }
        return buffer.toString();
    }

    // read from cs[start] util meet the specified char 'util',
    // or (-1) if not found:
    static int readUtil(final char[] cs, final int start, final char util, final int maxLength) {
        int end = start+maxLength;
        if(end>cs.length)
            end = cs.length;
        for(int i=start; i<start+maxLength; i++) {
            if(i<cs.length && cs[i]==util) {
                return i-start+1;
            }
        }
        return (-1);
    }

    static int indexOf(char[] data, int start, String s) {
        char[] ss = s.toCharArray();
        // TODO: performance can be improved!
        for(int i=start; i<(data.length-ss.length); i++) {
            // compare from data[i] with ss[0]:
            boolean match = true;
            for(int j=0; j<ss.length; j++) {
                if(data[i+j]!=ss[j]) {
                    match = false;
                    break;
                }
            }
            if(match)
                return i;
        }
        return (-1);
    }

    static int indexOf(char[] data, int start, char c) {
        for(int i=start; i<data.length; i++) {
            if(data[i]==c)
                return i;
        }
        return (-1);
    }

    private static final Set<String> TAGS_NEED_APPEND_LINE = new HashSet<String>();
    private static final Map<String, String> SPECIAL_CHARS = new HashMap<String, String>();

    static {
        TAGS_NEED_APPEND_LINE.add("p");
        TAGS_NEED_APPEND_LINE.add("br");
        TAGS_NEED_APPEND_LINE.add("hr");
        TAGS_NEED_APPEND_LINE.add("div");
        TAGS_NEED_APPEND_LINE.add("table");
        TAGS_NEED_APPEND_LINE.add("th");
        TAGS_NEED_APPEND_LINE.add("td");
        TAGS_NEED_APPEND_LINE.add("li");
        TAGS_NEED_APPEND_LINE.add("dt");
        TAGS_NEED_APPEND_LINE.add("dd");

        SPECIAL_CHARS.put("&quot;", "\"");
        SPECIAL_CHARS.put("&apos;", "\'");
        SPECIAL_CHARS.put("&lt;",   "<");
        SPECIAL_CHARS.put("&gt;",   ">");
        SPECIAL_CHARS.put("&amp;",  "&");
        SPECIAL_CHARS.put("&reg;",  "(r)");
        SPECIAL_CHARS.put("&copy;", "(c)");
        SPECIAL_CHARS.put("&nbsp;", " ");
        SPECIAL_CHARS.put("&pound;", "?");
        SPECIAL_CHARS.put("&ldquo;", "“");
        SPECIAL_CHARS.put("&rdquo;", "”");
        SPECIAL_CHARS.put("&hellip;", "…");
    }

}
