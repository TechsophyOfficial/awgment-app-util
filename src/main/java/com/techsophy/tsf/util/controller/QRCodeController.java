package com.techsophy.tsf.util.controller;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.techsophy.tsf.util.dto.CreateQRDto;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.awt.image.BufferedImage;
import java.io.IOException;
import static com.techsophy.tsf.util.constants.PropertiesConstants.CREATE_OR_ALL_ACCESS;
import static com.techsophy.tsf.util.constants.QRCodeConstants.*;

@RequestMapping(BASE_URL+VERSION_V1)
public interface QRCodeController
{
    @PostMapping(QR_CODE_URL)
    @PreAuthorize(CREATE_OR_ALL_ACCESS)
    String generateQRCode(@RequestBody CreateQRDto createQRDto) throws IOException, WriterException, NotFoundException;

    @PostMapping(path =DOWNLOAD_QR_CODE_URL, produces = MediaType.IMAGE_PNG_VALUE)
    @PreAuthorize(CREATE_OR_ALL_ACCESS)
    BufferedImage generateQRCodeDownload(@RequestBody CreateQRDto createQRDto) throws IOException, WriterException;
}
