package com.techsophy.tsf.util.controller;

import com.techsophy.tsf.util.config.CustomFilter;
import com.techsophy.tsf.util.config.GlobalMessageSource;
import com.techsophy.tsf.util.controller.impl.FileUploadControllerImpl;
import com.techsophy.tsf.util.dto.FileUploadResponse;
import com.techsophy.tsf.util.dto.FileUploadSchema;
import com.techsophy.tsf.util.model.ApiResponse;
import com.techsophy.tsf.util.service.FileUploadService;
import com.techsophy.tsf.util.service.impl.UtilServiceImpl;
import com.techsophy.tsf.util.utils.TokenUtils;
import com.techsophy.tsf.util.utils.UserDetails;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.util.HashMap;
import java.util.Map;

import static com.techsophy.tsf.util.constants.FileUploadTestConstants.*;
import static com.techsophy.tsf.util.constants.FileUploadTestConstants.CONTENT_TYPE;
import static com.techsophy.tsf.util.constants.FileUploadTestConstants.ID;
import static com.techsophy.tsf.util.constants.FileUploadTestConstants.KEY;
import static com.techsophy.tsf.util.constants.FileUploadTestConstants.VALUE;
import static com.techsophy.tsf.util.constants.PropertiesConstants.*;
import static com.techsophy.tsf.util.constants.PropertiesTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
@SpringBootTest
@ActiveProfiles(TEST_ACTIVE_PROFILE)
@ExtendWith({MockitoExtension.class})
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FileUploadControllerImplTest {

    private static  final SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtSaveOrUpdate = jwt().authorities(new SimpleGrantedAuthority(AWGMENT_UTIL_CREATE_OR_UPDATE));
    private static  final SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtRead = jwt().authorities(new SimpleGrantedAuthority(AWGMENT_UTIL_READ));
    private static  final SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtDelete = jwt().authorities(new SimpleGrantedAuthority(AWGMENT_UTIL_DELETE));

    @Mock
    TokenUtils mockTokenUtils;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    UtilServiceImpl utilService;

    @Mock
    FileUploadService fileUploadService;

    @Mock
    GlobalMessageSource mockGlobalMessageSource;

    @InjectMocks
    FileUploadControllerImpl fileUploadController;
    @Mock
    UserDetails mockUserDetails;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    CustomFilter customFilter;



    @BeforeEach
    public void setUp()
    {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilters(customFilter)
                .apply(springSecurity())
                .build();
    }

    @Test
    void uploadFileTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile( NAME, ORIGINAL_FILE_NAME, CONTENT_TYPE, "Hello, World!".getBytes() );
        ApiResponse<FileUploadResponse> responseEntity = fileUploadController.uploadFile(file,TYPE);
        assertEquals(true, responseEntity.getSuccess());
    }

    @Test
    void updateStatusTest() throws Exception {
        Map<String,Object> userData = new HashMap<>();
        userData.put(KEY,VALUE);
        FileUploadSchema fileUploadSchema = new FileUploadSchema(ID,userData,DOCUMENT_ID,STATUS);
        ApiResponse<FileUploadResponse> response = fileUploadController.updateStatus(fileUploadSchema);
        Assertions.assertNotNull(response);
    }

    @Test
    void getAllRecordsPageNullTest(){
        ApiResponse response = fileUploadController.getAllRecords(null,null,null,"1");
        Assertions.assertNotNull(response);
    }

    @Test
    void getAllRecordsPageNotNullTest(){
        ApiResponse response = fileUploadController.getAllRecords(PAGE,PAGE_SIZE,null,DOCUMENT_ID);
        Assertions.assertNotNull(response);
    }

    @Test
    void deleteRecordByIdTest()
    {
        ApiResponse response = fileUploadController.deleteRecordById(ID,DOCUMENT_ID);
        Assertions.assertNotNull(response);
    }


}
