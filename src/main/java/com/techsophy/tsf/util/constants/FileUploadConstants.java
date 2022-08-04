package com.techsophy.tsf.util.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUploadConstants
{
    /*FileUploadServiceImplConstants*/
    public static final String  DOT=".";
    public static final String UPLOADING_CSV_FILE="Uploading CSV file";
    public static final String CSV = "csv";
    public static final String USER_INFO_MISSING="User information missing ";
    public static final String ROW_AND=" row and ";
    public static final String COLUMN_IN_FILE_UPLOAD ="column in uploaded file";
    public static final String CREATED="created";
    public static final String UPDATED="updated";
    public static final String STATUS="status";
    public static final String USER_REGISTRATION_FAILURE_EMAIL_TO="${USER.REGISTRATION.FAILURE.EMAIL.TO}";
    public static final String USER_REGISTRATION_FAILURE_EMAIL_SUBJECT="${USER.REGISTRATION.FAILURE.EMAIL.SUBJECT}";
    public static final String FILE_VALIDATIONS_GOING_ON="File validations going on";
    public static final String CALLING_BPMN="Calling BPMN";
    public static final String USER_REG_FAILURE_EMAIL_TO="userRegistrationFailureEmailTo";
    public static final String USER_REG_FAILURE_EMAIL_SUBJECT="userRegistrationFailureEmailSubject";
    public static final String USER_DEFINITION_FIRST_NAME="firstName";
    public static final String USER_DEFINTION_LAST_NAME="lastName";
    public static final String TP_FILE_UPLOAD ="tp_file_upload";
    public static final String USER_ID_NOT_NULL="userId cannot be null";
    public static final String FILE_UPLOAD_URL ="/file-upload";
    public static final String FILE_UPLOAD_SUCCESS ="FILE.UPLOAD.SUCCESS";
    public static final String FILE="file";


    /*FileUploadDefinitionRepositoryConstants*/
    public static final String FIND_ALL_BY_ID_QUERY="{'documentId' : ?0 }";

    /*FileUploadControllerConstants*/
    public static final String GET_ALL_RECORDS_SUCCESS = "GET.ALL.RECORDS.SUCCESS";
    public static final String STATUS_UPDATE_SUCCESS ="STATUS.UPDATED.SUCCESSFULLY";
    public static final String PLEASE_SELECT_CSV_FILE_WITH_DATA="AUGMENT-ACCOUNT-1025";
    public static final String DELETE_RECORD_SUCCESS = "DELETE.RECORD.SUCCESS";
    public static final String CSV_FILE_NAME_NOT_EMPTY="AUGMENT-ACCOUNT-1005";
    public static final String CANNOT_UPDATE_RECORD_WITH_GIVEN_ID="AUGMENT-ACCOUNT-1001";
    public static final String UPLOAD_CSV_FILE_EXTENSION_ONLY="AUGMENT-ACCOUNT-1049";
    public static final String  USER_NOT_FOUND_BY_ID="AUGMENT-ACCOUNT-1002";
    public static final String INVALID_FILE_NAME_OR_EXTENSION="AUGMENT-ACCOUNT-1008";
    public static final String CANNOT_FIND_RECORD_WITH_GIVEN_DOCUMENT_ID="CANNOT.FIND.RECORD.WITH.GIVEN.DOCUMENT.ID";
    public static final String FILE_URL ="/file-upload";
    public static final String DELETE_BY_ID="/file-upload";
    public static final String FILE_STATUS_URL ="/file-upload/status";
    public static final String PAGE="page";
    public static final String SIZE="size";
    public static final String SORT_BY="sort-by";
    public static final String FILTER_VALUE="filter-value";
    public static final String FILTER_COLUMN_NAME="filter-column";
    public static final String QUERY ="q";
    public static final String ID="id";
    public static final String DOCUMENT_ID ="documentId";
    public static final String TYPE="type";
    public static final String TO ="to";
    public static final String EMAIL_ID="emailId";
    public static final String PROCESS_DEFINITION_KEY="processDefinitionKey";
    public static final String BUSINESS_KEY="businessKey";
    public static final String VARIABLES="variables";
    public static final String PROCESS_ID="Process_ntost2p";
    public static final String WORKFLOW_START_URL="/workflow/v1/process/start";
    public static final String SERVICE = "service";

    public static final String LOGO_FILE_NOT_FOUND = "Logo file not found in logo url";


}
