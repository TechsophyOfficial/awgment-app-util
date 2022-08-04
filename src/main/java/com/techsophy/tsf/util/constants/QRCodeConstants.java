package com.techsophy.tsf.util.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QRCodeConstants
{
    /* TSF Constants*/
    public static final String BASE_URL = "/util";
    public static final String VERSION_V1 = "/v1";

    /* QR code Constants */
    public static final String QR_CODE_URL = "/qrcode";
    public static final String DOWNLOAD_QR_CODE_URL = "/qrcode/download";
    public static final String PNG = "PNG";
    public static final String LOGO_URL = "${logo.url}";

    public static final String LOGO_DISPLAY = "${logo.display}";

    /*TokenUtils and WebclientWrapperConstants*/
    public static final String AUTHENTICATION_FAILED="Authentication failed";
    public static final String UNABLE_GET_TOKEN="Unable to get token";
    public static final String PREFERED_USERNAME="preferred_username";
    public static final String EMPTY_STRING="";
    public static final String BEARER="Bearer ";
    public static final String REGEX_SPLIT="\\.";
    public static final String INVALID_TOKEN ="Invalid Token";
    public static final String ISS="iss";
    public static final String URL_SEPERATOR="/";
    public static final int SEVEN=7;
    public static final int ONE=1;
    public static final String DESCRIPTION = "description";
    public static final String CODE = "code";

    /*UserDetailsConstants*/
    public static final String AUTHORIZATION="Authorization";
    public static final String CONTENT_TYPE="Content-Type";
    public static final String APPLICATION_JSON ="application/json";

    //WebClientWrapper
    public static final String GET="GET";
    public static final String POST="POST";
    public static final String PUT="PUT";
    public static final String DELETE="DELETE";
    public static final String PATCH="PATCH";


    /* Imported Constants */
    public static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String TIME_ZONE = "UTC";
    public static final String ACCEPT_LANGUAGE = "Accept-Language";

    public static final String MULTI_TENANCY_PROJECT = "com.techsophy.multitenancy.mongo.*";

    /*TenantAuthenticationManagerConstants*/
    public static final String KEYCLOAK_ISSUER_URI = "${keycloak.issuer-uri}";
    public static final String GATEWAY_URL="${gateway.uri}";

    /*MainMethodConstants*/
    public static final String PACKAGE_NAME ="com.techsophy.tsf.util.*";
    public static final String UTIL ="tp-app-util";
    public static final String VERSION_1="1.0";
    public static final String UTIL_MODELER_API_VERSION_1="Util API v1.0";
    public static final String API_URL="/api";
    public static final String DEFAULT_SERVER_URL="Default server url";
}
