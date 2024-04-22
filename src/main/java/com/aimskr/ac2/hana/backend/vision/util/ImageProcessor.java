package com.aimskr.ac2.hana.backend.vision.util;

import com.aimskr.ac2.common.enums.image.ImageProcessingResultCode;
import com.aimskr.ac2.hana.backend.channel.json.ImgFileInfoDto;
import com.aimskr.ac2.hana.backend.channel.json.ImportDto;
import com.aimskr.ac2.common.util.FileUtil;
import com.aimskr.ac2.hana.backend.core.image.service.ImageService;
import dev.brachtendorf.jimagehash.hash.Hash;
import dev.brachtendorf.jimagehash.hashAlgorithms.HashingAlgorithm;
import dev.brachtendorf.jimagehash.hashAlgorithms.PerceptiveHash;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageProcessor {
    private final FileUtil fileUtil;
    private final ImageService imageService;

    // 동일한 포맷의 카드사용캡쳐에서 금액만 바뀌는 경우 중복으로 인식하여 Resolution 변경 32 -> 64
    private HashingAlgorithm hasher = new PerceptiveHash(64);

    /**
     * Orogin -> Ac : 알파투명도 제거, 모든 이미지를 jpg로 저장
     */
    public void preProcessImage(ImportDto importDto, ImgFileInfoDto imgFileInfoDto) throws Exception {
        try {
            log.debug("[preProcessImage] - {}", imgFileInfoDto.getImgId());
            String originFilePath = fileUtil.calcOriginFilePath(importDto, imgFileInfoDto);
            String acDir = fileUtil.calcAcDir(importDto);
            String acFilePath = fileUtil.calcAcFilePath(importDto, imgFileInfoDto);

            File originImage = new File(originFilePath);
            Files.createDirectories(Paths.get(acDir));
            Runtime.getRuntime().exec("chmod -R 777 " + acDir);

            if (imgFileInfoDto.getImgFileNm().toLowerCase().indexOf("pdf") > 0){
                // PDF 이미지 전처리
                try (PDDocument document = PDDocument.load(originImage)) {
                    log.debug("[preProcessImage] - PDF - {}", originFilePath);
                    PDFRenderer renderer = new PDFRenderer(document);
                    // PDF의 첫 페이지를 렌더링 (0은 첫 페이지를 의미)
                    BufferedImage image = renderer.renderImageWithDPI(0, 300, ImageType.RGB);
                    // 이미지를 JPG 형식으로 저장
                    ImageIO.write(image, "JPEG", new File(acFilePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // PDF가 아닌 경우 이미지 전처리
                BufferedImage bufferedImage = ensureOpaque(ImageIO.read(originImage));

                if (bufferedImage != null) {

                    if (imgFileInfoDto.getImgFileNm().indexOf("png") > 0){
                        BufferedImage afterImg  = changePngToJpg(bufferedImage);
                        ImageIO.write(afterImg, "jpg", new File(acFilePath));
                    }else{
                        ImageIO.write(bufferedImage, "jpg", new File(acFilePath));
                    }
                    log.debug("[preProcessImage] - {}", acFilePath);
                } else {
                    log.error("[preProcessImage] buffer is null - {}", originFilePath);
                    File file = new File(originFilePath);
                    File newFile = new File(acFilePath);
                    Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    throw new RuntimeException("buffer is null");
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public BufferedImage changePngToJpg(BufferedImage inputImage){

        if (inputImage == null) return null;
        int newHeight = inputImage.getHeight();
        int newWidth = inputImage.getWidth();

        if (inputImage.getHeight() > 1600){
            newHeight = inputImage.getHeight()/3;
            newWidth = inputImage.getWidth()/3;
        }

        BufferedImage afterImg  = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        afterImg.createGraphics().drawImage(inputImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);

        return afterImg;
    }

    public BufferedImage changeGrayScale(BufferedImage inputImage){

        if (inputImage == null) return null;
        int newHeight = inputImage.getHeight();
        int newWidth = inputImage.getWidth();

        if (inputImage.getHeight() > 1600){
            newHeight = inputImage.getHeight()/3;
            newWidth = inputImage.getWidth()/3;
        }

        BufferedImage grayscale = new BufferedImage(
                newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
//        for (int i = 0; i < inputImage.getWidth(); i++) {
//            for (int j = 0; j < inputImage.getHeight(); j++) {
//                int rgb = inputImage.getRGB(i, j);
//
//                grayscale.setRGB(i, j, rgb);
//            }
//        }

        grayscale.createGraphics().drawImage(inputImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);

        return grayscale;
    }

    public BufferedImage binarize(BufferedImage originalImage, String path) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        int newHeight = height;
        int newWidth = width;

        if (height> 1600){
            newHeight = height / 3;
            newWidth = width/3;
        }

        BufferedImage binarized = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        boolean isBackgroundLight = isBackgroundLight(originalImage);
        int threshold = isBackgroundLight ? 220 : 80;

        // Convert to grayscale then to binary
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color originalColor = new Color(originalImage.getRGB(x, y));
                int red = originalColor.getRed();
                int green = originalColor.getGreen();
                int blue = originalColor.getBlue();
                int gray = (red + green + blue) / 3;

                // Define threshold value, you might need to adjust these
                if (gray > threshold) {
                    binarized.setRGB(x, y, Color.WHITE.getRGB());
                } else {
                    binarized.setRGB(x, y, Color.BLACK.getRGB());
                }
            }
        }

//        try{
//            File ouputFile = new File(path);
//            ImageIO.write(binarized, "png", ouputFile);
//        } catch (IOException e){
//            e.printStackTrace();
//        }

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, binarized.getType());
        resizedImage.createGraphics().drawImage(binarized.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);

        return resizedImage;
    }


    public static boolean isBackgroundLight(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[] cornerPixels = {
                image.getRGB(0, 0), // Top-left corner
                image.getRGB(width - 1, 0), // Top-right corner
                image.getRGB(0, height - 1), // Bottom-left corner
                image.getRGB(width - 1, height - 1) // Bottom-right corner
        };

        long totalBrightness = 0;
        for (int rgb : cornerPixels) {
            int red = (rgb >> 16) & 0xff;
            int green = (rgb >> 8) & 0xff;
            int blue = (rgb) & 0xff;
            int brightness = (int) Math.round(0.299 * red + 0.587 * green + 0.114 * blue);
            totalBrightness += brightness;
        }

        long averageBrightness = totalBrightness / cornerPixels.length;
        int brightnessThreshold = 128; // This is a mid-point in 0-255 range, adjust as needed
        return averageBrightness > brightnessThreshold;
    }


    /**
     * 해당 경로에 있는 이미지의 Hash값을 계산해서 리턴
     */
    public Hash calcHash(String filepath) throws Exception {
        return hasher.hash(new File(filepath));
    }

    public String generateMD5(byte [] data) throws NoSuchAlgorithmException {
        return String.format("%032x", new BigInteger(1, MessageDigest.getInstance("MD5").digest(data)));
    }

    // Make sure your BufferedImage does not have alpha transparency.
    // JPEG does not support alpha, so if your image has alpha then ImageIO cannot write it to JPEG.
    // Use the following code to ensure your image does not have alpha transparancy:
    public BufferedImage ensureOpaque(BufferedImage bi) {
        if (bi == null) return null;

        if (bi.getTransparency() == BufferedImage.OPAQUE)
            return bi;
        int w = bi.getWidth();
        int h = bi.getHeight();
        int[] pixels = new int[w * h];
        bi.getRGB(0, 0, w, h, pixels, 0, w);
        BufferedImage bi2 = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        bi2.setRGB(0, 0, w, h, pixels, 0, w);
        return bi2;
    }

}
