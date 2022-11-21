package com.techsophy.tsf.util.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PropertiesConstants
{
    //PropertiesControllerConstants
    public static final String BASE_URL = "/util";
    public static final String VERSION_V1 = "/v1";
    public static final String PROPERTIES_URL = "/properties";
    public static final String PROPERTIES_BY_PROJECT_NAME ="/properties/project";
    public static final String PROJECT_NAME = "projectName";
    public static final String MULTI_TENANCY_PACKAGE_NAME ="com.techsophy.multitenancy.mongo.*";
    public static final String GET_PROPERTIES_SUCCESS="GET_PROPERTIES_SUCCESS";
    public static final String SAVE_PROPERTIES_SUCCESS="SAVE_PROPERTIES_SUCCESS";
    public static final String DELETE_PROPERTIES_SUCCESS="DELETE_PROPERTIES_SUCCESS";
    public static final String FILTER="filter";

    //JWTRoleConverter
    public static final String CLIENT_ROLES="clientRoles";
    public static final String USER_INFO_URL= "/protocol/openid-connect/userinfo";
    public static final String TOKEN_VERIFICATION_FAILED="Token verification failed";
    public static final String AWGMENT_ROLES_MISSING_IN_CLIENT_ROLES ="AugmentRoles are missing in clientRoles";
    public static final String CLIENT_ROLES_MISSING_IN_USER_INFORMATION="ClientRoles are missing in the userInformation";
    public static final String AWGMENT ="augment";

    /*TenantAuthenticationManagerConstants*/
    public static final String KEYCLOAK_ISSUER_URI = "${keycloak.issuer-uri}";

    // Roles
    public static final String HAS_ANY_AUTHORITY="hasAnyAuthority('";
    public static final String HAS_ANY_AUTHORITY_ENDING="')";
    public static final String AWGMENT_UTIL_CREATE_OR_UPDATE = "augment-util-create-or-update";
    public static final String AWGMENT_UTIL_READ = "augment-util-read";
    public static final String AWGMENT_UTIL_DELETE = "augment-util-delete";

    public static final String AWGMENT_UTIL_ALL = "awgment-util-all";
    public static final String OR=" or ";
    public static final String CREATE_OR_ALL_ACCESS =HAS_ANY_AUTHORITY+ AWGMENT_UTIL_CREATE_OR_UPDATE +HAS_ANY_AUTHORITY_ENDING+OR+HAS_ANY_AUTHORITY+ AWGMENT_UTIL_ALL +HAS_ANY_AUTHORITY_ENDING;
    public static final String READ_OR_ALL_ACCESS =HAS_ANY_AUTHORITY+ AWGMENT_UTIL_READ +HAS_ANY_AUTHORITY_ENDING+OR+HAS_ANY_AUTHORITY+ AWGMENT_UTIL_ALL +HAS_ANY_AUTHORITY_ENDING;
    public static final String DELETE_OR_ALL_ACCESS =HAS_ANY_AUTHORITY+ AWGMENT_UTIL_DELETE +HAS_ANY_AUTHORITY_ENDING+OR+HAS_ANY_AUTHORITY+ AWGMENT_UTIL_ALL +HAS_ANY_AUTHORITY_ENDING;

    //PropertiesSchemaCONSTANTS
    public static final String NAME_NOT_BLANK = "Project Name should not be blank";
    public static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String TIME_ZONE = "UTC";

    /*TokenUtilsAndWebclientWrapperConstants*/
    public static final String AUTHENTICATION_FAILED="Authentication failed";
    public static final String UNABLE_GET_TOKEN="Unable to get token";
    public static final String PREFERED_USERNAME="preferred_username";
    public static final String EMPTY_STRING="";
    public static final String BEARER="Bearer ";
    public static final String REGEX_SPLIT="\\.";
    public static final String ISS="iss";
    public static final String URL_SEPERATOR="/";
    public static final int SEVEN=7;
    public static final int ONE=1;
    public static final String DEFAULT_PAGE_LIMIT= "${default.pagelimit}";
    public static final String CREATED_ON="createdOn";
    public static final String DESCENDING="desc";
    public static final String  COLON=":";
    public static final String CREATED_ON_ASC="createdOn: ASC";

    //WebClientWrapper
    public static final String GET="GET";
    public static final String PUT="PUT";
    public static final String DELETE="DELETE";
    public static final String APPLICATION_JSON ="application/json";
    public static final String CONTENT_TYPE="Content-Type";

    /*PropertiesServiceImplConstants*/
    public static final String SPACE=" ";

    /*UserDetailsConstants*/
    public static final String GATEWAY_URI="${gateway.uri}";
    public static final String LOGGED_USER="loggeduser";
    public static final String TOKEN="token";
    public static final String AUTHORIZATION="Authorization";
    public static final String FILTER_COLUMN="?filter-column=loginId&filter-value=";
    public static final String MANDATORY_FIELDS="&only-mandatory-fields=true";
    public static final String RESPONSE="response";
    public static final String DATA ="data";
    public static final String GATEWAY="gateway";
    public static final String ACCOUNT_URL = "/accounts/v1/users";

    /*PropertiesDefinitionCustomRepositoryConstants*/
    public static final String FIRST_NAME="firstName";
    public static final String LAST_NAME="lastName";
    public static final String COMMA=",";
    public static final String MATCH="$match";
    public static final String PROJECT="$project";
    public static final String PROPERTIES="properties";
    public static final String FIRST_FILTER="$filter";
    public static final String INPUT="input";
    public static final String PROPERTIES_KEY="$properties";
    public static final String AS="as";
    public static final String P="p";
    public static final String CONDITION="cond";
    public static final String OR_CONDITION="$or";
    public static final String EQUALS_CONST ="$eq";
    public static final String CATEGORY="category";
    public static final String CATEGORY_IN_PROPERTIES_MAP="$$p.category";
    public static final String KEY_IN_PROPERTIES_MAP="$$p.key";
    public static final String KEY="key";
    public static final String VALUE_IN_PROPERTIES_MAP="$$p.value";
    public static final String VALUE="value";

    /*FormControllerConstants*/
    public static final String ID = "id";
    public static final String BASENAME_ERROR_MESSAGES = "classpath:errorMessages";
    public static final String BASENAME_MESSAGES = "classpath:messages";
    public static final String TP_PROPERTIES_DEFINITION_COLLECTION = "tp_properties";

    //PropertiesDefCustomRepoImpl
    public static final String REGEX_PATTERN_1="^\"|\"$";

    //LoggingHandler
    public static final String CONTROLLER_CLASS_PATH = "execution(* com.techsophy.tsf.util.controller..*(..))";
    public static final String SERVICE_CLASS_PATH= "execution(* com.techsophy.tsf.util.service..*(..))";
    public static final String EXCEPTION = "ex";
    public static final String IS_INVOKED_IN_CONTROLLER= "{}() is invoked in controller ";
    public static final String IS_INVOKED_IN_SERVICE= "{}() is invoked in service ";
    public static final String EXECUTION_IS_COMPLETED_IN_CONTROLLER="{}() execution is completed  in controller";
    public static final String EXECUTION_IS_COMPLETED_IN_SERVICE="{}() execution is completed  in service";
    public static final String EXCEPTION_THROWN="An exception has been thrown in ";
    public static final String CAUSE="Cause : ";
    public static final String BRACKETS_IN_CONTROLLER="() in controller";
    public static final String BRACKETS_IN_SERVICE="() in service";
}
