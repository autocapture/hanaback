package com.aimskr.ac2.kakao.backend.common.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "filepath")
public class FilepathConfig {
    /**
     * HGI를 시뮬레이션해서 한화손보 이미지 저장소 역할을 하는 디렉토리
     * 해당 디렉토리에 이미지가 그대로 들어있음
     */
    private String importDir;

    /**
     * 한화손보에서 내려받은 원본을 저장하는 디렉토리
     * 이미지는 originDir/${accrNo} 에 저장
     */
    private String originDir;

    /**
     * 원본에 대한 이미지 처리를 끝내고 실제
     * 이미지는 acDir/${accrNo} 에 저장
     */
    private String acDir;


    /**
     * 리포트 파일이 생성될 디렉토리
     */
    private String reportDir;


    /**
     * vision 결과 파일 생성 1
     */
    private String visorgDir;

    /**
     * vision 결과 파일 생성 2
     */
    private String visDir;

}