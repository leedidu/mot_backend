package com.umc.mot.comment.mapper;



import com.umc.mot.comment.dto.CommentRequestDto;
import com.umc.mot.comment.dto.CommentResponseDto;
import com.umc.mot.comment.entity.Comment;
import com.umc.mot.room.entity.Room;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentResponseDto.Response CommentToCommentResponseDto(Comment comment);

    default List<CommentResponseDto.ListResponse> commentToCommentResponseDtoList(List<Comment> commentList,List<String> HotelName,List<String> roomName,List<String> PackageName){
        List<CommentResponseDto.ListResponse> list = new ArrayList<>();

        for(int i=0;i<commentList.size();i++){
            CommentResponseDto.ListResponse listResponse = new CommentResponseDto.ListResponse();
            listResponse.setId(commentList.get(i).getId());
            listResponse.setContext(commentList.get(i).getContext());
            listResponse.setStar(commentList.get(i).getStar());
            listResponse.setHotelName(HotelName.get(i));
            listResponse.setPhotos(commentList.get(i).getPhotos());
            listResponse.setPackageName(PackageName.get(i));
            listResponse.setRoomName(roomName.get(i));
            listResponse.setModifiedAt(commentList.get(i).getModifiedAt());
            list.add(listResponse);
        }
        return list;

    }
    Comment CommentRequestDtoPostToComment(CommentRequestDto.Post post);
    Comment CommentRequestDtoPatchToComment(CommentRequestDto.Patch patch);



}
