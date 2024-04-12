package com.example.apiWithDb.controller;


import com.example.apiWithDb.model.StuffMember;
import com.example.apiWithDb.response.ResponseHandler;
import com.example.apiWithDb.service.StuffMemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stuffMember")
public class stuffMemberController {

    public StuffMemberService stuffMemberService;

    public stuffMemberController(StuffMemberService stuffMemberService) {
        this.stuffMemberService = stuffMemberService;
    }

    @GetMapping()
    public ResponseEntity<Object> getStuffMemberDetails()
    {
        return ResponseHandler.responseBuilder("Запрошенные данные предоставлены", HttpStatus.OK, stuffMemberService.getAllStuffMembers());
    }

    @GetMapping("{stuffMemberId}")
    public ResponseEntity<Object> getStuffMemberDetails(@PathVariable("stuffMemberId") String stuffMemberId)
    {
        return ResponseHandler.responseBuilder("Запрошенные данные предоставлены", HttpStatus.OK, stuffMemberService.getStuffMember(stuffMemberId));
    }

    @PostMapping
    public String CreateStuffMemberDetails(@RequestBody StuffMember stuffmember)
    {
        stuffMemberService.createStuffMember(stuffmember);
        return "StuffMember Created!";
    }

    @PutMapping
    public String UpdateStuffMemberDetails(@RequestBody StuffMember stuffmember)
    {
        stuffMemberService.updateStuffMember(stuffmember);
        return "StuffMember Updated!";
    }

    @DeleteMapping("{stuffMemberId}")
    public String deleteStuffMember(@PathVariable("stuffMemberId") String stuffMemberId)
    {
        stuffMemberService.deleteStuffMember(stuffMemberId);
        return "StuffMember Deleted!";
    }


}
