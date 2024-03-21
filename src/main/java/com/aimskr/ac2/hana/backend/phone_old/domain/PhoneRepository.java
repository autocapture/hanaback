package com.aimskr.ac2.hana.backend.phone_old.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
    public List<Phone> findByAccrNoAndImageName(String accrNo, String imageName);
}
