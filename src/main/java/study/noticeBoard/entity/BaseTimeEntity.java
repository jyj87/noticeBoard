package study.noticeBoard.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * 공통 사용 컬럼
 */


@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
abstract class BaseTimeEntity {

    @Column(name="created_date", nullable = false)
    @CreatedDate
    private String createdDate;


    @Column(name="modified_date", nullable = false)
    @LastModifiedDate
    private String modifiedDate;

    /* 엔티티를 저장하기 이전에 실행 */
    @PrePersist
    public void onPrePersist(){
        this.createdDate = localDateNow();
        this.modifiedDate = this.createdDate;
    }

    /* 엔티티를 업데이트 하기전에 실행 */
    @PreUpdate
    public void onPreUpdate(){
        this.modifiedDate = localDateNow();
    }

    /* 현재 Date 취득 */
    private String localDateNow(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }

}
