package com.aimskr.ac2.kakao.backend.common.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@Entity
@RevisionEntity
public class CustomRevisionEntity implements Serializable {
    @Id
    @GeneratedValue
    @RevisionNumber
    private long rev;
    @RevisionTimestamp
    private long timestamp;
}
