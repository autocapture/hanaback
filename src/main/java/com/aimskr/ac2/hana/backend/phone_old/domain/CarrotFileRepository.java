package com.aimskr.ac2.hana.backend.phone_old.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrotFileRepository extends JpaRepository<CarrotFile, Long> {
    public CarrotFile findByAccrNoAndImageName(String accrNo, String imageName);
}
