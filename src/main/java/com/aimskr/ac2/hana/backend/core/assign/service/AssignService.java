package com.aimskr.ac2.hana.backend.core.assign.service;

import com.aimskr.ac2.hana.backend.channel.domain.ImageHashRepository;
import com.aimskr.ac2.hana.backend.channel.json.CompleteDto;
import com.aimskr.ac2.hana.backend.channel.json.ImportDto;
import com.aimskr.ac2.hana.backend.channel.json.ResultDto;
import com.aimskr.ac2.hana.backend.channel.service.ChannelService;
import com.aimskr.ac2.hana.backend.core.assign.domain.Assign;
import com.aimskr.ac2.hana.backend.core.assign.domain.AssignRepository;
import com.aimskr.ac2.hana.backend.core.assign.dto.AssignResponseDto;
import com.aimskr.ac2.hana.backend.core.assign.dto.AssignSearchRequestDto;
import com.aimskr.ac2.hana.backend.core.image.domain.Image;
import com.aimskr.ac2.hana.backend.core.image.domain.ImageRepository;
import com.aimskr.ac2.hana.backend.core.image.dto.ImageResponseDto;
import com.aimskr.ac2.hana.backend.core.image.service.ImageService;
import com.aimskr.ac2.hana.backend.core.phone.domain.AiPhoneRepairRepository;
import com.aimskr.ac2.hana.backend.core.phone.domain.PhoneRepair;
import com.aimskr.ac2.hana.backend.core.phone.domain.PhoneRepairRepository;
import com.aimskr.ac2.hana.backend.core.phone.domain.PhoneRepairDetailRepository;
import com.aimskr.ac2.hana.backend.security.service.EmailService;
import com.aimskr.ac2.common.config.AutocaptureConfig;
import com.aimskr.ac2.common.config.ControlConfig;
import com.aimskr.ac2.common.enums.Constant;
import com.aimskr.ac2.common.enums.assign.RequestType;
import com.aimskr.ac2.common.enums.doc.AccidentType;
import com.aimskr.ac2.common.enums.doc.DocType;
import com.aimskr.ac2.common.enums.status.AcceptStatus;
import com.aimskr.ac2.common.enums.status.ProcessResponseCode;
import com.aimskr.ac2.common.enums.status.ResultAcceptCode;
import com.aimskr.ac2.common.enums.status.Step;
import com.aimskr.ac2.hana.backend.util.service.CacheService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@ComponentScan("com.aimskr.ac2.common")
public class AssignService {
    private final ImageService imageService;
    private final EmailService emailService;
    private final ControlConfig controlConfig;
    private final AutocaptureConfig autocaptureConfig;
    private final AssignRepository assignRepository;
    private final ImageRepository imageRepository;
    private final ImageHashRepository imageHashRepository;
    private final AiPhoneRepairRepository aiPhoneRepairRepository;
    private final PhoneRepairRepository phoneRepairRepository;
    private final PhoneRepairDetailRepository phoneRepairDetailRepository;
    private final CacheService cacheService;
    private final ChannelService channelService;
    private final Gson gson;

    @Transactional
    public void saveAssign(ImportDto importDto) {
        // 1. 배당 객체 생성 및 초기 설정
        Assign assign = importDto.toEntity();
        AcceptStatus acceptStatus = AcceptStatus.OK;
        String requestJson = gson.toJson(importDto);

//        if (endpoint.equals(Constant.PHONE_REPAIR)){
//            assign.updateAccidentType(AccidentType.DAMAGE);
//        } else if (endpoint.equals(Constant.MED)){
//            assign.updateAccidentType(AccidentType.MEDICAL);
//        }

        // 2. 전문검증
        // TODO: 필요한지 재검토
        if (controlConfig.isRequestValidation()) {
            log.debug("[saveAssign] validation start : {}", requestJson);
            String validationResult = importDto.checkValid();
            if (!validationResult.equals(ImportDto.VALID)) {
                acceptStatus = AcceptStatus.INVALID;
                log.error("[saveAssign] validation error : {}" , validationResult);
            }
        }

        // 3. 기존에 존재하던 배당이면, 기존 배당목록 삭제
        String accrNo = importDto.getACD_NO();
        String dmSeqno = importDto.getRCT_SEQ();
        AssignResponseDto assignResponseDto = findByKey(accrNo, dmSeqno);

        assign.updateAcceptInfo(requestJson, acceptStatus);
        assignRepository.save(assign);
        log.debug("[saveAssign] saveAssign - " + importDto.getKey() + " DB 저장");

    }

