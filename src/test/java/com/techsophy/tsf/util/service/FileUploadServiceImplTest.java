package com.techsophy.tsf.util.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.idgenerator.IdGeneratorImpl;
import com.techsophy.tsf.util.config.GlobalMessageSource;
import com.techsophy.tsf.util.constants.FileUploadConstants;
import com.techsophy.tsf.util.dto.FileData;
import com.techsophy.tsf.util.dto.FileUploadResponse;
import com.techsophy.tsf.util.dto.FileUploadSchema;
import com.techsophy.tsf.util.entity.FileUploadDefinition;
import com.techsophy.tsf.util.exception.*;
import com.techsophy.tsf.util.repository.FileUploadDefinitionRepository;
import com.techsophy.tsf.util.service.impl.FileUploadServiceImpl;
import com.techsophy.tsf.util.utils.TokenUtils;
import com.techsophy.tsf.util.utils.UserDetails;
import com.techsophy.tsf.util.utils.WebClientWrapper;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static com.techsophy.tsf.util.constants.FileUploadTestConstants.*;
import static com.techsophy.tsf.util.constants.FileUploadTestConstants.ID;
import static com.techsophy.tsf.util.constants.PropertiesTestConstants.*;
import static io.vavr.API.Match.Pattern0.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.util.MimeTypeUtils.TEXT_PLAIN_VALUE;

