package com.umc.mot.comment.mapper;



import com.umc.mot.comment.dto.CommentRequestDto;
import com.umc.mot.comment.dto.CommentResponseDto;
import com.umc.mot.comment.entity.Comment;
import com.umc.mot.reserve.dto.ReserveResponseDto;
import com.umc.mot.reserve.entity.Reserve;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentResponseDto.Response CommentToCommentResponseDto(Comment comment,int PURCHASE_MEMBER_ID,int HOTEL_ID);
    Comment CommentRequestDtoPostToComment(CommentRequestDto.Post post);
    Comment CommentRequestDtoPatchToComment(CommentRequestDto.Patch patch);



}
