package com.example.apiWithDb.service.impl;

import com.example.apiWithDb.model.StuffMember;
import com.example.apiWithDb.repository.stuffMemberRepository;
import com.example.apiWithDb.service.StuffMemberService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class stuffmemberServiceimpl implements StuffMemberService {

    stuffMemberRepository stuffmemberRepository;

    public stuffmemberServiceimpl(stuffMemberRepository stuffmemberRepository) {
        this.stuffmemberRepository = stuffmemberRepository;
    }

    @Override
    public String createStuffMember(StuffMember stuffmember) {
        stuffmemberRepository.save(stuffmember);
        return "Success";
    }

    @Override
    public String updateStuffMember(StuffMember stuffmember) {
        stuffmemberRepository.save(stuffmember);
        return "Success";
    }

    @Override
    public String deleteStuffMember(String stuffmemberId) {
        stuffmemberRepository.deleteById(stuffmemberId);
        return "Success";
    }

    @Override
    public StuffMember getStuffMember(String stuffMemberId) {
        return stuffmemberRepository.findById(stuffMemberId).get();
    }

    @Override
    public List<StuffMember> getAllStuffMembers() {
        return stuffmemberRepository.findAll();
    }
}
