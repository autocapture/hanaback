package com.aimskr.ac2.kakao.backend.common.enums.status;

import lombok.Getter;

@Getter
public enum Step {

    ACCEPT("accept", "접수"),
    ASSIGN("assign", "QA"),
    RETURN("return", "회신"),
    COMPLETE("complete", "완료");

    private String stepName;
    private String korName;

    Step(String stepName, String korName){
        this.stepName = stepName;
        this.korName = korName;
    }

    public static Step getEnum(String stepName) {
        for (Step step: values()){
            if (step.stepName.equals(stepName)){
                return step;
            }
        }
        return null;
    }

    public static Step getEnumByKorName(String korName) {
        for (Step step: values()){
            if (step.korName.equals(korName)){
                return step;
            }
        }
        return null;
    }
}
