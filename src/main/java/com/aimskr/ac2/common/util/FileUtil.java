package com.aimskr.ac2.common.util;

import com.aimskr.ac2.hana.backend.channel.json.ImgFileInfoDto;
import com.aimskr.ac2.hana.backend.channel.json.ImportDto;
import com.aimskr.ac2.common.config.AutocaptureConfig;
import com.aimskr.ac2.common.config.FilepathConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RequiredArgsConstructor
@Component
public class FileUtil {
    private final AutocaptureConfig autocaptureConfig;
    private final FilepathConfig filepathConfig;

    public void createDir(String dirPath) {
        File dest = new File(dirPath);
        if (!dest.exists()) {
            Path storeLocation = Paths.get(dirPath);
            try {
                Files.createDirectories(storeLocation);
                log.debug("[createDir] Directory 생성 : {}", dirPath);
            } catch (IOException ioe) {
                log.error("[createDir] IOException : {} ", ioe.getMessage());
                ioe.printStackTrace();
            }
        }
    }
//
//
//    public String calcAcPath(ImportDto importDto) {
//        return  filepathConfig.getAcDir() + "/" + importDto.getReceiptNo() + "/"
//                + importDto.getReceiptSeq() + "/";
//    }
//
    public String calcFtpFilePath(ImportDto importDto, ImgFileInfoDto imgFileInfoDto) {
        return autocaptureConfig.getSftpPath() + importDto.calcReqDate()
                + "/" + importDto.getAcdNo() + "/" + FileUtil.calcOriginFilename(imgFileInfoDto);
    }

    public String calcOriginFilePath(ImportDto importDto, ImgFileInfoDto imgFileInfoDto) {
        return calcOriginDir(importDto) + FileUtil.calcOriginFilename(imgFileInfoDto);
    }

    public String calcAcFilePath(ImportDto importDto, ImgFileInfoDto imgFileInfoDto) {
        return calcAcDir(importDto) + FileUtil.calcUpdateFilename(imgFileInfoDto);
    }

    public static String calcOriginFilename(ImgFileInfoDto imgFileInfoDto) {
//        return FileUtil.extractFileName(imgFileInfoDto.getImgId());
        return imgFileInfoDto.getImgFileNm();
    }
    public static String calcUpdateFilename(ImgFileInfoDto imgFileInfoDto) {
        return FileUtil.changeExtToJpg(FileUtil.calcOriginFilename(imgFileInfoDto));
    }

    public static String changeExtToJpg(String fileName) {
        String name = fileName.substring(0, fileName.indexOf("."));
        // 11.25 jpg로 테스트
        return name + ".jpg";
    }

    public static String extractFileName(String fullPath) {
        return fullPath.substring(fullPath.lastIndexOf("/") + 1);
    }
//
    public String calcOriginDir(ImportDto importDto) {
        return filepathConfig.getOriginDir() + "/"
                + importDto.calcReqDate() + "/"
                + importDto.getAcdNo() + "/";
    }
//
    public String calcAcDir(ImportDto importDto) {
        return filepathConfig.getAcDir() + "/"
                + importDto.calcReqDate() + "/"
                + importDto.getAcdNo() + "/";
    }

}
