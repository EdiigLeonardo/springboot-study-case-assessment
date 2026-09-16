package com.siemens.portal.model;
import jakarta.persistence.*;
@Entity
public class User {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  private String email;
  private String password;
  @Enumerated(EnumType.STRING) private Role role;
  public Long getId() { return id; }
  public String getEmail() { return email; }
  public void setEmail(String v) { email = v; }
  public String getPassword() { return password; }
  public void setPassword(String v) { password = v; }
  public Role getRole() { return role; }
  public void setRole(Role v) { role = v; }
}