@ContextConfiguration
@ActiveProfiles(TEST_ACTIVE_PROFILE)
@ExtendWith({SpringExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FileUploadServiceImplTest {
    @Mock
    IdGeneratorImpl mockIdGenerator;
    @Mock
    GlobalMessageSource mockGlobalMessageSource;
    @Mock
    TokenUtils tokenUtils;
    @Mock
    UserDetails mockUserDetails;
    @Mock
    FileUploadDefinitionRepository mockFileUploadDefinitionRepository;
    @Mock
    ObjectMapper mockObjectMapper;
    @Mock
    WebClientWrapper mockWebClientWrapper;

    @InjectMocks
    FileUploadServiceImpl mockFileUploadService;
    Map<String, Object> map = new HashMap<>();
    @BeforeEach
    public void init()
    {

        map.put(CREATED_BY_ID, NULL);
        map.put(CREATED_BY_NAME, NULL);
        map.put(CREATED_ON, NULL);
        map.put(UPDATED_BY_ID, NULL);
        map.put(UPDATED_BY_NAME, NULL);
        map.put(UPDATED_ON, NULL);
        map.put(FileUploadConstants.ID, BIGINTEGER_ID);
        map.put(USER_NAME, USER_FIRST_NAME);
        map.put(FIRST_NAME, USER_LAST_NAME);
        map.put(MOBILE_NUMBER, NUMBER);
        map.put(EMAIL_ID, MAIL_ID);
        map.put(DEPARTMENT, NULL);
    }

    @Test
    void uploadRecordsTest() throws IOException {
        File file1 = new File("src/test/resources/testdata/accounts_template.csv");
        FileInputStream input = new FileInputStream(file1);
        MultipartFile multipartFile = new MockMultipartFile(NAME, file1.getName(), CONTENT_TYPE, IOUtils.toByteArray(input));
        FileData fileData = new FileData(BIG_ID,map,BIG_ID,STATUS);
        FileUploadDefinition fileUploadDefinition = new FileUploadDefinition(BIG_ID,map,BIG_ID,STATUS);
        Mockito.when(tokenUtils.getTokenFromContext()).thenReturn(TOKEN);
//        WebClient webClient = mock(WebClient.class);
//        Mockito.when(mockWebClientWrapper.createWebClient(anyString())).thenReturn(webClient);
        Mockito.when(mockIdGenerator.nextId()).thenReturn(BIG_ID);
        Mockito.when(mockUserDetails.getUserDetails()).thenReturn(List.of(map));
        Mockito.when(this.mockFileUploadDefinitionRepository.findById(BigInteger.valueOf(Long.parseLong("1")))).thenReturn(Optional.of(fileUploadDefinition));
        Mockito.when(this.mockObjectMapper.convertValue(any(), FileData.class)).thenReturn(fileData);
        Mockito.when(this.mockObjectMapper.convertValue(any(), FileUploadDefinition.class)).thenReturn(fileUploadDefinition);
        Mockito.when(this.mockFileUploadDefinitionRepository.save(fileUploadDefinition)).thenReturn(fileUploadDefinition);
        FileUploadResponse response = mockFileUploadService.uploadRecords(multipartFile,TYPE);
        Assertions.assertNotNull(response);
    }

    @Test
    void uploadRecordsKeyValueIdTest() throws IOException {
        File file1 = new File("src/test/resources/testdata/accounts_template_Id.csv");
        FileInputStream input = new FileInputStream(file1);
        MultipartFile multipartFile = new MockMultipartFile(NAME, file1.getName(), CONTENT_TYPE, IOUtils.toByteArray(input));
        FileUploadDefinition fileUploadDefinition = new FileUploadDefinition(BIG_ID,map,BIG_ID,STATUS);
        Mockito.when(tokenUtils.getTokenFromContext()).thenReturn(TOKEN);
//        WebClient webClient = mock(WebClient.class);
//        Mockito.when(mockWebClientWrapper.createWebClient(anyString())).thenReturn(webClient);
        Mockito.when(mockIdGenerator.nextId()).thenReturn(BIG_ID);
        Mockito.when(mockUserDetails.getUserDetails()).thenReturn(List.of(map));
        Mockito.when(this.mockFileUploadDefinitionRepository.findById(BigInteger.valueOf(Long.parseLong("1")))).thenReturn(Optional.of(fileUploadDefinition));
        Assertions.assertThrows(UploadedUserNotFoundException.class,()-> mockFileUploadService.uploadRecords(multipartFile,TYPE));
    }

    @Test
    void uploadRecordsExceptionTest() throws IOException {
        MockMultipartFile file = new MockMultipartFile(NAME,"abc", TEXT_PLAIN_VALUE,"ABC".getBytes());
        Mockito.when(mockIdGenerator.nextId()).thenReturn(BIG_ID);
        Mockito.when(mockUserDetails.getUserDetails()).thenReturn(List.of(map));
        Assertions.assertThrows(InvalidDataException.class,()->mockFileUploadService.uploadRecords(file,TYPE));
    }

    @Test
    void uploadRecordsInvalidDataExceptionTest() throws IOException {
        MockMultipartFile file = new MockMultipartFile(NAME,"abc.ab.ab", TEXT_PLAIN_VALUE,"ABC".getBytes());
        Mockito.when(mockIdGenerator.nextId()).thenReturn(BIG_ID);
        Mockito.when(mockUserDetails.getUserDetails()).thenReturn(List.of(map));
        Assertions.assertThrows(InvalidDataException.class,()->mockFileUploadService.uploadRecords(file,TYPE));

    }

    @Test
    void uploadRecordsInvalidInputExceptionTest() throws IOException {
        MockMultipartFile file = new MockMultipartFile(NAME,"abc.ab", TEXT_PLAIN_VALUE,"ABC".getBytes());
        Mockito.when(mockIdGenerator.nextId()).thenReturn(BIG_ID);
        Mockito.when(mockUserDetails.getUserDetails()).thenReturn(List.of(map));
        Assertions.assertThrows(InvalidInputException.class,()->mockFileUploadService.uploadRecords(file,TYPE));
    }



    @Test
    void uploadRecordsNullCsvTest() throws IOException {
        File file1 = new File("src/test/resources/testdata/accounts_template_null.csv");
        FileInputStream input = new FileInputStream(file1);
        MultipartFile multipartFile = new MockMultipartFile(NAME, file1.getName(), CONTENT_TYPE, IOUtils.toByteArray(input));
        Mockito.when(tokenUtils.getTokenFromContext()).thenReturn(TOKEN);
        Mockito.when(mockIdGenerator.nextId()).thenReturn(BIG_ID);
        Mockito.when(mockUserDetails.getUserDetails()).thenReturn(List.of(map));
        Assertions.assertThrows(InvalidInputException.class,()->mockFileUploadService.uploadRecords(multipartFile,TYPE));
    }

    @Test
    void uploadRecordsEmptyFileNameTest() throws JsonProcessingException {
        Mockito.when(mockIdGenerator.nextId()).thenReturn(BIG_ID);
        Mockito.when(mockUserDetails.getUserDetails()).thenReturn(List.of(map));
        MockMultipartFile file = new MockMultipartFile(NAME,"", TEXT_PLAIN_VALUE,"ABC".getBytes());
        Assertions.assertThrows(FileNameNotPresentException.class,()->mockFileUploadService.uploadRecords(file,TYPE));
    }

    @Test
    void uploadRecordsEmptyFileTest() throws JsonProcessingException {
        Mockito.when(mockIdGenerator.nextId()).thenReturn(BIG_ID);
        Mockito.when(mockUserDetails.getUserDetails()).thenReturn(List.of(map));
        MockMultipartFile file = new MockMultipartFile(NAME,"","","".getBytes());
        Assertions.assertThrows(NoDataFoundException.class,()->mockFileUploadService.uploadRecords(file,TYPE));
    }

    @Test
    void updateStatusTest() throws JsonProcessingException {
        FileUploadSchema fileUploadSchema = new FileUploadSchema(ID,map,DOCUMENT_ID,STATUS);
        FileUploadDefinition fileUploadDefinition = new FileUploadDefinition(BIG_ID,map,BIG_ID,STATUS);
        Mockito.when(mockUserDetails.getUserDetails()).thenReturn(List.of(map));
        Mockito.when(this.mockFileUploadDefinitionRepository.findById(BigInteger.valueOf(Long.parseLong("1")))).thenReturn(Optional.of(fileUploadDefinition));
        Mockito.when(this.mockFileUploadDefinitionRepository.save(fileUploadDefinition)).thenReturn(fileUploadDefinition);
        FileUploadResponse response = mockFileUploadService.updateStatus(fileUploadSchema);
        Assertions.assertNotNull(response);
    }

    @Test
    void updateStatusExceptionTest() throws JsonProcessingException {
        FileUploadSchema fileUploadSchema = new FileUploadSchema(ID,map,DOCUMENT_ID,STATUS);
        Mockito.when(mockUserDetails.getUserDetails()).thenReturn(List.of(map));
        Assertions.assertThrows(UploadedUserNotFoundException.class,()->mockFileUploadService.updateStatus(fileUploadSchema));
    }

    @Test
    void getAllRecordsSortTypeTest(){
        FileUploadSchema fileUploadSchema = new FileUploadSchema(ID,map,DOCUMENT_ID,STATUS);
        FileUploadDefinition fileUploadDefinition = new FileUploadDefinition(BIG_ID,map,BIG_ID,STATUS);
        Mockito.when(this.mockFileUploadDefinitionRepository.findAllByDocumentId(BigInteger.valueOf(Long.parseLong("1")),Sort.by("email"))).thenReturn(Optional.of(Stream.of(fileUploadDefinition)));
        Mockito.when(this.mockObjectMapper.convertValue(fileUploadDefinition, FileUploadSchema.class)).thenReturn(fileUploadSchema);
        List<FileUploadSchema> response = mockFileUploadService.getAllRecords(Sort.by("email"),DOCUMENT_ID);
        Assertions.assertNotNull(response);
    }

    @Test
    void getAllRecordsSortTypeExceptionTest(){
        Sort sort =Sort.by("email");
        Assertions.assertThrows(UploadedUserNotFoundException.class,()->mockFileUploadService.getAllRecords(sort,DOCUMENT_ID));
    }

    @Test
    void getAllRecordsSortTypeEmptyDocIdTest(){
        Sort sort = mock(Sort.class);
        FileUploadDefinition fileUploadDefinition = new FileUploadDefinition(BIG_ID,map,BIG_ID,STATUS);
         Mockito.when(this.mockFileUploadDefinitionRepository.findAll(sort)).thenReturn(List.of(fileUploadDefinition));
        List<FileUploadSchema> response = mockFileUploadService.getAllRecords(Sort.by("email"),"");
        Assertions.assertNotNull(response);
    }

    @Test
    void getAllRecordsPaginationTypeTest(){
        Pageable pageable = PageRequest.of(1,1);
        FileUploadSchema fileUploadSchema = new FileUploadSchema(ID,map,DOCUMENT_ID,STATUS);
        FileUploadDefinition fileUploadDefinition = new FileUploadDefinition(BIG_ID,map,BIG_ID,STATUS);
        Page<FileUploadDefinition> page = new PageImpl<>(List.of(fileUploadDefinition));
        Mockito.when(this.mockFileUploadDefinitionRepository.findAllByDocumentId(BigInteger.valueOf(Long.parseLong("1")),pageable)).thenReturn(Optional.of(page));
        Mockito.when(this.mockObjectMapper.convertValue(fileUploadDefinition, Map.class)).thenReturn(map);
        mockFileUploadService.getAllRecords(pageable,DOCUMENT_ID);
        verify(mockFileUploadDefinitionRepository,times(1)).findAllByDocumentId(BigInteger.valueOf(Long.parseLong("1")),pageable);
    }

    @Test
    void getAllRecordsPaginationExceptionTest(){
        Pageable pageable = PageRequest.of(1,1);
        Assertions.assertThrows(UploadedUserNotFoundException.class,()->mockFileUploadService.getAllRecords(pageable,DOCUMENT_ID));
    }

    @Test
    void getAllRecordsPaginationTypeEmptyDocIdTest(){
        Pageable pageable = PageRequest.of(1,1);
        FileUploadSchema fileUploadSchema = new FileUploadSchema(ID,map,DOCUMENT_ID,STATUS);
        FileUploadDefinition fileUploadDefinition = new FileUploadDefinition(BIG_ID,map,BIG_ID,STATUS);
        Page<FileUploadDefinition> page = new PageImpl<>(List.of(fileUploadDefinition));
        Mockito.when(this.mockFileUploadDefinitionRepository.findAll(pageable)).thenReturn(page);
        Mockito.when(this.mockObjectMapper.convertValue(fileUploadDefinition, Map.class)).thenReturn(map);
        mockFileUploadService.getAllRecords(pageable,"");
        verify(mockFileUploadDefinitionRepository,times(1)).findAll(pageable);
    }

    @Test
    void deleteRecordByIdTest(){
        Mockito.when(mockFileUploadDefinitionRepository.existsById(BigInteger.valueOf(Long.parseLong("1")))).thenReturn(true);
        Mockito.when(this.mockFileUploadDefinitionRepository.deleteById(BigInteger.valueOf(Long.parseLong("1")))).thenReturn(1);
        mockFileUploadService.deleteRecordById("1","");
        Mockito.verify(mockFileUploadDefinitionRepository,times(1)).deleteById(BIG_ID);
    }
    @Test
    void deleteRecordByIdNullDocIdTest(){
        Mockito.when(mockFileUploadDefinitionRepository.existsById(BigInteger.valueOf(Long.parseLong("1")))).thenReturn(false);
        Assertions.assertThrows(UploadedUserNotFoundException.class,()->mockFileUploadService.deleteRecordById(ID,""));
    }

    @Test
    void deleteRecordByIdDocIdTest(){
        doNothing().when(mockFileUploadDefinitionRepository).deleteAll();
        mockFileUploadService.deleteRecordById(ID,DOCUMENT_ID);
        Mockito.verify(mockFileUploadDefinitionRepository,times(1)).deleteAll(BIG_ID);
    }
}
