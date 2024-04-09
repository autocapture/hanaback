package com.aimskr.ac2.kakao.backend.common.util

import com.aimskr.ac2.common.util.AES256Cipher
import spock.lang.Specification

class AES256CipherTest extends Specification {
    def "test encrypt"() {
        given:
        def name = "홍길동"
        def birth = "730101"

        when:
        def encryptedName = AES256Cipher.encrypt(name)
        println "encryptedName: $encryptedName"
        def encryptedBirth  = AES256Cipher.encrypt(birth)
        println "encryptedBirth: $encryptedBirth"
        def decryptedName = AES256Cipher.decrypt(encryptedName)
        def decryptedBirth = AES256Cipher.decrypt(encryptedBirth)

        then:
        name == decryptedName
        birth == decryptedBirth
    }


    def "test json"() {
        given:
        def e1 = "F7AA8DB0AAB0C19BAE45C640E33EC878"
        def e2 = "A81A0750BEA6EF39DD5A95BC2C962BE6"

        when:
        def d1 = AES256Cipher.decrypt(e1)
        def d2 = AES256Cipher.decrypt(e2)

        then:
        println "e1: $e1"
        println "d1: $d1"
        println "e2: $e2"
        println "d2: $d2"
    }
}
