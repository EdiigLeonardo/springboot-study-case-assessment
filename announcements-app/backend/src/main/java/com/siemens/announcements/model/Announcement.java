package com.siemens.announcements.model;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.Instant;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Announcement {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  private String title;

  @Column(name = "body") private String body; // BUG: a coluna foi renomeada para "content" na V3 - isto deixa de bater certo.

  @CreatedDate private Instant createdAt;
  @CreatedBy private String createdBy;
  @LastModifiedDate private Instant updatedAt;
  @LastModifiedBy private String updatedBy;

  public Long getId() { return id; }
  public String getTitle() { return title; }
  public void setTitle(String v) { title = v; }
  public String getBody() { return body; }
  public void setBody(String v) { body = v; }
  public Instant getCreatedAt() { return createdAt; }
  public String getCreatedBy() { return createdBy; }
}
