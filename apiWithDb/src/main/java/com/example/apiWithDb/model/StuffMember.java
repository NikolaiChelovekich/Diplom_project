package com.example.apiWithDb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="stuffMemberInfo")
public class StuffMember
{
    @Id
    private String stuffMemberId;
    private String stuffMemberName;
    private String stuffMemberSecName;
    private String stuffMemberPhoto;

    public StuffMember() {
    }

    public StuffMember(String stuffMemberPhoto,
                       String stuffMemberSecName,
                       String stuffMemberName,
                       String stuffMemberId) {
            this.stuffMemberPhoto = stuffMemberPhoto;
            this.stuffMemberSecName = stuffMemberSecName;
            this.stuffMemberName = stuffMemberName;
            this.stuffMemberId = stuffMemberId;
    }

    public String getStuffMemberId() {
        return stuffMemberId;
    }

    public void setStuffMemberId(String stuffMemberId) {
        this.stuffMemberId = stuffMemberId;
    }

    public String getStuffMemberPhoto() {
        return stuffMemberPhoto;
    }

    public void setStuffMemberPhoto(String stuffMemberPhoto) {
        this.stuffMemberPhoto = stuffMemberPhoto;
    }

    public String getStuffMemberSecName() {
        return stuffMemberSecName;
    }

    public void setStuffMemberSecName(String stuffMemberSecName) {
        this.stuffMemberSecName = stuffMemberSecName;
    }

    public String getStuffMemberName() {
        return stuffMemberName;
    }

    public void setStuffMemberName(String stuffMemberName) {
        this.stuffMemberName = stuffMemberName;
    }
}
