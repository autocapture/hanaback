package com.aimskr.ac2.common.enums;

import lombok.Getter;

@Getter
public enum ProcessType {
    PT11("11", "대인I(차대차)")
    , PT12("12", "대인II")
    , PT13("13", "자손(차대차, 피보험자 일방 과실)")
    , PT14("14", "자동차상해")
    , PT15("15", "무보험대인")
    , PT16("16", "무-타차운전-자손")
    , PT17("17", "무-타차운전-대인")

    , PT21("21", "자차(피보험자 단독 사고)")
    , PT22("22", "대물I")
    , PT23("23", "대물II")
    , PT24("24", "무-타차운전-자차")
    , PT25("25", "무-타차운전-대물")
    ;

    String code;
    String name;

    ProcessType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ProcessType findByCode(String code) {
        for (ProcessType value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

    public static ProcessType findByName(String name) {
        for (ProcessType value : values()) {
            if (value.name.equals(name)) {
                return value;
            }
        }
        return null;
    }
}
