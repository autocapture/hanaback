package com.aimskr.ac2.hana.backend.core.medical.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KcdRepository extends JpaRepository<Kcd, Long> {

    Optional<Kcd> findByKcdCd(String kcdFinal);
}