    /**
     * 중복 요청 전문 확인
     */
    @Transactional(readOnly = true)
    public boolean checkDupAssign(ImportDto importDto) {
        String arrcNo = importDto.getACD_NO();
        String dmSeqno = importDto.getRCT_SEQ();

        AssignResponseDto assignResponseDto = findByKey(arrcNo, dmSeqno);

        if (assignResponseDto != null){
            return true;
        }

        return false;
    }


    @Transactional
    public boolean checkDupImageContentsFromAll(String receiptNo, String receiptSeq) {

        boolean qaDuplicity = false;

//        List<Image> images = imageRepository.findByKeyForDupCheck(receiptNo, receiptSeq);
//        List<Image> imagesForDupCheck = images.stream()
//                .filter(i -> i.getImageDocumentType().equals(DocType.CDRC) || i.getImageDocumentType().equals(DocType.ECRC) || i.getImageDocumentType().equals(DocType.CDRF))
//                .toList();
//
//        for (Image image: imagesForDupCheck){
//
//            boolean qaDuplicateImage = imageService.checkDupImageContentsByImage(image.getFileName());
//            if (qaDuplicateImage) qaDuplicity = true;
//
//        }

        return qaDuplicity;
    }


    /**
     * 자동회신 가능 여부 판단
     */
    @Transactional
    public boolean checkAutoReturnable(String receiptNo, String receiptSeq, boolean qaDuplicity){

        if (qaDuplicity){
            log.debug("[checkAutoReturnable] qaDuplicity - receiptNo : {}, receiptSeq : {}", receiptNo, receiptSeq);
            return false;
        }

        if (!checkAutoReturnableByImage(receiptNo, receiptSeq)){
            log.debug("[checkAutoReturnable] checkAutoReturnableByImage - receiptNo : {}, receiptSeq : {}", receiptNo, receiptSeq);
            return false;
        }

        List<PhoneRepair> phoneRepairs = phoneRepairRepository.findByKey(receiptNo, receiptSeq);

        // 영수증 합계 금액으로 자동회신 가능 여부 체크
        if (checkAutoReturnableByTotalSum(receiptNo, receiptSeq, phoneRepairs)){
            return true;
        }
        //주요 detail의 정확도로 자동회신 가능 여부 체크
        if (!checkAutoReturnableByAccuracy(phoneRepairs)) {
            log.debug("[checkAutoReturnable] checkAutoReturnableByAccuracy - receiptNo : {}, receiptSeq : {}", receiptNo, receiptSeq);
            return false;
        }
            return true;
    }

    /**
     * 모든 영수증 이미지의 거래일자, 거래시간, 사용금액의 정확도가 0.9 이상인 경우 자동회신 가능
     * 위 3항목 중 하나라도 정확도가 0.9 미만인 경우, 자동회신 불가
     */


