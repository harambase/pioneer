package com.harambase.pioneer.helper;

import com.harambase.pioneer.common.support.util.RestTemplateUtil;

import java.util.HashMap;
import java.util.Map;

public class OneDriveHelper {

    private final String CLIENT_ID = "ae863513-0ccf-419a-bdfc-72cd641196c1";
    private final String CLIENT_CODE = "AQABAAIAAACrHKvrx7G2SaZbZh-tDnp7WJO-YvIlXxiColdmUAQe-b20OnXBcTPGk-rjMC8eBGXz0qZoQaIxdBSpZBfsxiQNLQIw_NYkjD3arWO13IecVBRgBSAJ4t5R_zj-pDRD0oiwFtmXXXi2z_h5XtFXGgr7L3Get1rZNtN4KEtFuU3e0oiKgzpR7Jc7hF52zrozAdkeGPme6SJ4HIHBufyiulJbiPhMDgqD2GPDYqmzFtxqgIH2_aa9xv-jmkhd3wVBlRrR31bSZ1LBqrBoDL4JRjhISg9L-8rXMuPxwfQxotcz5ZUu1EQ-ZIVL7lTG6ZeELbzPTzhK0ktuUgN6PiKlprzAtOsNqblVEY8BQppUWbUyH6J4JlThs77TZWjgpPRXHwuDoFcVWcHsgQ-2JRljkkmVqdbPQkVLTBLrnX8kma6MgNy-MTY1fR96-bveDrlEqn8LZyddpzYqkQJImuhJCJI-v11l0XYtX8luVuFXlOHQ6Uqk01y7QaTDknZqMJ2-lpANMRqvDRM_JEIn7c_3KGirqSeb8k5YwOcc701893432SAA";
    private final String CLIENT_SECRET = "cABhAHMAcwB3AG8AcgBkAA==";
    private final String REDERIECT_LOCAL = "http://localhost:30001";
    private final String REDERIECT_INTERNAL = "http://192.168.1.6:30000";
    private final String URL_LOCAL = "https://login.partner.microsoftonline.cn/common/oauth2/authorize?response_type=code&client_id="+ CLIENT_ID +"&redirect_uri=" + REDERIECT_LOCAL;
    private final String URL_INTERNAL = "https://login.partner.microsoftonline.cn/common/oauth2/authorize?response_type=code&client_id="+ CLIENT_ID +"&redirect_uri=" + REDERIECT_INTERNAL;
    private final String RESOURCE_URL_LOCAL = "https://login.partner.microsoftonline.cn/common/oauth2/token?client_id="+ CLIENT_ID +"&redirect_uri=" + REDERIECT_LOCAL + "&code=" + CLIENT_CODE +
            "&grant_type=authorization_code&resource=";

//    public Map getAccessToken(){
//        Map<String, String> clientInfo = new HashMap<>();
//        clientInfo.put("client_id", CLIENT_ID);
//        clientInfo.put("redirect_uri", REDERIECT_LOCAL);
//        clientInfo.put("client_secret", CLIENT_SECRET);
//        clientInfo.put("code", CLIENT_CODE);
//        clientInfo.put("resource", )
//
//        RestTemplateUtil.sendRestRequest();
//    }
}
