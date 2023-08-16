package com.umc.mot.comment.mapper;



import com.umc.mot.comment.dto.CommentRequestDto;
import com.umc.mot.comment.dto.CommentResponseDto;
import com.umc.mot.comment.entity.Comment;
import com.umc.mot.hotel.entity.Hotel;
import com.umc.mot.reserve.dto.ReserveResponseDto;
import com.umc.mot.reserve.entity.Reserve;
import com.umc.mot.room.entity.Room;
import com.umc.mot.room.service.RoomService;
import lombok.AllArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentResponseDto.Response CommentToCommentResponseDto(Comment comment);

    List<CommentResponseDto.Response> commentToCommentResponseDtoList(List<Comment> commentList);
    Comment CommentRequestDtoPostToComment(CommentRequestDto.Post post);
    Comment CommentRequestDtoPatchToComment(CommentRequestDto.Patch patch);



}
