package com.techsophy.tsf.util.controller;

import com.techsophy.tsf.util.config.CustomFilter;
import com.techsophy.tsf.util.controller.impl.QRCodeControllerImpl;
import com.techsophy.tsf.util.dto.CreateQRDto;
import com.techsophy.tsf.util.service.impl.UtilServiceImpl;
import com.techsophy.tsf.util.utils.TokenUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import java.awt.image.BufferedImage;
import static com.techsophy.tsf.util.constants.PropertiesTestConstants.AUGMENT_UTIL_CREATE_OR_UPDATE;
import static com.techsophy.tsf.util.constants.PropertiesTestConstants.TEST_ACTIVE_PROFILE;
import static com.techsophy.tsf.util.constants.QRCodeConstants.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

@ActiveProfiles(TEST_ACTIVE_PROFILE)
@ExtendWith({MockitoExtension.class})
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class QRCodeControllerTest
{
    private static  final SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtSaveOrUpdate = jwt().authorities(new SimpleGrantedAuthority(AUGMENT_UTIL_CREATE_OR_UPDATE));

    @Mock
    TokenUtils mockTokenUtils;

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    QRCodeControllerImpl qrCodeController;

    @Mock
    UtilServiceImpl utilService;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    CustomFilter customFilter;


    @Order(1)
    @Test
    void generateQRCodeTest() throws Exception {
        CreateQRDto createQRDto=new CreateQRDto(DATA,HEIGHT,WIDTH);
        Mockito.when(utilService.generateQRCode(createQRDto)).thenReturn(new String());
        String response  = qrCodeController.generateQRCode(createQRDto);
        Assertions.assertNotNull(response);

    }

    @Order(1)
    @Test
    void generateQRCodeDownloadTest() throws Exception
    {
        CreateQRDto createQRDto=new CreateQRDto(DATA,HEIGHT,WIDTH);
        BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Mockito.when(utilService.generateQRCodeDownload(createQRDto)).thenReturn(bufferedImage);
        BufferedImage response  = qrCodeController.generateQRCodeDownload(createQRDto);
        Assertions.assertNotNull(response);
    }
}
