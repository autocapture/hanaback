package com.aimskr.ac2.hana.backend.core.phone.service;

import com.aimskr.ac2.hana.backend.core.image.dto.ImageSearchRequestDto;
import com.aimskr.ac2.hana.backend.core.image.dto.ImageSingleSearchRequestDto;
import com.aimskr.ac2.hana.backend.core.phone.domain.*;
import com.aimskr.ac2.hana.backend.core.phone.dto.*;
import com.aimskr.ac2.hana.backend.util.service.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PhoneRepairService {
    private final PhoneRepairRepository phoneRepairRepository;
    private final AiPhoneRepairRepository aiPhoneRepairRepository;
    private final PhoneRepairDetailRepository phoneRepairDetailRepository;
    private final CacheService cacheService;
//    private final InputVerifier inputVerifier;

    @Transactional(readOnly = true)
    public List<PhoneRepairResponseDto> findByFileName(String  fileName) {
        List<PhoneRepairResponseDto> phoneRepairResponseDtos = phoneRepairRepository.findByFileName(fileName)
                .stream()
                .map(PhoneRepairResponseDto::new)
                .toList();

        log.debug("[findByFileName] size : {}", phoneRepairResponseDtos.size());
        return phoneRepairResponseDtos;
    }

    @Transactional(readOnly = true)
    public List<PhoneRepairResponseDto> findByKey(ImageSearchRequestDto imageSearchRequestDto) {
        String accrNo = imageSearchRequestDto.getAccrNo();
        String dmSeqno = imageSearchRequestDto.getDmSeqno();

        List<PhoneRepairResponseDto> phoneRepairResponseDtos = phoneRepairRepository.findByKey(accrNo, dmSeqno)
                .stream()
                .map(PhoneRepairResponseDto::new)
                .toList();

        log.debug("[findByKey] size : {}", phoneRepairResponseDtos.size());
        return phoneRepairResponseDtos;
    }

    @Transactional(readOnly = true)
    public List<PhoneRepairResponseDto> findByKeyAndFileName(String accrNo, String dmSeqnom, String fileName) {

        List<PhoneRepairResponseDto> phoneRepairResponseDtos
                = phoneRepairRepository.findByKeyAndFileName(accrNo, dmSeqnom, fileName)
                .stream()
                .map(PhoneRepairResponseDto::new)
                .toList();

        log.debug("[findByKeyAndFileName] size : {}", phoneRepairResponseDtos.size());
        return phoneRepairResponseDtos;
    }

    @Transactional
    public void saveDetail(PhoneRepair phoneRepair){
        phoneRepairRepository.save(phoneRepair);
    }

    @Transactional
    public void saveDetailFromAiDetails(String accrNo, String dmSeqno, String fileName){

       List<AiPhoneRepair> aiPhoneRepairs = aiPhoneRepairRepository.findByKeyAndFileName(accrNo, dmSeqno, fileName);
       for (AiPhoneRepair aiPhoneRepair : aiPhoneRepairs) {


           PhoneRepair phoneRepair = aiPhoneRepair.toDetail();
           log.debug("[saveDetailFromAiDetails] detail : {}", phoneRepair);
           phoneRepairRepository.save(phoneRepair);

       }
    }

    @Transactional
    public List<PhoneRepairDetailResponseDto> getPhoneRepairDetail(String accrNo, String receiptSeq, String fileName){

        List<PhoneRepairDetail> phoneRepairDetails = phoneRepairDetailRepository.findByKeyAndFileName(accrNo, receiptSeq, fileName);

        return phoneRepairDetails.stream()
                .map(PhoneRepairDetailResponseDto::new)
                .toList();
    }

    @Transactional
    public void savePhoneRepairDetail(PhoneRepairDetailListDto phoneRepairDetailListDto){

        String accrNo = phoneRepairDetailListDto.getAccrNo();
        String dmSeqno = phoneRepairDetailListDto.getDmSeqno();
        String fileName = phoneRepairDetailListDto.getFileName();
        List<PhoneRepairDetailRequestDto> accdList = phoneRepairDetailListDto.getAccdList();

        List<PhoneRepairDetail> prevPhoneRepairDetails = phoneRepairDetailRepository.findByKeyAndFileName(accrNo, dmSeqno, fileName);

        for (PhoneRepairDetail prevPhoneRepairDetail: prevPhoneRepairDetails){
            phoneRepairDetailRepository.delete(prevPhoneRepairDetail);
        }

        for (PhoneRepairDetailRequestDto phoneRepairDetailRequestDto : accdList) {
            PhoneRepairDetail phoneRepairDetail = phoneRepairDetailRequestDto.toEntity();
            PhoneAccdDto phoneAccdDto = getMatchedPhoneAccdDto(phoneRepairDetail.getAccdCd());

            phoneRepairDetail.updateMappedAccdValue(phoneAccdDto);

            phoneRepairDetail.updateKeyAndFile(accrNo, dmSeqno, fileName);
            phoneRepairDetailRepository.save(phoneRepairDetail);
        }

    }


    public PhoneAccdDto getMatchedPhoneAccdDto (String rawCode){
        List<PhoneAccd> accdList = cacheService.getPhoneAccd();

        for (PhoneAccd accd: accdList) {
            if (rawCode.contains(accd.getAccdCd().replace(" ", ""))) {
                return new PhoneAccdDto(accd);
            }
            if (rawCode.contains(accd.getAccdNm().replace(" ", ""))) {
                return new PhoneAccdDto(accd);
            }
        }

        if (rawCode.contains("합계") && rawCode.contains("파손")){
            return PhoneAccdDto.buildRepairServiceAccd();
        } else if (rawCode.contains("합계") && rawCode.contains("수리") && !rawCode.contains("파손")){
            return PhoneAccdDto.buildNonRepairServiceAccd();
        }

        PhoneAccdDto phoneAccdDto = PhoneAccdDto.buildDefault();
        phoneAccdDto.setAccdCd(rawCode);

        return phoneAccdDto;

    }

}