    @Transactional
    public boolean checkAutoReturnableByImage(String receiptNo, String receiptSeq){

        boolean autoreturnable = true;

//            List<Image> images = imageRepository.findByKey(receiptNo, receiptSeq);
//
//            for (Image image: images){
//                // 여러영수증, 뒤집한 영수증, 카드취소 영수증인 경우,자동회신 불가
//                if (image.getImageDocumentTypeOcr().equals(DocType.MULT)){
//                    log.debug("[checkAutoReturnable] multiple images - receiptNo : {}, receiptSeq : {}, image : {} ", receiptNo, receiptSeq, image.getFileName());
//                    image.updateQaReason(QaReason.MULT.getMessage());
//                    autoreturnable = false;
//                } else if(image.getImageDocumentTypeOcr().equals(DocType.FLIP)){
//                    log.debug("[checkAutoReturnable] flipped images - receiptNo : {}, receiptSeq : {}, image : {} ", receiptNo, receiptSeq, image.getFileName());
//                    image.updateQaReason(QaReason.FLIP.getMessage());
//                    autoreturnable = false;
//                } else if(image.getImageDocumentTypeOcr().equals(DocType.CDRF)){
//                    log.debug("[checkAutoReturnable] card refund images - receiptNo : {}, receiptSeq : {}, image : {} ", receiptNo, receiptSeq, image.getFileName());
//                    image.updateQaReason(QaReason.CDRF.getMessage());
//                    autoreturnable = false;
//                }
//
//                boolean containsDiscount = discounts.stream()
//                        .anyMatch(image.getRawData()::contains);
//                if (containsDiscount){
//                    log.debug("[checkAutoReturnable] meal ticket or meal coupon - receiptNo : {}, receiptSeq : {} ", receiptNo, receiptSeq);
//                    image.updateQaReason(QaReason.DISCOUNT.getMessage());
//                    autoreturnable = false;
//                }
//
//                if (image.getIsQa()){
//                    image.updateQaReason(QaReason.ERROR.getMessage());
//                    autoreturnable = false;
//                }
//
//            }
            return autoreturnable;
    }

    @Transactional
    public boolean checkAutoReturnableByAccuracy(List<PhoneRepair> phoneRepairs){

        boolean autoreturnable = true;
        // 거래일자, 거래시간, 사용금액의 정확도가 0.9 이상인 경우 자동회신 가능
        for (PhoneRepair phoneRepair : phoneRepairs){
            if (phoneRepair.getItemName().equals("거래일자") || phoneRepair.getItemName().equals("거래시간") || phoneRepair.getItemName().equals("사용금액")){
                if (phoneRepair.getAccuracy() < 0.9){
                    log.debug("[checkAutoReturnable] accuracy is low - itemName : {}, accuracy : {}",
                            phoneRepair.getItemName(), phoneRepair.getAccuracy());
                    autoreturnable = false;
                    Image image = imageRepository.findByFileName(phoneRepair.getFileName()).orElse(null);
//                    image.updateQaReason(QaReason.ACCURACY.getMessage());
                }
                if (phoneRepair.getItemName().equals("사용금액")){
                    if (phoneRepair.getItemValue().indexOf("-") > -1){
                        log.debug("[checkAutoReturnable] negative Amount - itemName : {}, amount : {}",
                                phoneRepair.getItemName(), phoneRepair.getItemValue());
                        autoreturnable = false;
                        Image image = imageRepository.findByFileName(phoneRepair.getFileName()).orElse(null);
//                        image.updateQaReason(QaReason.NEGATIVE.getMessage());
                    }
                }
            }
        }
        return autoreturnable;
    }

    /**
     * Heather 요청 사항: 자동회신 가능한 영수증의 사용금액 합계가 20,000원 이상인 경우에는 다른 영수증의 정합성과 관계없이 자동회신
     * 자동회신 가능한 영수증이라 함은, 14. 카드영수증으로 분류가 되었고, 중복이 아니고, 거래일자/거래시간/사용금액이 모두 정확도 0.9 이상인 영수증
     */
    public boolean checkAutoReturnableByTotalSum(String receiptNo, String receiptSeq, List<PhoneRepair> phoneRepairs){

        Map<String, List<PhoneRepair>> detailsByImage = new HashMap<>();

        for (PhoneRepair phoneRepair : phoneRepairs){
            detailsByImage.computeIfAbsent(phoneRepair.getFileName(), k -> new ArrayList<>()).add(phoneRepair);
        }

        List<Image> images = imageRepository.findByKey(receiptNo, receiptSeq);
        List<String> validImages = images.stream()
                .filter(i -> i.getIsDup() == false && DocType.CDRC.equals(i.getImgType()))
                .map(Image::getFileName)
                .toList();

        int totalAmountSum = 0;

        for (String fileName: detailsByImage.keySet()){
            if (validImages.contains(fileName)){
                List<PhoneRepair> detailsInImage = detailsByImage.get(fileName);
                List<PhoneRepair> accuratePhoneRepairs = detailsInImage.stream()
                        .filter(d -> (d.getItemName().equals("거래일자")||
                                d.getItemName().equals("거래시간") ||
                                d.getItemName().equals("사용금액")) &&
                                d.getAccuracy() >= 0.9).toList();
                if (accuratePhoneRepairs.size() == 3) {
                    totalAmountSum += accuratePhoneRepairs.stream()
                            .filter(d -> d.getItemName().equals("사용금액"))
                            .mapToInt(d -> Integer.parseInt(d.getItemValue().replaceAll("\\D", "")))
                            .sum();
                }
            }
        }

        if (totalAmountSum > 20000){
            log.debug("[checkAutoReturnable] totalAmountSum is over 20000 - receiptNo : {}, receiptSeq : {}, totalAmountSum : {}",
                    receiptNo, receiptSeq, totalAmountSum);
            return true;
        } else {
            log.debug("[checkAutoReturnable] totalAmountSum is under 20000 - receiptNo : {}, receiptSeq : {}, totalAmountSum : {}",
                    receiptNo, receiptSeq, totalAmountSum);
            return false;
        }
    }

