package com.umc.mot.comment.service;

import com.umc.mot.comment.repository.CommentRepository;
import com.umc.mot.exception.BusinessLogicException;
import com.umc.mot.exception.ExceptionCode;
import com.umc.mot.comment.entity.Comment;
import com.umc.mot.heart.entity.Heart;
import com.umc.mot.hotel.entity.Hotel;
import com.umc.mot.packagee.service.PackageService;
import com.umc.mot.purchaseMember.entity.PurchaseMember;
import com.umc.mot.reserve.entity.Reserve;
import com.umc.mot.reserve.repository.ReserveRepository;
import com.umc.mot.reserve.service.ReserveService;
import com.umc.mot.room.entity.Room;
import com.umc.mot.room.service.RoomService;
import com.umc.mot.token.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {
    
    private final CommentRepository commentRepository;
    private final TokenService tokenService;
    private final ReserveService reserveService;

    //후기작성
    public Comment createComment(Comment comment,int reserveId){
        PurchaseMember purchaseMember = tokenService.getLoginPurchaseMember();
        Reserve reserve = reserveService.findReserve(reserveId);
        if(LocalDate.now().isAfter(reserve.getCheckOut())){
            comment.setHotel(reserve.getHotel());
            comment.setPurchaseMember(purchaseMember);
            return commentRepository.save(comment);
        }
        else{
            throw new IllegalArgumentException("체크아웃날짜 이후에 후기를 작성할 수 있습니다.");
        }

    }
    // Read
    public Comment findComment(int commentId) {
        Comment comment = verifiedComment(commentId);
        return comment;
    }


    // Update
    public Comment patchComment(Comment comment) {
        Comment findComment = verifiedComment(comment.getId());
        Optional.ofNullable(comment.getId()).ifPresent(findComment::setId);
        Optional.ofNullable(comment.getContext()).ifPresent(findComment::setContext);
        Optional.ofNullable(comment.getImageUrl()).ifPresent(findComment::setImageUrl);
        Optional.ofNullable(comment.getStar()).ifPresent(findComment::setStar);
        Optional.ofNullable(comment.getMemberId()).ifPresent(findComment::setMemberId);
        Optional.ofNullable(comment.isVisible()).ifPresent(findComment::setVisible);



        return commentRepository.save(findComment);
    }

    // Delete
    public void deleteComment(int commentId) {
        Comment comment = verifiedComment(commentId);
        commentRepository.delete(comment);
    }

    // 멤버 검증
    public Comment verifiedComment(int commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        return comment.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));

    }
}
