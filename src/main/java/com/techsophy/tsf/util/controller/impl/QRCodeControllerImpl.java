package com.techsophy.tsf.util.controller.impl;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.techsophy.tsf.util.controller.QRCodeController;
import com.techsophy.tsf.util.dto.CreateQRDto;
import com.techsophy.tsf.util.service.UtilService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Slf4j
@RestController
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class QRCodeControllerImpl implements QRCodeController {

    UtilService utilService;

    @Override
    public String generateQRCode(CreateQRDto createQRDto) throws IOException, WriterException, NotFoundException {
        return utilService.generateQRCode(createQRDto);
    }

    @Override
    public BufferedImage generateQRCodeDownload(CreateQRDto createQRDto) throws WriterException, IOException {
        return utilService.generateQRCodeDownload(createQRDto);
    }
}
