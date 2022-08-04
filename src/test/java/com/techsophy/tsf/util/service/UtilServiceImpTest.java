package com.techsophy.tsf.util.service;

import com.google.zxing.WriterException;
import com.techsophy.tsf.util.config.GlobalMessageSource;
import com.techsophy.tsf.util.dto.CreateQRDto;
import com.techsophy.tsf.util.exception.NoDataFoundException;
import com.techsophy.tsf.util.service.impl.UtilServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

import static com.techsophy.tsf.util.constants.QRCodeConstants.*;

@ExtendWith(MockitoExtension.class)
//@ActiveProfiles(PropertiesTestConstants.TEST_ACTIVE_PROFILE)
class UtilServiceImpTest {
        @Mock
        GlobalMessageSource messageSource;
        @InjectMocks
        UtilServiceImpl utilService = new UtilServiceImpl("abc",true,messageSource);

        @Test
        void generateQRCode() throws IOException, WriterException {
            ReflectionTestUtils.setField(utilService,"logoDisplay",false);
            utilService.generateQRCode(new CreateQRDto(DATA,HEIGHT,WIDTH));
            String value = "iVBORw0KGgoAAAANSUhEUgAAAB0AAAAdAQAAAAB+6FqiAAAAcElEQVR4XmP4////DwY04oOsQwXD9/vxFQxfQi8CifhAIBEFJL7fuF/B8EEUKPv/K1DdR1ZZoI6cVRUMn890VDD8NFP7wfArogso+/keUJ1wCVDHxXs/GL6Eh4EMWAVkhXUCxa4oAe0Q6qlAtxdEAADatlS6xMLUvAAAAABJRU5ErkJggg==";
            Assertions.assertEquals("iVBORw0KGgoAAAANSUhEUgAAAB0AAAAdAQAAAAB+6FqiAAAAcElEQVR4XmP4////DwY04oOsQwXD9/vxFQxfQi8CifhAIBEFJL7fuF/B8EEUKPv/K1DdR1ZZoI6cVRUMn890VDD8NFP7wfArogso+/keUJ1wCVDHxXs/GL6Eh4EMWAVkhXUCxa4oAe0Q6qlAtxdEAADatlS6xMLUvAAAAABJRU5ErkJggg==",value);
        }
        @Test
        void generateQRCode1() {
            ReflectionTestUtils.setField(utilService,"logoDisplay",true);
            ReflectionTestUtils.setField(utilService,"logoPath","/opt/qrcode/logo.png");
            CreateQRDto createQRDto = new CreateQRDto(DATA,HEIGHT,WIDTH);
            Assertions.assertThrows(NoDataFoundException.class,()->utilService.generateQRCode(createQRDto));
        }

        @Test
        void generateQRCodeDownload() throws IOException, WriterException {
            ReflectionTestUtils.setField(utilService,"logoDisplay",false);
            utilService.generateQRCodeDownload(new CreateQRDto(DATA,HEIGHT,WIDTH));
            String value = "BufferedImage@b5b9333: type = 12 IndexColorModel: #pixelBits = 1 numComponents = 3 color space = java.awt.color.ICC_ColorSpace@45592af7 transparency = 1 transIndex   = -1 has alpha = false isAlphaPre = false BytePackedRaster: width = 29 height = 29 #channels 1 xOff = 0 yOff = 0";
            Assertions.assertEquals("BufferedImage@b5b9333: type = 12 IndexColorModel: #pixelBits = 1 numComponents = 3 color space = java.awt.color.ICC_ColorSpace@45592af7 transparency = 1 transIndex   = -1 has alpha = false isAlphaPre = false BytePackedRaster: width = 29 height = 29 #channels 1 xOff = 0 yOff = 0",value);
        }

        @Test
        void generateQRCodeDownload1() {
            ReflectionTestUtils.setField(utilService,"logoDisplay",true);
            ReflectionTestUtils.setField(utilService,"logoPath","/opt/qrcode/logo.png");
            CreateQRDto createQRDto = new CreateQRDto(DATA,HEIGHT,WIDTH);
            Assertions.assertThrows(NoDataFoundException.class,()->utilService.generateQRCodeDownload(createQRDto));
        }
}
