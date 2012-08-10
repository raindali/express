package org.expressme.modules.utils.html;

public class HtmlReader {

    private String html;
    private char[] buffer;

    private int position;
    private final int length;

    public HtmlReader(String html) {
        this.html = html;
        this.buffer = html.toLowerCase().toCharArray();
        this.length = buffer.length;
    }

    public void parse(HtmlHandler handler) {
        setPosition(0);
        while (!isEnd()) {
            char c = currentChar();
            if (c=='<') {
                if (startsWith(buffer, position, "<!--")) {
                    // a comment:
                    int endComment = indexOf(buffer, position + 4, "-->");
                    if (endComment==(-1))
                        endComment = buffer.length;
                    if (!handler.onComment(html.substring(position, endComment + 3)))
                        return;
                    setPosition(endComment + 3);
                }
                else if (startsWith(buffer, position, "</")) {
                    // end tag: </xxx>
                    int gt = indexOf(buffer, position, '>');
                    if (gt==(-1)) {
                        // treat the start '<' as normal character:
                        if (!handler.onText("&lt;" + html.substring(position+1, buffer.length)))
                            return;
                        setPosition(buffer.length);
                    }
                    else {
                        if (!handler.onEndTag(html.substring(position + 2, gt), html.substring(position, gt+1)))
                            return;
                        setPosition(gt + 1);
                    }
                }
                else {
                    // a start or empty tag:
                    int gt = indexOf(buffer, position, '>');
                    if (gt==(-1)) {
                        // treat the start '<' as normal character:
                        if (!handler.onText("&lt;" + html.substring(position+1, buffer.length)))
                            return;
                        setPosition(buffer.length);
                    }
                    else {
                        String tagHtml = html.substring(position, gt + 1);
                        String tagName = getTagName(tagHtml);
                        if (tagName.length()==0) {
                            // treat the start '<' as normal character:
                            if (!handler.onText("&lt;" + html.substring(position+1, gt)))
                                return;
                            setPosition(gt + 1);
                        }
                        else if (buffer[gt-1]=='/') {
                            // it is an empty tag: <xxx />:
                            if (!handler.onEmptyTag(tagName, tagHtml))
                                return;
                            setPosition(gt + 1);
                        }
                        else {
                            // it is a start tag: <xxx>:
                            if (!handler.onStartTag(tagName, tagHtml))
                                return;
                            setPosition(gt + 1);
                        }
                    }
                }
            }
            else {
                // a text:
                int end = indexOf(buffer, position, '<');
                if (end==(-1))
                    end = buffer.length;
                // text found from position to end:
                if (!handler.onText(html.substring(position, end)))
                    return;
                setPosition(end);
            }
        }
    }

    // get tag name from html "<xxx abc='xyz'... "
    String getTagName(String tagHtml) {
        for (int i=1; i<tagHtml.length(); i++) {
            char c = tagHtml.charAt(i);
            if (! ((c>='a' && c<='z') || (c>='A' && c<='Z') || (c>='0' && c<='9')))
                return tagHtml.substring(1, i).toLowerCase();
        }
        return "";
    }

    char currentChar() {
        return buffer[position];
    }

    boolean isEnd() {
        return position>=length;
    }

    void setPosition(int p) {
        position = p;
    }

    boolean moveNext() {
        position++;
        if (position<length)
            return true;
        position--;
        return false;
    }

    boolean hasNext() {
        return position<length-1;
    }

    boolean moveNext(int n) {
        position += n;
        if (position<length)
            return true;
        position -= n;
        return false;
    }

    boolean movePrev() {
        if (position>0) {
            position--;
            return true;
        }
        return false;
    }

    boolean movePrev(int n) {
        if (position>n) {
            position -= n;
            return true;
        }
        return false;
    }

    boolean startsWith(char[] data, int start, String s) {
        for (int i=0; i<s.length(); i++) {
            if (s.charAt(i)!=data[start])
                return false;
            start++;
            if (start>=data.length)
                return false;
        }
        return true;
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

}
