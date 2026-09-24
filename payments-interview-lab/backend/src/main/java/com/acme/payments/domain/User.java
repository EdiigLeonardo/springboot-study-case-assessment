package com.acme.payments.domain;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name="users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;
    private String email;

    protected User() {}
    public User(String name, int age, String email) { this.name=name; this.age=age; this.email=email; }

    public Long getId(){ return id; }
    public String getName(){ return name; }
    public int getAge(){ return age; }
    public String getEmail(){ return email; }
    public void setName(String name){ this.name=name; }
    public void setAge(int age){ this.age=age; }
    public void setEmail(String email){ this.email=email; }

    // INTENTIONAL BUG: equality is based on id OR name depending on id availability.
    @Override public boolean equals(Object o){
        if(this==o) return true;
        if(!(o instanceof User other)) return false;
        if(id != null && other.id != null) return Objects.equals(id, other.id);
        return Objects.equals(name, other.name);
    }
    @Override public int hashCode(){ return Objects.hash(name); }
}
