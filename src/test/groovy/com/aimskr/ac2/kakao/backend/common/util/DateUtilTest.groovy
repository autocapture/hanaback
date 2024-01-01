package com.aimskr.ac2.kakao.backend.common.util

import spock.lang.Specification

class DateUtilTest extends Specification {
    def "test checkDateFormat"() {
        given:
        def test1 = "2020-01-01"
        def test2 = "20200101"
        def test3 = "notdate"

        when:
        def result1_t = DateUtil.checkDateFormat(test1, DateUtil.YYYYMMDD_DASH)
        def result1_f  = DateUtil.checkDateFormat(test1, DateUtil.YYYYMMDD)
        def result2_t = DateUtil.checkDateFormat(test2, DateUtil.YYYYMMDD)
        def result2_f = DateUtil.checkDateFormat(test2, DateUtil.YYYYMMDD_SLASH)
        def result3_f = DateUtil.checkDateFormat(test3, DateUtil.YYYYMMDD)

        then:
        result1_t == true
        result1_f == false
        result2_t == true
        result2_f == false
        result3_f == false
    }
}
