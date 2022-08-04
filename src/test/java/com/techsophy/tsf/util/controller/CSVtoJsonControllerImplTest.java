package com.techsophy.tsf.util.controller;

import com.techsophy.tsf.util.controller.impl.CSVtoJsonControllerImpl;
import com.techsophy.tsf.util.model.ApiResponse;
import com.techsophy.tsf.util.service.CSVtoJsonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.techsophy.tsf.util.constants.FileUploadTestConstants.KEY;
import static com.techsophy.tsf.util.constants.FileUploadTestConstants.VALUE;
import static com.techsophy.tsf.util.constants.PropertiesTestConstants.TEST_ACTIVE_PROFILE;

@ActiveProfiles(TEST_ACTIVE_PROFILE)
@ExtendWith({MockitoExtension.class})
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CSVtoJsonControllerImplTest {
    @Mock
    CSVtoJsonService csVtoJsonService;
    @InjectMocks
    CSVtoJsonControllerImpl csVtoJsonController;

    @Test
    void convertToJsonTest() throws IOException {
        Map<String, Object> doc = new HashMap<>();
        doc.put(KEY,VALUE);
        Mockito.when(csVtoJsonService.convertToJson("1")).thenReturn(List.of(doc));
        ApiResponse<List<Map<String, Object>>> response = csVtoJsonController.convertToJson("1");
        Assertions.assertNotNull(response);
    }
}
