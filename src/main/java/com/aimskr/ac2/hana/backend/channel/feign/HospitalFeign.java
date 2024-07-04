package com.aimskr.ac2.hana.backend.channel.feign;

import com.aimskr.ac2.hana.backend.core.medical.dto.HospitalResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(url="https://dev.aimskr.com:44308/common/v2/hospital", name="hospitalFeign")
public interface HospitalFeign {
    @GetMapping
    public List<HospitalResponseDto> getHospitalInfo(@RequestParam(value = "code", required = false) String code,
                                                     @RequestParam(value = "bn", required = false) String bn,
                                                     @RequestParam(value = "name", required = false) String name,
                                                     @RequestParam(value = "like", required = false) String like);
}
