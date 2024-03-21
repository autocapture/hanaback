package com.aimskr.ac2.hana.backend.channel.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface ImageHashRepository extends JpaRepository<ImageHash, Long> {
    @Query("SELECT a FROM ImageHash a where a.hash = ?1")
    Optional<ImageHash> findByHash(String hash);

    @Query("SELECT a FROM ImageHash a where a.imageDocumentId = ?1")
    Optional<ImageHash> findByFileName(String imageDocumentId);
}
