package org.expressme.modules.utils;

public class UrlGenerator {

    public static final String BASE_URL = "/upload/";

    public static final String id2Url(String id, String fileExt) {
        int n = id.hashCode();
        int dir1 = (n & 0xff0000) >> 16;
        int dir2 = (n & 0xff00) >> 8;
        int dir3 = n & 0xff;
        return new StringBuilder(64).append(BASE_URL).append(dir1).append('/').append(dir2).append('/').append(dir3).append('/').append(id).append(fileExt).toString();
    }

    public static final String id2Dir(String id, String fileExt) {
        int n = id.hashCode();
        int dir1 = (n & 0xff0000) >> 16;
        int dir2 = (n & 0xff00) >> 8;
        int dir3 = n & 0xff;
        return new StringBuilder(64).append(dir1).append('/').append(dir2).append('/').append(dir3).append('/').toString();
    }
}
