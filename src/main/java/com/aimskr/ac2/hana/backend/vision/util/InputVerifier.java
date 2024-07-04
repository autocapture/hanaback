package com.aimskr.ac2.hana.backend.vision.util;

import com.aimskr.ac2.hana.backend.channel.json.ImgFileInfoDto;
import com.aimskr.ac2.hana.backend.channel.json.ImportDto;
import com.aimskr.ac2.hana.backend.core.medical.dto.HospitalResponseDto;
import com.aimskr.ac2.hana.backend.core.medical.service.HospitalService;
import com.aimskr.ac2.hana.backend.util.service.CacheService;
import com.aimskr.ac2.hana.backend.vision.domain.AiDetail;
import com.aimskr.ac2.hana.backend.vision.domain.AiDetailRepository;
import com.aimskr.ac2.hana.backend.vision.dto.VisionResult;
import com.aimskr.ac2.common.enums.detail.ItemType;
import com.aimskr.ac2.common.enums.doc.DocType;
import com.aimskr.ac2.common.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
//
@Slf4j
@RequiredArgsConstructor
@Service
public class InputVerifier {

    private final AiDetailRepository aiDetailRepository;
    private final HospitalService hospitalService;

    @Transactional
    public VisionResult verifyInput(ImportDto importDto, ImgFileInfoDto imgFileInfoDto, List<AiDetail> aiDetails, DocType docType){

        String rqsReqId = importDto.getRqsReqId();
        String accrNo = importDto.getAcdNo();
        String dmSeqno = importDto.getRctSeq();

        String fileName = FileUtil.changeExtToJpg(imgFileInfoDto.getImgFileNm());

        List<AiDetail> aiDetailPrevs = aiDetailRepository.findByKeyAndFileName(rqsReqId, accrNo, dmSeqno, fileName);

        // 기존 AI Detail 삭제
        if (aiDetailPrevs.size() > 0){

            for (AiDetail aiDetailPrev: aiDetailPrevs){
                aiDetailRepository.delete(aiDetailPrev);
            }

        }
        if (docType.name().startsWith("MD")){

            aiDetails = verifyMedInfo(aiDetails, importDto.getAcdNo(), importDto.getRctSeq(), fileName, docType);

        } else if (docType.equals(DocType.CIPS)){
            aiDetails = verifyCIPSInfo(aiDetails, importDto.getAcdNo(), importDto.getRctSeq(), fileName, docType);

        }


        //QA Rule
        for (AiDetail aiDetail : aiDetails) {
            aiDetail.updateKey(rqsReqId, accrNo, dmSeqno, fileName);


            if (aiDetail.getItemName().length() > 254){
                aiDetail.updateValue("");
            }

            aiDetailRepository.save(aiDetail);
            log.debug("[InputVerifier] - inputVerified, aiDetail: {}", aiDetail);
//            if (!aiDetail.getItemName().equals(ItemType.AIR_AIRPORT.getItemName()) &&
//                    !aiDetail.getItemName().equals(ItemType.BIZ_TEL.getItemName())){
//                Detail detail = aiDetail.toDetail();
//                detailRepository.save(detail);
//            }
        }

//        List<Detail> bizDetails = detailRepository.findItemValueByItemName(receiptNo, receiptSeq, ItemType.BIZ_NO.getItemName());
//        if (bizDetails.size() > 0){
//            verifyBizno(bizDetails);
//        }
//
//        List<Detail> details_f = detailRepository.findByFileName(fileName);
//        log.debug("[verifyInput] - finalDetails, : {}", details_f);

        // Validation(QA) Rule
        return VisionResult.builder()
                .isError(false)
                .isInputRequired(true)
                .isQa(false)
                .docType(docType)
                .content("")
                .build();

    }

    private List<AiDetail> verifyCIPSInfo(List<AiDetail> aiDetails, String acdNo, String rctSeq, String fileName, DocType docType) {

        List<AiDetail> newDetails = new ArrayList<>();

        for (AiDetail aiDetail: aiDetails){

            if (aiDetail.getItemName().equals(ItemType.CIPS_INJURY_GRADE.getItemName())){

                String itemValue = aiDetail.getItemValue();
                String digitValue = itemValue.replaceAll("[^0-9]", "");
                if (digitValue.length() > 2){
                    String gup = digitValue.substring(0, 2);
                    AiDetail gupDetail = AiDetail.builder()
                            .itemCode(ItemType.CIPS_INJURY_GUP.getItemCode())
                            .itemName(ItemType.CIPS_INJURY_GUP.getItemName())
                            .itemValue(gup)
                            .accuracy(aiDetail.getAccuracy())
                            .build();
                    newDetails.add(gupDetail);
                }
                if (digitValue.length() == 4) {

                    String hang = digitValue.substring(2);
                    AiDetail gupDetail = AiDetail.builder()
                            .itemCode(ItemType.CIPS_INJURY_HANG.getItemCode())
                            .itemName(ItemType.CIPS_INJURY_HANG.getItemName())
                            .itemValue(hang)
                            .accuracy(aiDetail.getAccuracy())
                            .build();
                    newDetails.add(gupDetail);
                }
            } else{
                newDetails.add(aiDetail);
            }

        }
        return newDetails;
    }

