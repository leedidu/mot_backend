package com.umc.mot.sellMember.controller;


import com.umc.mot.sellMember.dto.SellMemberRequestDto;
import com.umc.mot.sellMember.dto.SellMemberResponseDto;
import com.umc.mot.sellMember.entity.SellMember;
import com.umc.mot.sellMember.mapper.SellMemberMapper;
import com.umc.mot.sellMember.service.SellMemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/sellMember")
@Validated
@AllArgsConstructor
public class SellMemberController {
    private final SellMemberService sellMemberService;
    private final SellMemberMapper sellMemberMapper;

    // Create
    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody SellMemberRequestDto.Post post) {
        SellMember member = sellMemberService.createSellMember(sellMemberMapper.SellMemberRequestDtoPostToSellMember(post));
        SellMemberResponseDto.Response response = sellMemberMapper.SellMemberToSellMemberDtoResponse(member);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Read
    @GetMapping
    public ResponseEntity getMember(@Positive @RequestParam int memberId) {
        SellMember member = sellMemberService.findMember(memberId);
        SellMemberResponseDto.Response response = sellMemberMapper.SellMemberToSellMemberDtoResponse(member);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Update
    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@Positive @PathVariable("member-id") int memberId,
                                      @RequestBody SellMemberRequestDto.Patch patch) {
        patch.setSellMemberId(memberId);
        SellMember member = sellMemberService.patchMember(sellMemberMapper.SellMemberRequestDtoPatchToSellMember(patch));
        SellMemberResponseDto.Response response = sellMemberMapper.SellMemberToSellMemberDtoResponse(member);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // Delete
    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@Positive @PathVariable("member-id") int memberId) {
        sellMemberService.deleteMember(memberId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
