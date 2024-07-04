package com.aimskr.ac2.hana.backend.core.medical.service;


import com.aimskr.ac2.hana.backend.channel.feign.HospitalFeign;
import com.aimskr.ac2.hana.backend.core.medical.dto.HospitalResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class HospitalService {

    private final HospitalFeign hospitalFeign;
    //TODO : Configuration으로 변경
    public List<HospitalResponseDto> getHospitalInfo(@Nullable String code, @Nullable  String bn, @Nullable  String name, @Nullable String like) {

        log.debug("[HospitalService getHospitalInfo] code = {}, bn = {}, name = {}, like = {}", code, bn, name, like);
        if (bn != null) {
            bn = bn.replaceAll("[^0-9]", "");
        }

        List<HospitalResponseDto> hospitalInfos = hospitalFeign.getHospitalInfo(code, bn, name, like);
        log.debug("[HospitalService getHospitalInfo] hospitals = {}", hospitalInfos);

        if (hospitalInfos.size() == 0 && StringUtils.hasText(name)) {
            List<HospitalResponseDto> hospitalInfos2 = hospitalFeign.getHospitalInfo(code, bn, "", name);
            log.debug("[HospitalService] getHospitalInfo hospitals2 = {}", hospitalInfos2);
            return hospitalInfos2;

        }

        return hospitalInfos;
    }

}