    public List<AiDetail> verifyMedInfo(List<AiDetail> aiDetails, String receiptNo, String receiptSeq, String fileName, DocType docType){

        AiDetail bizNameDetail = aiDetails.stream().filter(aiDetail -> aiDetail.getItemName().equals("의료기관명"))
                .findFirst().orElse(null);


        // 사업자등록번호로 저장되지 않은 경우, 병원명으로 병원정보 조회 (주소 생성 안되어 있음)
        if (bizNameDetail != null && StringUtils.hasText(bizNameDetail.getItemValue())){

            String bizName = bizNameDetail.getItemValue();
            List<AiDetail> hspDetails = makeMatchedHspInfos(receiptNo, receiptSeq, fileName, null, bizName, bizNameDetail);

            log.debug("[verifyMedInfo] - hspDetails: {}", hspDetails);

            if (hspDetails.size() > 0){
                aiDetails.removeIf(d -> ItemType.HSP_NAME.getItemName().equals(d.getItemName()));
                aiDetails.addAll(hspDetails);
            }

        }


        return aiDetails;
    }

    public List<AiDetail> makeMatchedHspInfos(String receiptNo, String receiptSeq, String fileName, String bizNo, String name, AiDetail aiDetail){

        log.debug("[InputVerifier] - makeHspInfoDetails, receiptNo: {}, receiptSeq: {}, fileName: {}, bizNo: {}, name: {}", receiptNo, receiptSeq, fileName, bizNo, name);

        try{
            List<HospitalResponseDto> hospitalResponseDtos = hospitalService.getHospitalInfo(null, bizNo, name, null);
            if (hospitalResponseDtos.size() == 1) {
                HospitalResponseDto hospitalResponseDto = hospitalResponseDtos.get(0);
                List<AiDetail> hspDetails = makeMatchedHspDetails(hospitalResponseDto, aiDetail);
                return hspDetails;
            } else if (hospitalResponseDtos.size() == 0){

                List<HospitalResponseDto> likeHospitalResponseDtos = hospitalService.getHospitalInfo(null, null, null, name);
                if (likeHospitalResponseDtos.size() == 1) {
                    HospitalResponseDto hospitalResponseDto = hospitalResponseDtos.get(0);
                    List<AiDetail> hspDetails = makeMatchedHspDetails(hospitalResponseDto, aiDetail);
                    return hspDetails;
                }
            }


        } catch(Exception e){
            e.printStackTrace();
        }

        return new ArrayList<>();

    }


    public List<AiDetail> makeMatchedHspDetails(HospitalResponseDto hospitalResponseDto, AiDetail aiDetail){

        log.debug("[makeMatchedHspDetails] hospitalResponseDto: {}", hospitalResponseDto);
        List<AiDetail> hspDetails = new ArrayList<>();

        String rqsReqId = aiDetail.getRqsReqId();
        String accrNo = aiDetail.getAccrNo();
        String dmSeqno = aiDetail.getDmSeqno();
        String fileName = aiDetail.getFileName();

        String hspName = hospitalResponseDto.getHospitalName();
        String fssNo = hospitalResponseDto.getHospitalCode();
        String address = hospitalResponseDto.getAddress();
        String bizNumber = hospitalResponseDto.getBusinessNumber();
        String zipCode = hospitalResponseDto.getZipCode();

        AiDetail bizNoDetail = AiDetail.builder()
                .rqsReqId(rqsReqId)
                .accrNo(accrNo)
                .dmSeqno(dmSeqno)
                .fileName(fileName)
                .itemCode(ItemType.HSP_BIZ_NO.getItemCode())
                .itemName(ItemType.HSP_BIZ_NO.getItemCode())
                .itemValue(bizNumber)
                .accuracy(1.0)
                .build();
        hspDetails.add(bizNoDetail);


        AiDetail hspNameDetail = AiDetail.builder()
                .rqsReqId(rqsReqId)
                .accrNo(accrNo)
                .dmSeqno(dmSeqno)
                .fileName(fileName)
                .itemCode(ItemType.HSP_NAME.getItemCode())
                .itemName(ItemType.HSP_NAME.getItemName())
                .itemValue(hspName)
                .accuracy(1.0)
                .build();
        hspDetails.add(hspNameDetail);

        AiDetail addressDetail = AiDetail.builder()
                .rqsReqId(rqsReqId)
                .accrNo(accrNo)
                .dmSeqno(dmSeqno)
                .fileName(fileName)
                .itemCode(ItemType.HSP_ADDRESS.getItemCode())
                .itemName(ItemType.HSP_ADDRESS.getItemName())
                .itemValue(address)
                .accuracy(1.0)
                .build();
        hspDetails.add(addressDetail);

        AiDetail fssDetail = AiDetail.builder()
                .rqsReqId(rqsReqId)
                .accrNo(accrNo)
                .dmSeqno(dmSeqno)
                .fileName(fileName)
                .itemCode(ItemType.HSP_TYPE_CODE.getItemCode())
                .itemName(ItemType.HSP_TYPE_CODE.getItemName())
                .itemValue(fssNo)
                .accuracy(1.0)
                .build();
        hspDetails.add(fssDetail);

        AiDetail zipCodeDetail = AiDetail.builder()
                .rqsReqId(rqsReqId)
                .accrNo(accrNo)
                .dmSeqno(dmSeqno)
                .fileName(fileName)
                .itemCode(ItemType.HSP_ZIP_CODE.getItemCode())
                .itemName(ItemType.HSP_ZIP_CODE.getItemName())
                .itemValue(zipCode)
                .accuracy(1.0)
                .build();
        hspDetails.add(zipCodeDetail);

        return hspDetails;

    }

}