    /**
     * 예외 Case - 처리 결과를 카카오에 전달
     * TODO: 에러 응답코드 세분화 협의 with 카카오
     * 1) 전문 검증에 실패한 경우 - 요청 오류
     */
    @Transactional
    public void finishWithValidationError(ImportDto importDto) {
        log.info("[finishWithError] ImportDto : {}", importDto);
        Assign assign = importDto.toEntity();
        assign.updateFinishWithError();
        assignRepository.save(assign);
        assign.updateResultDeliveryTime(LocalDateTime.now());
        assign.updateProcessResponseCode(ProcessResponseCode.ERROR);
        String receiptNo = importDto.getACD_NO();
        String receiptSeq = importDto.getRCT_SEQ();
        ResultDto resultDtoWithError = ResultDto.buildErrorResult(importDto);
        assign.updateResponseJson(gson.toJson(resultDtoWithError));
//        List<String> email = controlConfig.getErrorEmail();
//        try{
//            emailService.sendErrorAlert(receiptNo, email, controlConfig.isDupCheck());
//        }catch(Exception es){
//            es.printStackTrace();
//        }

        log.debug("[finishWithValidationError] receiptNo : {}, receiptSeq : {} ", receiptNo, receiptSeq);
        channelService.complete(resultDtoWithError);
    }


    /**
     * 예외 Case - 처리 결과를 카카오에 전달
     * 2) FTP 연결이 실패하거나, 이미지 다운로드 3회 시도 실패한 경우
     */
    @Transactional
    public void finishWithFtpError(ImportDto importDto) {
        log.info("[finishWithError] ImportDto : {}", importDto);
        String accrNo = importDto.getACD_NO();
        String dmSeqno = importDto.getRCT_SEQ();
        Assign assign = assignRepository.findByKey(accrNo, dmSeqno)
                .orElse(null);
        if (assign != null) {
            assign.updateFinishWithError();
        } else {
            log.error("[finishWithError] assign is null- ImportDto : {}", importDto);
        }

        ResultDto resultDtoWithError = ResultDto.buildErrorResult(importDto);
        assign.updateResponseJson(gson.toJson(resultDtoWithError));
        log.debug("[finishWithFtpError] receiptNo : {}, receiptSeq : {} ", accrNo, dmSeqno);

        List<String> email = controlConfig.getErrorEmail();
        try{
            if (controlConfig.isAlertMode()){
                emailService.sendErrorAlert(accrNo, email, controlConfig.isDupCheck());
            }
        }catch(Exception es){
            es.printStackTrace();
        }

        channelService.complete(resultDtoWithError);
    }

    @Transactional
    public void applyQaAssign(ImportDto importDto, String qaOwner) {
        Assign assign = assignRepository.findByKey(importDto.getACD_NO(), importDto.getRCT_SEQ()).orElse(null);
        if (assign != null){
            assign.updateStep(Step.ASSIGN);
            assign.updateQaOwner(qaOwner);
            assign.updateQaAssignTime(LocalDateTime.now());
        } else {
            log.error("[applyQaAssign] assign is null - ImportDto : {}", importDto);
        }
    }

