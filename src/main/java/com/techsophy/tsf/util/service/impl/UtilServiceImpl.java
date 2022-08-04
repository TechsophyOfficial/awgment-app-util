package com.techsophy.tsf.util.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.techsophy.tsf.util.config.GlobalMessageSource;
import com.techsophy.tsf.util.dto.CreateQRDto;
import com.techsophy.tsf.util.exception.NoDataFoundException;
import com.techsophy.tsf.util.service.UtilService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

import static com.techsophy.tsf.util.constants.FileUploadConstants.LOGO_FILE_NOT_FOUND;
import static com.techsophy.tsf.util.constants.FileUploadConstants.PLEASE_SELECT_CSV_FILE_WITH_DATA;
import static com.techsophy.tsf.util.constants.QRCodeConstants.*;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class UtilServiceImpl implements UtilService
{
    @Value(LOGO_URL)
    private String logoPath;

    @Value(LOGO_DISPLAY)
    private boolean logoDisplay;

    private  GlobalMessageSource globalMessageSource;

    @Override
    public String generateQRCode(CreateQRDto createQRDto) throws WriterException, IOException {

        //  Initializing the writer
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        //  Creating QR Code
        BitMatrix bitMatrix = qrCodeWriter.encode(createQRDto.getData(), BarcodeFormat.QR_CODE, createQRDto.getWidth(), createQRDto.getHeight());

        //  Setting the color to display the QR Code
        MatrixToImageConfig con = new MatrixToImageConfig( MatrixToImageConfig.BLACK , MatrixToImageConfig.WHITE );

        //  Generating QR code with provided configuration
        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix, con);

        BufferedImage finalImage;
        if(logoDisplay) {
            finalImage = addLogo(qrImage);
        }else {
            finalImage = qrImage;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(finalImage, PNG, baos);

        //  Converting it to the Byte Array
        byte[] pngData = baos.toByteArray();

        // Convert Byte Array into Base64 Encode String
        return Base64.getEncoder().encodeToString(pngData);
    }

    @Override
    public BufferedImage generateQRCodeDownload(CreateQRDto createQRDto) throws WriterException, IOException {
        //  Initializing the writer
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        //  Creating QR Code
        BitMatrix bitMatrix = qrCodeWriter.encode(createQRDto.getData(), BarcodeFormat.QR_CODE,createQRDto.getWidth(), createQRDto.getHeight());

        //  Setting the color to display the QR Code
        MatrixToImageConfig con = new MatrixToImageConfig( MatrixToImageConfig.BLACK , MatrixToImageConfig.WHITE ) ;

        //  Generating QR code with provided configuration
        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix, con);

        if(logoDisplay){
            return addLogo(qrImage);
        }else{
            return qrImage;
        }
    }

    private BufferedImage addLogo(BufferedImage qrImage) throws IOException {

        // Getting logo image
        BufferedImage logoImage;
        try {
            logoImage = ImageIO.read( new File(logoPath));
        }catch (IIOException e){
            throw new NoDataFoundException(LOGO_FILE_NOT_FOUND,globalMessageSource.get(LOGO_FILE_NOT_FOUND));
        }
        int finalImageHeight = qrImage.getHeight() - logoImage.getHeight();
        int finalImageWidth = qrImage.getWidth() - logoImage.getWidth();

        // Merging both images
        BufferedImage finalImage = new BufferedImage(qrImage.getHeight(), qrImage.getWidth(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = (Graphics2D) finalImage.getGraphics();
        graphics.drawImage(qrImage, 0, 0, null);
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        graphics.drawImage(logoImage,(finalImageWidth / 2),(finalImageHeight / 2), null);
        return finalImage;
    }
}