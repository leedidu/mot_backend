package com.umc.mot.purchaseMember.controller;


import com.umc.mot.purchaseMember.dto.PurchaseMemberRequestDto;
import com.umc.mot.purchaseMember.dto.PurchaseMemberResponseDto;
import com.umc.mot.purchaseMember.entity.PurchaseMember;
import com.umc.mot.purchaseMember.mapper.PurchaseMemberMapper;
import com.umc.mot.purchaseMember.service.PurchaseMemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/purchaseMember")
@Validated
@AllArgsConstructor
public class PurchaseMemberController {
    private final PurchaseMemberService purchaseMemberService;
    private final PurchaseMemberMapper purchaseMemberMapper;

    // Create
    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody PurchaseMemberRequestDto.Post post) {
        PurchaseMember member = purchaseMemberService.createPurchaseMember(purchaseMemberMapper.purchaseMemberRequestDtoPostToPurchaseMember(post));
        PurchaseMemberResponseDto.Response response = purchaseMemberMapper.purchaseMemberToPurchaseMemberResponseDto(member);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Read
    @GetMapping
    public ResponseEntity getMember(@Positive @RequestParam int memberId) {
        PurchaseMember member = purchaseMemberService.findMember(memberId);
        PurchaseMemberResponseDto.Response response = purchaseMemberMapper.purchaseMemberToPurchaseMemberResponseDto(member);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Update
    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@Positive @PathVariable("member-id") int memberId,
                                      @RequestBody PurchaseMemberRequestDto.Patch patch) {
        patch.setPurchaseMemberId(memberId);
        PurchaseMember member = purchaseMemberService.patchMember(purchaseMemberMapper.purchaseMemberRequestDtoPatchToPurchaseMember(patch));
        PurchaseMemberResponseDto.Response response = purchaseMemberMapper.purchaseMemberToPurchaseMemberResponseDto(member);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // Delete
    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@Positive @PathVariable("member-id") int memberId) {
        purchaseMemberService.deleteMember(memberId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/PToS")
    public ResponseEntity patchSell(@Positive @RequestParam("PMemberId") int id){
        purchaseMemberService.PurchaseMemberTosellMember(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
