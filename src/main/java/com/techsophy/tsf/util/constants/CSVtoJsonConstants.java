package com.techsophy.tsf.util.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CSVtoJsonConstants {

    public static final String CONVERT_URL = "/convert/{documentId}";
    public static final String DOCUMENT_DOWNLOAD_URI= "/dms/v1/documents/download?id=";
    public static final String CONVERTION_SUCCESS = "Converted Successfully";
    public static final String WEBCLIENT_BUFFER_SIZE = "${webclient.buffer-size}";
    public static final String CSV = ".csv";
    public static final String DOCUMENT_ID = "documentId";
    public static final String FETCHING_DOCUMENT_FROM_DMS = "FETCHING DOCUMENT FROM DMS";
    public static final String CONVERSION_STARTED = "CONVERSION STARTED";
    public static final String CONVERSION_COMPLETED = "CONVERSION COMPLETED";
}
