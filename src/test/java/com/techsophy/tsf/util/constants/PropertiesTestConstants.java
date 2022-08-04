package com.techsophy.tsf.util.constants;

import java.time.Instant;

import static com.techsophy.tsf.util.constants.PropertiesConstants.*;

public class PropertiesTestConstants
{
    /*TokenUtilsAndWebclientWrapperConstants*/
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
    public static final String DESCENDING="desc";
    public static final String CREATED_ON="createdOn";
    public static final String CREATED_ON_ASC="createdOn: ASC";
    public static final String  COLON=":";
    public static final String INVALID_PAGE_REVIEW="INVALID_PAGE_REVIEW";
    public static final String DEFAULT_PAGE_LIMIT= "${default.pagelimit}";
    public static final String TEST_ACTIVE_PROFILE ="test";
    public static final String TENANT="tenant";

    //PropertiesControllerTest
    public static final String TEST_CATEGORY="category";
    public static final String TEST_KEY="key1";
    public static final String TEST_VALUE="value1";
    public static final String TEST_ID="1";
    public static final String TEST_PROPERTIES_NAME="abc";
    public final static String TEST_CREATED_BY_ID_VALUE ="1";
    public final static Instant TEST_CREATED_ON_INSTANT =Instant.now();
    public final static String TEST_UPDATED_BY_ID_VALUE ="1";
    public final static Instant TEST_UPDATED_ON_INSTANT =Instant.now();
    public final static String TEST_PROJECT_NAME="abc";
    public final static String TEST_CREATED_BY_NAME="abc";
    public final static String TEST_UPDATED_BY_NAME="abc";
    public static final String TEST_FILTER="abc";

    //INITILIZATION CONSTANTS
    public static final String DEPARTMENT="department";
    public static final String  NULL=null;
    public static final String EMAIL_ID="emailId";
    public static final String MOBILE_NUMBER="mobileNumber";
    public static final String LAST_NAME="lastName";
    public static final String  FIRST_NAME="firstName";
    public static final String USER_NAME="userName";
    public static final String ID ="id";
    public static final String UPDATED_ON="updatedOn";
    public static final String UPDATED_BY_NAME="updatedByName";
    public static final String UPDATED_BY_ID="updatedById";
    public static final String CREATED_BY_NAME ="createdByName";
    public static final String CREATED_BY_ID="createdById";
    public static final String USER_FIRST_NAME ="tejaswini";
    public static final String USER_LAST_NAME ="Kaza";
    public static final String NUMBER="1234567890";
    public static final String MAIL_ID ="tejaswini.k@techsophy.com";
    public static final String BIGINTEGER_ID = "847117072898674688";

    //WebClientWrapper
    public static final String GET="GET";
    public static final String POST="POST";
    public static final String PUT="PUT";
    public static final String DELETE="DELETE";
    public static final String TOKEN="token";

    //UserDetailsTestConstants
    public static final String  USER_DETAILS_RETRIEVED_SUCCESS="User details retrieved successfully";
    public static final String TEST_TOKEN="token";
    public static final String INITIALIZATION_DATA="{\"data\":[{\"id\":\"847117072898674688\",\"userName\":\"tejaswini\",\"firstName\":\"Kaza\",\"lastName\":\"Tejaswini\",\"mobileNumber\":\"1234567890\",\"emailId\":\"tejaswini.k@techsophy.com\",\"department\":null,\"createdById\":null,\"createdByName\":null,\"createdOn\":null,\"updatedById\":null,\"updatedByName\":null,\"updatedOn\":null}],\"success\":true,\"message\":\"User details retrieved successfully\"}\n";

    //PropertiesControllerConstants
    public static final String BASE_URL = "/util";
    public static final String VERSION_V1 = "/v1";
    public static final String PROPERTIES = "/properties";
    public static final String PROPERTIES_BY_APPLICATION_NAME ="/properties/application-name";
    public static final String APPLICATION_NOT_FOUND_EXCEPTION="APPLICATION_NOT_FOUND_EXCEPTION";
    public static final String APPLICATION_NAME = "applicationName";
    public static final String MULTI_TENANCY_PACKAGE_NAME ="com.techsophy.multitenancy.mongo.*";
    public static final String GET_PROPERTIES_SUCCESS="GET_PROPERTIES_SUCCESS";
    public static final String SAVE_PROPERTIES_SUCCESS="SAVE_PROPERTIES_SUCCESS";
    public static final String DELETE_PROPERTIES_SUCCESS="DELETE_PROPERTIES_SUCCESS";
    public static final String ENTITY_NOT_FOUND_EXCEPTION="ENTITY_NOT_FOUND_EXCEPTION";

    // Roles
    public static final String HAS_ANY_AUTHORITY="hasAnyAuthority('";
    public static final String HAS_ANY_AUTHORITY_ENDING="')";
    public static final String AUGMENT_UTIL_CREATE_OR_UPDATE = "augment-util-create-or-update";

    public static final String AWGMENT_UTIL_ALL = "awgment-rule-engine-all";
    public static final String OR=" or ";
    public static final String CREATE_OR_ALL_ACCESS =HAS_ANY_AUTHORITY+ AWGMENT_UTIL_CREATE_OR_UPDATE +HAS_ANY_AUTHORITY_ENDING+OR+HAS_ANY_AUTHORITY+ AWGMENT_UTIL_ALL +HAS_ANY_AUTHORITY_ENDING;
    public static final String READ_OR_ALL_ACCESS =HAS_ANY_AUTHORITY+ AWGMENT_UTIL_READ +HAS_ANY_AUTHORITY_ENDING+OR+HAS_ANY_AUTHORITY+ AWGMENT_UTIL_ALL +HAS_ANY_AUTHORITY_ENDING;
    public static final String DELETE_OR_ALL_ACCESS =HAS_ANY_AUTHORITY+ AWGMENT_UTIL_DELETE +HAS_ANY_AUTHORITY_ENDING+OR+HAS_ANY_AUTHORITY+ AWGMENT_UTIL_ALL +HAS_ANY_AUTHORITY_ENDING;
}
