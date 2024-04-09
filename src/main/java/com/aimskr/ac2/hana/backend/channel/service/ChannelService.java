package com.aimskr.ac2.hana.backend.channel.service;


import com.aimskr.ac2.hana.backend.channel.json.HeaderDto;
import com.aimskr.ac2.hana.backend.channel.json.ResultDto;
import com.aimskr.ac2.common.config.AutocaptureConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
@Service
@ComponentScan("com.aimskr.ac2.common")
public class ChannelService {
    private final AutocaptureConfig autocaptureConfig;
//    public void complete(ResultDto resultDto) {
    public ResponseEntity<String> complete(ResultDto resultDto) {
        log.debug("[complete] resultDto = {}", resultDto);
        String accrNo = resultDto.getAcdNo();
        RestTemplate restTemplate = buildRestTemplate();
        HttpHeaders requestHeaders = buildHeaders();
        log.debug("[complete] requestHeaders : {}", requestHeaders.toString());

        HeaderDto headerDto = buildCarrotHeaderDto();

        HttpEntity<ResultDto> resultDtoHttpEntity = new HttpEntity<>(resultDto, requestHeaders);

        String endPoint = buildEndPoint(resultDto.getAcdNo());
        log.debug("[complete] endPoint : {}", endPoint);
        ResponseEntity<String>  finalResponse = restTemplate.exchange(endPoint, HttpMethod.POST, resultDtoHttpEntity, String.class);
        log.debug("[complete] fianlResponse : {}", finalResponse);
        log.debug("[complete]¡ finalResponse.getBody() : {}", finalResponse.getBody());
        return finalResponse;
    }

//    public ResponseEntity<String> qa(ResultDto resultDto) {
//        log.debug("[complete] resultDto = {}", resultDto);
//        RestTemplate restTemplate = new RestTemplateBuilder()
//                .additionalMessageConverters(
//                        new StringHttpMessageConverter(StandardCharsets.UTF_8),
//                        new MappingJackson2HttpMessageConverter())
//                .build();
//        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//        HttpHeaders requestHeaders = new HttpHeaders();
//        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
//        requestHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
//        requestHeaders.add(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8.name());
//        HttpEntity<ResultDto> resultDtoHttpEntity = new HttpEntity<>(resultDto, requestHeaders);
//        String endPoint = buildQaEndPoint();
//        ResponseEntity<String>  kakaoResponse = restTemplate.postForEntity(endPoint, resultDtoHttpEntity, String.class);
//        log.debug("[complete] kakaoResponse : {}", kakaoResponse);
//        log.debug("[complete] kakaoResponse.getBody() : {}", kakaoResponse.getBody());
//        return kakaoResponse;
//    }

    public HeaderDto buildCarrotHeaderDto(){

        return HeaderDto.builder()
                .tlmSpecd("0200")
                .txCode("transLseX102")
                .stfno("7900002")
                .trsOrgcd("AIM")
                .rcvOrgcd("AIM")
                .trsDthms(String.valueOf(System.currentTimeMillis()))
                .fnCd("CP")
                .build();
    }

    public RestTemplate buildRestTemplate() {
        RestTemplate restTemplate =  new RestTemplateBuilder()
                .additionalMessageConverters(
                        new StringHttpMessageConverter(StandardCharsets.UTF_8),
                        new MappingJackson2HttpMessageConverter())
                .build();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        return restTemplate;
    }

    public String buildEndPoint(String accrNo) {
        return autocaptureConfig.getApiServer();
    }

    public String buildQaEndPoint() {
        return autocaptureConfig.getApiServer() + autocaptureConfig.getQaPath();
    }

    public String buildResultPath(String accrNo) {
        return autocaptureConfig.getResultPath() + "/" + accrNo + "/complete";
    }

    public HttpHeaders buildHeaders() {
        String timestamp = String.valueOf(System.currentTimeMillis());
//        String authHeader = "KakaoPI " + HmacUtil.generate(autocaptureConfig.getKakaoCredential(), path, timestamp);
        HttpHeaders requestHeaders = new HttpHeaders();
//        requestHeaders.set("Authorization", authHeader);
        // set ("Authorization" , "KakaoPI fjdksalfjdksalfjlakfjdksalfjksalf")
        requestHeaders.set("tlmSpecd", "0200");
        requestHeaders.set("txCode", "transLseX102");
        requestHeaders.set("stfno", "7900002");
        requestHeaders.set("trsOrgcd", "AIM");
        requestHeaders.set("rcvOrgcd", "AIM");
        requestHeaders.set("trsDthms", timestamp);
        requestHeaders.set("fnCd", "CP");
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        requestHeaders.add(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8.name());
        return requestHeaders;
    }
}