    @Transactional
    public void updateSuccess(String receiptNo, String receiptSeq){
        Assign assign = assignRepository.findByKey(receiptNo, receiptSeq).orElse(null);

        if (assign != null){
            assign.updateStep(Step.RETURN);
            assign.updateProcessResponseCode(ProcessResponseCode.SUCCESS);
            assign.updateResultDeliveryTime(LocalDateTime.now());
        } else {
            log.error("[updateSuccess] assign is null - receiptNo : {}, receiptSeq : {}", receiptNo, receiptSeq);
        }
    }

    @Transactional
    public void updatComplete(CompleteDto completeDto) {

        String accrNo = completeDto.getACD_NO();
        String dmSeqno = completeDto.getRCT_SEQ();
        Assign assign = assignRepository.findByKey(accrNo, dmSeqno).orElse(null);
        ResultAcceptCode resultAcceptCode = ResultAcceptCode.getEnum(completeDto.getRCI_RSL_CD());

        if (assign != null){
            assign.updateStep(Step.COMPLETE);
            assign.updateComplete(resultAcceptCode, LocalDateTime.now().toString());

        } else {
            log.error("[updateSuccess] assign is null - accrNo : {}, dmSeqno : {}", accrNo, dmSeqno);
        }
    }

    /**
     * 정상 Case - 처리 결과를 카카오에 자동회신
     */
    @Transactional
    public void finishWithAIP(String accrNo, String dmSeqno, ResultDto resultDto) {
        Assign assign = assignRepository.findByKey(accrNo, dmSeqno).orElse(null);
        String resultJson = "";
        try{
            resultJson = new ObjectMapper().writeValueAsString(resultDto);
        } catch(Exception e){
            e.printStackTrace();
        }

        if (assign != null){
           assign.updateQaOwner("AIP");
           assign.updateAutoReturn(true);
           assign.updateResponseJson(resultJson);
        }
        log.debug("[finishWithAIP] receiptNo : {}, receiptSeq : {} ", accrNo, dmSeqno);

        channelService.complete(resultDto);
    }

    /**
     * 정상 Case - 처리 결과를 카카오에 자동회신
     */
    @Transactional
    public void noticeQAAssign(String accrNo, String dmSeqno, ResultDto resultDto) {
        Assign assign = assignRepository.findByKey(accrNo, dmSeqno).orElse(null);

        if (assign != null){
            assign.updateQaOwner("AIP");
            assign.updateAutoReturn(true);
            assign.updateResponseJson(gson.toJson(resultDto));
        }
        log.debug("[noticeQAAssign] receiptNo : {}, receiptSeq : {} ", accrNo, dmSeqno);

        channelService.complete(resultDto);
    }

    /**
     * 정상 Case - QA 처리 결과를 회신
     */
    @Transactional
    public void finishWithQA(String receiptNo, String receiptSeq, ResultDto resultDto) {
        Assign assign = assignRepository.findByKey(receiptNo, receiptSeq).orElse(null);
        String resultJson = "";
        try{
         resultJson = new ObjectMapper().writeValueAsString(resultDto);
        } catch(Exception e){
            e.printStackTrace();
        }
        if (assign != null){
            assign.updateAutoReturn(false);
            assign.updateStep(Step.RETURN);
            assign.updateResponseJson(resultJson);
        }

//        if (autocaptureConfig.getSftpIp().equals("test")){
//            return;
//        }

        log.debug("[finishWithQA] receiptNo : {}, receiptSeq : {} ", receiptNo, receiptSeq);
        channelService.complete(resultDto);

        // TODO: QA Endpoint로 회신하도록 변경함. 향후 수정 필요
//        if (ChannelController.CHECK.equals(assign.getEndpoint())) {
//            log.debug("[finishWithQA] receiptNo : {}, receiptSeq : {} ", receiptNo, receiptSeq);
//            kakaoService.qa(makeResultDto(receiptNo, receiptSeq));
//        }
    }

    @Transactional
    public void deleteDetailsETCS(String receiptNo, String receiptSeq){

        List<ImageResponseDto> images = imageService.findByKey(receiptNo, receiptSeq);
        List<String> etcsImages = images.stream()
                .filter(i -> i.getDocType().equals(DocType.ETCS))
                .map(ImageResponseDto::getFileName).collect(Collectors.toList());

        for (String fileName: etcsImages){
            List<PhoneRepair> phoneRepairs = phoneRepairRepository.findByKeyAndFileName(receiptNo, receiptSeq, fileName);
            for (PhoneRepair phoneRepair : phoneRepairs){
                phoneRepairRepository.delete(phoneRepair);
            }
        }
    }

