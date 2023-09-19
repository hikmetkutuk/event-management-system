package com.qrcodeservice.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class QRCodeService {
    public Resource generateQRCode(String qrCodeData) {
        try {
            int width = 200;
            int height = 200;

            BitMatrix bitMatrix = new QRCodeWriter().encode(qrCodeData, BarcodeFormat.QR_CODE, width, height);
            BufferedImage bufferedImage = toBufferedImage(bitMatrix);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", outputStream);

            byte[] imageBytes = outputStream.toByteArray();
            ByteArrayResource resource = new ByteArrayResource(imageBytes);

            File uploadDir = new File("qrcode-service/src/main/resources/static/img");
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String filePath = uploadDir + "/QRCode.png";

            try {
                FileOutputStream fos = new FileOutputStream(filePath);
                fos.write(imageBytes);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return resource;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Yardımcı metot: BitMatrix'i BufferedImage'e dönüştürür.
    private BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }
}
