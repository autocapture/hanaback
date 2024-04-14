package com.aimskr.ac2.hana.backend.core.detail.service;



import com.aimskr.ac2.hana.backend.core.detail.domain.Detail;
import com.aimskr.ac2.hana.backend.core.detail.domain.DetailRepository;
import com.aimskr.ac2.hana.backend.core.detail.dto.DetailResponseDto;
import com.aimskr.ac2.hana.backend.core.image.dto.ImageSearchRequestDto;
import com.aimskr.ac2.hana.backend.core.image.dto.ImageSingleSearchRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DetailService {
    private final DetailRepository detailRepository;

    @Transactional(readOnly = true)
    public List<DetailResponseDto> findByFileName(String rqsReqId, String  fileName) {
        List<DetailResponseDto> detailResponseDtos = detailRepository.findByFileName(rqsReqId, fileName)
                .stream()
                .map(DetailResponseDto::new)
                .toList();

        log.debug("[findByFileName] size : {}", detailResponseDtos.size());
        return detailResponseDtos;
    }

    @Transactional(readOnly = true)
    public List<DetailResponseDto> findByKey(ImageSearchRequestDto imageSearchRequestDto) {
        String rqsReqId = imageSearchRequestDto.getRqsReqId();
        String accrNo = imageSearchRequestDto.getAccrNo();
        String dmSeqno = imageSearchRequestDto.getDmSeqno();

        List<DetailResponseDto> detailResponseDtos = detailRepository.findByKey(rqsReqId, accrNo, dmSeqno)
                .stream()
                .map(DetailResponseDto::new)
                .toList();

        log.debug("[findByKey] size : {}", detailResponseDtos.size());
        return detailResponseDtos;
    }

    @Transactional(readOnly = true)
    public List<DetailResponseDto> findByKeyAndFileName(ImageSingleSearchRequestDto imageSingleSearchRequestDto) {
        String rqsReqId = imageSingleSearchRequestDto.getRqsReqId();
        String accrNo = imageSingleSearchRequestDto.getAccrNo();
        String dmSeqno = imageSingleSearchRequestDto.getDmSeqno();
        String fileName = imageSingleSearchRequestDto.getFileName();

        List<DetailResponseDto> detailResponseDtos
                = detailRepository.findByKeyAndFileName(rqsReqId, accrNo, dmSeqno, fileName)
                .stream()
                .map(DetailResponseDto::new)
                .toList();

        log.debug("[findByKeyAndFileName] size : {}", detailResponseDtos.size());
        return detailResponseDtos;
    }

    @Transactional
    public void saveDetail(Detail detail){
        detailRepository.save(detail);
    }

}
