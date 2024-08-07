package com.aimskr.ac2.hana.backend.channel.service;


import com.aimskr.ac2.common.util.FileUtil;
import com.aimskr.ac2.common.util.clustering.ClusteringService;
import com.aimskr.ac2.hana.backend.channel.json.ImgFileInfoDto;
import com.aimskr.ac2.hana.backend.channel.json.ImportDto;
import com.aimskr.ac2.hana.backend.vision.domain.AiDetail;
import com.aimskr.ac2.hana.backend.vision.dto.ValueBox;
import com.aimskr.ac2.hana.backend.vision.dto.VisionResult;
import com.aimskr.ac2.hana.backend.vision.service.DiagAutoInputService;
import com.aimskr.ac2.hana.backend.vision.service.VisionService;
import com.aimskr.ac2.hana.backend.vision.util.DocumentTypeChecker;
import com.aimskr.ac2.hana.backend.vision.util.InputVerifier;
import com.aimskr.ac2.hana.backend.vision.util.RuleOrganizer;
import com.aimskr.ac2.common.config.AutocaptureConfig;
import com.aimskr.ac2.common.enums.doc.DocType;
import com.aimskr.ac2.common.enums.image.ImageProcessingResultCode;
import com.aimskr.ac2.common.exception.AutoReturnFailedException;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.synap.ocr.sdk.OCRBox;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Note: AOP로 생성 시점에 Retry 기능을 주입해서 넣어주기 때문에,
 * Retry를 사용하는 서비스와 제공하는 서비스가 동일 클래스에 있으면 적용되지 않음
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RetryService {

    private final VisionService visionService;
    private final RuleOrganizer ruleOrganizer;
    private final DocumentTypeChecker documentTypeChecker;
    private final AutocaptureConfig autocaptureConfig;
    private final InputVerifier inputVerifier;
    private final DiagAutoInputService diagAutoInputService;
    private final ClusteringService clusteringService;
    private final FileUtil fileUtil;
    private VisionResult visionResult;

    @Retryable(value = SftpException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public ImageProcessingResultCode downloadFile(ChannelSftp channelSftp, String srcFullPath, String destFullPath)
            throws SftpException {
        log.debug("[downloadFile] - download file : {}", srcFullPath);
        channelSftp.get(srcFullPath, destFullPath, SftpService.getProgressMonitor(), ChannelSftp.RESUME);
        return ImageProcessingResultCode.NORMAL;
    }

    @Retryable(value = AutoReturnFailedException.class, maxAttempts = 2, backoff = @Backoff(delay = 10))
    public VisionResult autoInput(String autocaptureImage, ImportDto importDto, ImgFileInfoDto imgFileInfoDto) throws AutoReturnFailedException {

        log.debug("[autoInput] - autocaptureImage : {}", autocaptureImage);
//        List<AiPhoneRepair> aiPhoneRepairs;
        String accidentCode = "";

//        if (Constant.PHONE_REPAIR.equals("importDto.getApiFlgCd()")){
//            accidentCode = AccidentType.DAMAGE.getCode();
//        }

        visionResult = VisionResult.createInitialResult();

        List<OCRBox> boxes;
        if (autocaptureConfig.getSftpIp().equals("test")){
            boxes = visionService.doOCRTest(autocaptureImage);
        }else{
            boxes = visionService.doOCR(autocaptureImage, fileUtil.calcAcFilePath(importDto, imgFileInfoDto));
        }

        // ONE-0016 글자인식 없는 이미지의 경우 디폴트 값으로 리턴
        if (boxes.isEmpty()){
            return visionResult;
        }

        List<ValueBox> valueBoxesToSort = visionService.toValueBox(boxes);
        List<ValueBox> boxesClustered = clusteringService.cluster(valueBoxesToSort);
        List<ValueBox> valueBoxes = visionService.mergeAndSortClusterBoxes(boxesClustered);
        String labelString = visionService.makeLabelString(valueBoxes);
        List<String> rows = visionService.mergeLabelsByRow(valueBoxes);
        //TODO: 향후 확대시 문서분류 수정 필요
//        DocType classifyResult = documentTypeChecker.getDocumentType(valueBoxes, labelString, accidentCode);
        DocType classifyResult = documentTypeChecker.getDocumentType(valueBoxes, labelString);
        visionResult.setDocType(classifyResult);

        log.debug("[autoInput] - DocType : {}", classifyResult.toString());
        List<AiDetail> aiDetails = ruleOrganizer.runClaimRules(valueBoxes, rows, labelString, classifyResult);
        if (classifyResult.equals(DocType.MDDG)){
            diagAutoInputService.autoInputDiags(importDto.getRqsReqId(), importDto.getAcdNo(), importDto.getRctSeq(), imgFileInfoDto, rows, labelString);
        }

        visionResult = inputVerifier.verifyInput(importDto, imgFileInfoDto, aiDetails, classifyResult);
//        visionResult = inputVerifier.verifyInput(importDto, imgFileInfoDto, aiPhoneRepairs, classifyResult);
        visionResult.setContent(visionService.makeRawString(valueBoxes));

//        if (!visionService.checkEssentialItems(classifyResult, aiDetails)){
//            log.debug("[autoInput] - AutoReturnFailedException : {}", CustomExceptionCodes.NONAUTO);
//            throw new AutoReturnFailedException(CustomExceptionCodes.NONAUTO.getNumVal(), "NonAuto");
//        }

        return visionResult;
    }

    @Recover
    public VisionResult recover(AutoReturnFailedException e){
        log.debug("[recover] - AutoReturnFailedException : {}", e.toString());
        return visionResult;
    }

    @Recover
    public ImageProcessingResultCode recover(SftpException e){
        log.debug("[recover] - SftpException : {}", e.toString());
        return ImageProcessingResultCode.FTP_ERROR;
    }



}

