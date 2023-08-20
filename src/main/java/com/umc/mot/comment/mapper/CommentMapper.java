package com.umc.mot.comment.mapper;


import com.umc.mot.comment.dto.CommentRequestDto;
import com.umc.mot.comment.dto.CommentResponseDto;
import com.umc.mot.comment.entity.Comment;
import com.umc.mot.hotel.entity.Hotel;
import com.umc.mot.message.entity.Message;
import com.umc.mot.purchaseMember.entity.PurchaseMember;
import com.umc.mot.room.entity.Room;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentResponseDto.Response CommentToCommentResponseDto(Comment comment);

    default List<CommentResponseDto.ListResponse> commentToCommentResponseDtoList(List<Comment> commentList, List<String> HotelName, List<String> roomName, List<String> PackageName, List<Double> hotelStar) {
        List<CommentResponseDto.ListResponse> list = new ArrayList<>();

        for (int i = 0; i < commentList.size(); i++) {
            CommentResponseDto.ListResponse listResponse = new CommentResponseDto.ListResponse();
            listResponse.setId(commentList.get(i).getId());
            listResponse.setContext(commentList.get(i).getContext());
            listResponse.setStar(commentList.get(i).getStar());
            listResponse.setHotelName(HotelName.get(i));
            listResponse.setPhotos(commentList.get(i).getPhotos());
            listResponse.setVisible(commentList.get(i).isVisible());
            listResponse.setPackageName(PackageName.get(i));
            listResponse.setRoomName(roomName.get(i));
            listResponse.setModifiedAt(commentList.get(i).getModifiedAt());
            listResponse.setHotelStar(hotelStar.get(i));
            list.add(listResponse);

        }
        return list;

    }

    Comment CommentRequestDtoPostToComment(CommentRequestDto.Post post);

    Comment CommentRequestDtoPatchToComment(CommentRequestDto.Patch patch);

    default CommentResponseDto.ResponseSellMember commentsToListResponseSellMember(Double hotelStar,
                                                                                   List<Comment> comments,
                                                                                   List<PurchaseMember> purchaseMembers,
                                                                                   List<String> roomNames,
                                                                                   List<String> packageNames) {
        CommentResponseDto.ResponseSellMember responseSellMember = new CommentResponseDto.ResponseSellMember();
        responseSellMember.setHotelStar(hotelStar);
        List<CommentResponseDto.Comments> ListResponseComments = new ArrayList<>();
        int roomNamesIndex = 0;
        int packageNamesIndex = 0;

        for (int i = 0; i < comments.size(); i++) {
            CommentResponseDto.Comments responseComments = new CommentResponseDto.Comments();
            Comment comment = comments.get(i);
            PurchaseMember purchaseMember = purchaseMembers.get(i);

            responseComments.setCommentId(comment.getId());
            responseComments.setName(purchaseMember.getName());
            responseComments.setContext(comment.getContext());
            responseComments.setStar(comment.getStar());
            responseComments.setPhotos(comment.getPhotos());
            responseComments.setVisible(comment.isVisible());
            if (comment.getReserve().getPackagesId().isEmpty())
                responseComments.setRoomName(roomNames.get(roomNamesIndex++));
            else responseComments.setPackageName(packageNames.get(packageNamesIndex++));
            responseComments.setModifiedAt(comment.getModifiedAt());
            comment.getMessages().stream().forEach(message -> {
                CommentResponseDto.Messages messages = new CommentResponseDto.Messages();
                messages.setMesageId(message.getId());
                messages.setContent(message.getContent());
                responseComments.getMessages().add(messages);
            });

            ListResponseComments.add(responseComments);
        }

        responseSellMember.setComments(ListResponseComments);

        return responseSellMember;
    }

}
