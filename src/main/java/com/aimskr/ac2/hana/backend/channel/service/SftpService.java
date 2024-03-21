package com.aimskr.ac2.hana.backend.channel.service;


import com.aimskr.ac2.hana.backend.channel.json.ImgFileInfoDto;
import com.aimskr.ac2.hana.backend.channel.json.ImportDto;
import com.aimskr.ac2.common.config.AutocaptureConfig;
import com.aimskr.ac2.common.enums.image.ImageProcessingResultCode;
import com.aimskr.ac2.common.util.FileUtil;
import com.jcraft.jsch.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@RequiredArgsConstructor
@Service
public class SftpService {
    private final AutocaptureConfig autocaptureConfig;
    private final RetryService retryService;
    private final FileUtil fileUtil;

    // SFTP 파일 여러개 받기
    public boolean downloadMultiFile(ImportDto importDto) {
        boolean isSuccess = true;     // 실패로 초기화

        // 테스트 프로필일 경우, 다운로드 실행하지 않음
        if (autocaptureConfig.getSftpIp().equals("test")){
            return isSuccess;
        }
        ChannelSftp channelSftp = createSftpChannel();

        if (channelSftp == null) {
            log.error("[downloadMultiFile] - SFTP Channel is null");
            return false;
        }

        for(ImgFileInfoDto imgFileInfoDto : importDto.getIMG_LST()) {
            fileUtil.createDir(fileUtil.calcOriginDir(importDto));
            // 2. sftp에서 파일을 내려받아 저장
            String srcFilePath = fileUtil.calcFtpFilePath(importDto, imgFileInfoDto);
            String destFilePath = fileUtil.calcOriginFilePath(importDto, imgFileInfoDto);
            try {
                ImageProcessingResultCode resultCode = retryService.downloadFile(channelSftp, srcFilePath, destFilePath);
                if (resultCode.equals(ImageProcessingResultCode.ERROR)){
                    isSuccess = false;
                    log.error("[downloadMultiFile] - SFTP download failed : {}", srcFilePath);
                }
            } catch (SftpException se) {
                isSuccess = false;
                log.error("[downloadMultiFile] - SftpException : {}", se.getMessage());
            }
        }
        closeSftpChanlel(channelSftp);
        return isSuccess;
    }

    public ChannelSftp createSftpChannel() {
        try {
            JSch jsch = new JSch();

            if(StringUtils.hasText(autocaptureConfig.getSftpKey())) {//개인키가 존재한다면
                jsch.addIdentity(autocaptureConfig.getSftpKey());
            }

            Session session = jsch.getSession(
                    autocaptureConfig.getSftpUser(),
                    autocaptureConfig.getSftpIp(),
                    Integer.parseInt(autocaptureConfig.getSftpPort())
            );

            session.setPassword(autocaptureConfig.getSftpPwd());
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();

            log.debug("[createSftpChannel] SFTP connected - Host : {}", session.getHost());
            return (ChannelSftp) channel;
        } catch (JSchException e) {
            log.error("[createSftpChannel] SFPT ERROR");
            e.printStackTrace();
            return null;
        }
    }

    public void closeSftpChanlel(ChannelSftp channelSftp) {
        try {
            if (channelSftp.isConnected())  channelSftp.disconnect();
            Session session = channelSftp.getSession();
            if (session.isConnected())  session.disconnect();
            log.debug("[closeSftpChanlel] SFTP disconnected - Host : [{}]", session.getHost());
        } catch (JSchException e) {
            log.error("[closeSftpChanlel] SFPT ERROR");
        }
    }

    public static SftpProgressMonitor getProgressMonitor() {
        return new SftpProgressMonitor() {
            long maxSize;
            @Override
            public void init(int op, String src, String dest, long max) {
                this.maxSize = max;
                log.debug("SFTP MONITOR START : " + src + " -> " + dest + " (MAX : " + max + ")");
            }
            @Override
            public boolean count(long bytes) {
                return(true);
            }
            @Override
            public void end() { log.debug("SFTP MONITOR END"); }
            public long getMaxSize() { return this.maxSize; }
        };
    }
}
