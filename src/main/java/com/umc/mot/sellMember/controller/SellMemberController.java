package com.umc.mot.sellMember.controller;


import com.umc.mot.sellMember.dto.SellMemberRequestDto;
import com.umc.mot.sellMember.dto.SellMemberResponseDto;
import com.umc.mot.sellMember.entity.SellMember;
import com.umc.mot.sellMember.mapper.SellMemberMapper;
import com.umc.mot.sellMember.service.SellMemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
    public ResponseEntity getMember(@Positive @RequestParam long memberId) {
        MemberEntity member = memberService.findMember(memberId);
        MemberDto.Response response = memberMapper.memberToMemberDtoResponse(member);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Update
    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@Positive @PathVariable("member-id") long memberId,
                                      @RequestBody MemberDto.Patch patch) {
        patch.setMemberId(memberId);
        MemberEntity member = memberService.patchMember(memberMapper.memberDtoPatchToMember(patch));
        MemberDto.Response response = memberMapper.memberToMemberDtoResponse(member);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{member-id}")
    public ResponseEntity putMember(@Positive @PathVariable("member-id") long memberId,
                                    @Valid @RequestBody MemberDto.Put put) {
        put.setMemberId(memberId);
        MemberEntity member = memberService.putMember(memberMapper.memberDtoPutToMember(put));
        MemberDto.Response response = memberMapper.memberToMemberDtoResponse(member);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@Positive @PathVariable("member-id") long memberId) {
        memberService.deleteMember(memberId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
