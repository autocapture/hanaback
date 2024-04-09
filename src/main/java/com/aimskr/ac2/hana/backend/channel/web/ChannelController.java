package com.aimskr.ac2.hana.backend.channel.web;


import com.aimskr.ac2.common.enums.RequestCode;
import com.aimskr.ac2.common.enums.status.AcceptStatus;
import com.aimskr.ac2.hana.backend.channel.json.AcceptResultDto;
import com.aimskr.ac2.hana.backend.channel.json.ImportDto;
import com.aimskr.ac2.hana.backend.channel.json.ResultDto;
import com.aimskr.ac2.hana.backend.channel.service.AsyncService;
import com.aimskr.ac2.hana.backend.core.assign.service.AssignService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/hana/v1/")
public class ChannelController {
    private final AsyncService asyncService;
    private final AssignService assignService;
    private final Gson gson;

    @PostMapping("/order")
    public ResponseEntity<AcceptResultDto> save(@RequestBody ImportDto importDto) {
        log.info("[save] /order - importDto : {},", gson.toJson(importDto));

        try {
            if (importDto == null) {
                log.error("[save] /order - invalid : importDto is null");
                return ResponseEntity.badRequest().body(AcceptResultDto.of(AcceptStatus.INVALID));
            }

            RequestCode requestCode = RequestCode.findByCode(importDto.getRqsTpCd());
            // T00, 처리요청 전문일 경우
            if (RequestCode.REQUEST.equals(requestCode)) {
                String validation = importDto.checkValid();
                if (!validation.equals(ImportDto.VALID)) {
                    log.error("[save] /order - invalid : {}", validation);
                    return ResponseEntity.badRequest().body(AcceptResultDto.of(AcceptStatus.INVALID));
                }

                boolean duplicity = assignService.checkDupAssign(importDto);
                // 중복요청일 경우, 배당을 하지 않고 INVALID로 응답
//                if (duplicity){
//                    asyncService.processDupRequest(importDto);
//                    return ResponseEntity.ok().body(AcceptResultDto.of(AcceptStatus.OK));
//                }

//                else if (validation.equals(ImportDto.VALID) && !duplicity) {
                if (validation.equals(ImportDto.VALID)) {
                    log.debug("[save] validation success - processRequest start");
                    asyncService.processRequest(importDto);
                    return ResponseEntity.ok().body(AcceptResultDto.of(AcceptStatus.OK));
                } else {
                    log.error("[save] /order - invalid : {}", validation);
//                assignService.finishWithValidationError(importDto);
                    return ResponseEntity.badRequest().body(AcceptResultDto.of(AcceptStatus.INVALID));
                }
            }
            // R00 수신성공 or R99 수신오류인 경우
            else if (RequestCode.REQUEST.equals(requestCode)) {
                // TODO 처리결과 업데이트
                return ResponseEntity.ok().body(AcceptResultDto.of(AcceptStatus.OK));
            } else {
                log.error("[save] /order - invalid :Worng RequestCode : {}", importDto.getRqsTpCd());
                return ResponseEntity.badRequest().body(AcceptResultDto.of(AcceptStatus.INVALID));
            }
        } catch (Exception e) {
            log.error("[save] /order - error : {}", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(AcceptResultDto.of(AcceptStatus.ERROR));
        }

    }

    @PostMapping("/reply")
    public void reply(@RequestBody ResultDto resultDto) {
        log.info("/hana/v1/reply - ResultDto : {}", resultDto);
        log.info("/hana/v1/reply - ResultDto : {}", gson.toJson(resultDto));
        try {
            RestTemplate restTemplate = buildRestTemplate();
            HttpHeaders requestHeaders = buildHeaders();

            HttpEntity<ResultDto> resultDtoHttpEntity = new HttpEntity<>(resultDto, requestHeaders);
            String endPoint = buildEndPoint();
            log.debug("[reply] endpoint : {}", endPoint);
            ResponseEntity<String>  kakaoResponse = restTemplate.exchange(endPoint, HttpMethod.POST, resultDtoHttpEntity, String.class);
            log.debug("[reply] Response : {}", kakaoResponse);
            log.debug("[reply] Response.getBody() : {}", kakaoResponse.getBody());

        } catch (Exception e) {
            log.error("/shez/v2/reply - error : {}", e.getMessage());
            e.printStackTrace();
        }
    }

    @PostMapping("/reply2")
    public void reply2(@RequestBody ResultDto resultDto) {
        log.info("/hana/v1/reply - ResultDto : {}", resultDto);
        log.info("/hana/v1/reply - ResultDto : {}", gson.toJson(resultDto));
        try {
            RestTemplate restTemplate = buildRestTemplate();
            HttpHeaders requestHeaders = buildHeaders();

            HttpEntity<ResultDto> resultDtoHttpEntity = new HttpEntity<>(resultDto, requestHeaders);
            String endPoint = buildEndPoint2();
            log.debug("[reply] endpoint : {}", endPoint);
            ResponseEntity<String>  kakaoResponse = restTemplate.exchange(endPoint, HttpMethod.POST, resultDtoHttpEntity, String.class);
            log.debug("[reply] Response : {}", kakaoResponse);
            log.debug("[reply] Response.getBody() : {}", kakaoResponse.getBody());

        } catch (Exception e) {
            log.error("/shez/v2/reply - error : {}", e.getMessage());
            e.printStackTrace();
        }
    }


    @PostMapping("/mock")
    public void mock(@RequestBody ResultDto resultDto) {
        log.info("[mock] /mock - resultDto : {},", gson.toJson(resultDto));
    }

    // TODO - 임시코드 : 삭제할 것
    private String buildEndPoint() {
        return "https://dgate.hanainsure.co.kr:31010/aims/ProcResult";
    }
    private String buildEndPoint2() {
        return "https://dev.aimskr.com:44306/hana/v1/mock";
    }


    private RestTemplate buildRestTemplate() {
        RestTemplate restTemplate =  new RestTemplateBuilder()
                .additionalMessageConverters(
                        new StringHttpMessageConverter(StandardCharsets.UTF_8),
                        new MappingJackson2HttpMessageConverter())
                .build();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        return restTemplate;
    }

    private HttpHeaders buildHeaders() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        requestHeaders.add(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8.name());
        return requestHeaders;
    }

}
