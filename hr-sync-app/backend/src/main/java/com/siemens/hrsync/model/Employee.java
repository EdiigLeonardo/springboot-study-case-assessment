package com.siemens.hrsync.model;
import jakarta.persistence.*;
import java.time.Instant;
@Entity
public class Employee {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  private String externalId;
  private String name;
  private String email;
  private Instant lastSyncedAt;
  public Long getId() { return id; }
  public String getExternalId() { return externalId; }
  public void setExternalId(String v) { externalId = v; }
  public String getName() { return name; }
  public void setName(String v) { name = v; }
  public String getEmail() { return email; }
  public void setEmail(String v) { email = v; }
  public Instant getLastSyncedAt() { return lastSyncedAt; }
  public void setLastSyncedAt(Instant v) { lastSyncedAt = v; }
}
