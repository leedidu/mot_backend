package com.umc.mot.comment.mapper;



import com.umc.mot.comment.dto.CommentRequestDto;
import com.umc.mot.comment.dto.CommentResponseDto;
import com.umc.mot.comment.entity.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentResponseDto.Response CommentToCommentResponseDto(Comment comment);
    Comment CommentRequestDtoPostToComment(CommentRequestDto.Post post);
    Comment CommentRequestDtoPatchToComment(CommentRequestDto.Patch patch);
}
