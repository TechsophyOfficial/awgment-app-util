package com.techsophy.tsf.util.service;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.techsophy.tsf.util.dto.CreateQRDto;
import java.awt.image.BufferedImage;
import java.io.IOException;

public interface UtilService
{
    String generateQRCode(CreateQRDto createQRDto) throws WriterException, IOException, NotFoundException;

    BufferedImage generateQRCodeDownload(CreateQRDto createQRDto) throws WriterException, IOException;

}
