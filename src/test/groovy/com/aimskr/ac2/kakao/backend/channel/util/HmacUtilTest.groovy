package com.aimskr.ac2.kakao.backend.channel.util

import spock.lang.Specification

class HmacUtilTest extends Specification {
    def "test 카카오 HMAC 생성"() {
        given:
        def key = "tempSecret"
        def path = "/claim/v2/receipt/ocr/202307200000101/complete"
        // System.currentTimeMillis().toString()
        def timestap = "1690356988529"

        when:
        def hashedCredentail = HmacUtil.generate(key, path, timestap)

        then:
        hashedCredentail == "9OOU4/zqjpRSTgOlf9XhJqKv75cUwp7/GHxRpvbcI/D5hHQ339LehAxPdINHGmDqofDGBwo+zA8B3wbd3hjsRQ=="
    }
}
