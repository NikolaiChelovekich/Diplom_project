package com.example.apiWithDb.service;

import com.example.apiWithDb.model.StuffMember;

import java.util.List;

public interface StuffMemberService {

    public String createStuffMember(StuffMember stuffmember);
    public String updateStuffMember(StuffMember stuffmember);
    public String deleteStuffMember(String stuffmemberId);
    public StuffMember getStuffMember(String stuffMemberId);
    public List<StuffMember> getAllStuffMembers();
}
