package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Emessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    Long fromStudent;
    Long toStudent;
    String subject;
    String body;
    String signature;
    boolean isEncrypted;
    boolean isSigned;
    Date createDate;

    public Emessage() {
    }

    public Emessage(Long fromStudent, Long toStudent, String subject, String body, String signature, Date createDate) {
        this.fromStudent = fromStudent;
        this.toStudent = toStudent;
        this.subject = subject;
        this.body = body;
        this.signature = signature;
        this.createDate = createDate;
    }

    public Emessage(Long fromStudent, Long toStudent, String subject, String body, String signature, boolean isEncrypted, boolean isSigned, Date createDate) {
        this.fromStudent = fromStudent;
        this.toStudent = toStudent;
        this.subject = subject;
        this.body = body;
        this.signature = signature;
        this.isEncrypted = isEncrypted;
        this.isSigned = isSigned;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFromStudent() {
        return fromStudent;
    }

    public void setFromStudent(Long fromStudent) {
        this.fromStudent = fromStudent;
    }

    public Long getToStudent() {
        return toStudent;
    }

    public void setToStudent(Long toStudent) {
        this.toStudent = toStudent;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public boolean isEncrypted() {
        return isEncrypted;
    }

    public void setEncrypted(boolean encrypted) {
        isEncrypted = encrypted;
    }

    public boolean isSigned() {
        return isSigned;
    }

    public void setSigned(boolean signed) {
        isSigned = signed;
    }
}
