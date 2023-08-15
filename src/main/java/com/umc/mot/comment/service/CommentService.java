package com.umc.mot.comment.service;

import com.umc.mot.comment.repository.CommentRepository;
import com.umc.mot.exception.BusinessLogicException;
import com.umc.mot.exception.ExceptionCode;
import com.umc.mot.comment.entity.Comment;
import com.umc.mot.reserve.service.ReserveService;
import com.umc.mot.token.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {
    
    private final CommentRepository commentRepository;
    private final TokenService tokenService;
    private final ReserveService reserveService;
    //Create
    public Comment createComment(Comment comment) {
        tokenService.getLoginPurchaseMember();



        return commentRepository.save(comment);
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

    // 사진 업로드
    public Comment uploadRoomImage(int commentId, List<MultipartFile> multipartFiles) {
        Comment comment = verifiedComment(commentId);

        // 이미지 파일 이름만 추출
        List<String> saveImages = s3Uploader.autoImagesUploadAndDelete(comment.getPhotos(), multipartFiles);

        comment.setPhotos(saveImages);
        return commentRepository.save(comment);
    }
}