    /**
     * 카카오 최종결과 수신 업데이트
     */
    @Transactional
    public void complete(CompleteDto completeDto){
        Assign assign = assignRepository.findByKey(completeDto.getACD_NO(), completeDto.getRCT_SEQ())
                .orElseThrow(() -> new IllegalArgumentException("해당 배당목록이 없습니다."));
        assign.updateComplete(
                ResultAcceptCode.getEnum(completeDto.getRCI_RSL_CD()),
                LocalDateTime.now().toString());
    }

    /**
     * 파일 다운로드 완료시간 업데이트
     * processImages 시작시점에 수행
     */
    @Transactional
    public void updateDownloadTime(String receiptNo, String receiptSeq){
        Assign assign = assignRepository.findByKey(receiptNo, receiptSeq)
                .orElseThrow(() -> new IllegalArgumentException("해당 배당목록이 없습니다."));
        assign.updateDownloadTime();
    }

    @Transactional(readOnly = true)
    public AssignResponseDto findByKey(String receiptNo, String receiptSeq) {
        Assign assign = assignRepository.findByKey(receiptNo, receiptSeq)
                .orElse(null);
        return AssignResponseDto.of(assign);
    }

    @Transactional(readOnly = true)
    public Assign getAssignByKey(String receiptNo, String receiptSeq) {
        return  assignRepository.findByKey(receiptNo, receiptSeq).orElse(null);
    }

    @Transactional
    public void delete (Long id) {
        Assign assign = assignRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당  배당목록이 없습니다. id=" + id));
        log.info("[delete] ID : {}", assign.getId());
        assignRepository.delete(assign);
    }

    @Transactional(readOnly = true)
    public List<AssignResponseDto> getAllAssign() {
        return assignRepository.findAll()
                .stream()
                .map(AssignResponseDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AssignResponseDto> search(AssignSearchRequestDto assignSearchRequestDto) {
        log.debug("[search] AssignSearchRequestDto : {}", assignSearchRequestDto.toString());
        String searchStep = assignSearchRequestDto.getStep();
        String step = (searchStep != null && searchStep.equals("전체")) ? null : Step.getEnumByKorName(searchStep).getStepName();
        String searchQaOwner = assignSearchRequestDto.getQaOwner();
        String qaOwner = (searchQaOwner != null && searchQaOwner.equals("전체")) ? null : searchQaOwner;
        AccidentType searchAccidentType = AccidentType.getEnumByMessage(assignSearchRequestDto.getAccidentType());
        String accidentType = searchAccidentType == null? null : searchAccidentType.toString();
        LocalDateTime fromTime = LocalDateTime.of(assignSearchRequestDto.getFromDate(), LocalTime.of(0,0,0));
        LocalDateTime toTime = LocalDateTime.of(assignSearchRequestDto.getToDate(), LocalTime.of(23,59,59));

//        if (assignSearchRequestDto.getSearchType().equals("사고일")){
//            return assignRepository.searchByAccidentDate(step, qaOwner, accidentType, fromTime, toTime)
//                    .stream()
//                    .map(AssignResponseDto::new)
//                    .toList();
//        }

        return assignRepository.searchByAcceptDate(step, qaOwner, accidentType, fromTime, toTime)
                .stream()
                .map(AssignResponseDto::new)
                .toList();
    }

//    @Transactional(readOnly = true)
//    public List<BillingAssignDto> getInputAssign(String fromDate, String toDate) {
//        log.debug("[getInputAssign] AssignSearchRequestDto : {}, {}", fromDate.toString(), toDate.toString());
//        LocalDateTime fromTime = LocalDateTime.of(LocalDate.parse(fromDate, DateTimeFormatter.ISO_DATE), LocalTime.of(0,0,0));
//        LocalDateTime toTime = LocalDateTime.of(LocalDate.parse(toDate, DateTimeFormatter.ISO_DATE), LocalTime.of(23,59,59));
//        return assignRepository.getInputAssign(fromTime, toTime);
//    }
}
