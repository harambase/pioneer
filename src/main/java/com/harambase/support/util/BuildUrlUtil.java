package com.harambase.support.util;

public class BuildUrlUtil {

    public static StringBuilder buildUrl(String remotePath, String uri, String ip, int port) {
        String urlPrefix = ip + ":" + port;
        StringBuilder requestUrl = new StringBuilder(urlPrefix)
                .append(remotePath)
                .append("uri=").append(uri);
        return requestUrl;
    }

    public static StringBuilder buildUrl(String remotePath, String ip, int port) {
        String urlPrefix = ip + ":" + port;
        StringBuilder requestUrl = new StringBuilder(urlPrefix);
        requestUrl.append(remotePath);
        return requestUrl;
    }

}
