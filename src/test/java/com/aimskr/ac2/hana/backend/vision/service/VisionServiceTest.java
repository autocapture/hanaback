package com.aimskr.ac2.hana.backend.vision.service;

import com.aimskr.ac2.common.enums.doc.DocType;
import com.aimskr.ac2.common.util.FileUtil;
import com.aimskr.ac2.common.util.clustering.ClusteringService;
import com.aimskr.ac2.hana.backend.channel.json.ImgFileInfoDto;
import com.aimskr.ac2.hana.backend.channel.json.ImportDto;
import com.aimskr.ac2.hana.backend.channel.json.ResultDto;
import com.aimskr.ac2.hana.backend.core.assign.service.ClaimProcessManager;
import com.aimskr.ac2.hana.backend.core.detail.dto.DetailResponseDto;
import com.aimskr.ac2.hana.backend.core.detail.service.DetailService;
import com.aimskr.ac2.hana.backend.core.medical.dto.DiagInfoExchangeDto;
import com.aimskr.ac2.hana.backend.core.medical.service.DiagInfoService;
import com.aimskr.ac2.hana.backend.vision.domain.AiDetail;
import com.aimskr.ac2.hana.backend.vision.dto.ValueBox;
import com.aimskr.ac2.hana.backend.vision.dto.VisionResult;
import com.aimskr.ac2.hana.backend.vision.util.DocumentTypeChecker;
import com.aimskr.ac2.hana.backend.vision.util.ImageProcessor;
import com.aimskr.ac2.hana.backend.vision.util.InputVerifier;
import com.aimskr.ac2.hana.backend.vision.util.RuleOrganizer;
import com.synap.ocr.sdk.OCRBox;
import dev.brachtendorf.jimagehash.hash.Hash;
import groovy.util.logging.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;

@SpringBootTest
@ActiveProfiles("local3")
@Slf4j
@RunWith(SpringRunner.class)
public class VisionServiceTest {

    @Autowired
    private VisionService visionService;

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private ClusteringService clusteringService;

    @Autowired
    private DocumentTypeChecker documentTypeChecker;

    @Autowired
    private ClaimProcessManager claimProcessManager;

    @Autowired
    private DiagAutoInputService diagAutoInputService;

    @Autowired
    private DiagInfoService diagInfoService;

    @Autowired
    private RuleOrganizer ruleOrganizer;

    @Autowired
    private ImageProcessor imageProcessor;

    @Autowired
    private InputVerifier inputVerifier;

    @Autowired
    private DetailService detailService;

    @Test
    public void testVision() {

        String filename = "2024051017405704.jpg";

        ImportDto importDto = ImportDto.builder()
                .rqsReqId("aaa")
                .acdNo("202467863")
                .rctSeq("2")
                .acdDt("")
                .reqDtm("202407250001235")
                .build();

        ImgFileInfoDto imgFileInfoDto = ImgFileInfoDto.builder()
                .imgDcmNo("")
                .imgDcmflNo("")
                .imgId("")
                .imgFileNm(filename)
                .build();
        //202496897

        String imagePath = fileUtil.calcAcFilePath(importDto, imgFileInfoDto);

        List<OCRBox> boxes = visionService.doOCRTest(imagePath);


        List<ValueBox> valueBoxesToSort = visionService.toValueBox(boxes);
        List<ValueBox> boxesClustered = clusteringService.cluster(valueBoxesToSort);
        List<ValueBox> valueBoxes = visionService.mergeAndSortClusterBoxes(boxesClustered);
        List<String> rows = visionService.mergeLabelsByRow(valueBoxes);
        String labelString = visionService.makeLabelString(valueBoxes);

        System.err.println(valueBoxes);

        DocType classifyResult = documentTypeChecker.getDocumentType(valueBoxes, labelString);

        System.out.println(classifyResult);

        List<AiDetail> aiDetails = ruleOrganizer.runClaimRules(valueBoxes, rows, labelString, classifyResult);
        VisionResult visionResult = inputVerifier.verifyInput(importDto, imgFileInfoDto, aiDetails, classifyResult);

        System.err.println(aiDetails);
        detailService.saveDetailFromAiDetails(importDto.getRqsReqId(), importDto.getAcdNo(), importDto.getRctSeq(), filename);
        List<DetailResponseDto> details = detailService.findByFileName(importDto.getRqsReqId(), filename);

        System.out.println(details);


        diagAutoInputService.autoInputDiags(importDto.getRqsReqId(), importDto.getAcdNo(), importDto.getRctSeq(), imgFileInfoDto, rows, labelString);

        List<DiagInfoExchangeDto> diags = diagInfoService.getDiagInfoDto(importDto.getRqsReqId(), filename);
        System.out.println(diags);

//        System.out.println(classifyResult);
//        System.err.println(valueBoxes);

    }

    @Test
    public void testMakeResult(){

        ResultDto resultDto = claimProcessManager.makeSuccessResultDto("4o1wc8GEgf", "202469636", "2");

        System.out.println(resultDto);

    }

    @Test
    public void testCropImages(){

        String filename = "2024070414061896.tif";
        String orgPath = "src/test/resources/image/";
        String originFilePath = orgPath + filename;
        File originImage = new File(originFilePath);
        String newFileName = "test_margin.jpg";
        String acFilePath = orgPath + filename;



        // Create a new image with margins
        try{
            Hash hash = imageProcessor.calcHash(acFilePath);
            System.out.println(hash);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void calcImageSize(){

        String filename = "2024072412284490.jpg";
        String orgPath = "src/test/resources/image/";

        imageProcessor.resizeImage(orgPath + filename);

    }



}
