package com.example.demo.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    Long classroomid;
    String headshot;
    String name;
    @Lob
    @Column(columnDefinition = "BLOB", nullable = true)
    byte[] publicKey;
    @Lob
    @Column(columnDefinition = "BLOB", nullable = true)
    byte[] privateKey;
    Date lastLogin;

    public Student() {
    }

    public Student(Long classroomid, String headshot, String name, byte[] publicKey, byte[] privateKey, Date lastLogin) {
        this.classroomid = classroomid;
        this.headshot = headshot;
        this.name = name;
        this.lastLogin = lastLogin;
    }

    public Student(Long classroomid, String headshot, String name) {
        this.classroomid = classroomid;
        this.headshot = headshot;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeadshot() {
        return headshot;
    }

    public void setHeadshot(String headshot) {
        this.headshot = headshot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Long getClassroomid() {
        return classroomid;
    }

    public void setClassroomid(Long classroomid) {
        this.classroomid = classroomid;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }

    public byte[] getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(byte[] privateKey) {
        this.privateKey = privateKey;
    }
}
