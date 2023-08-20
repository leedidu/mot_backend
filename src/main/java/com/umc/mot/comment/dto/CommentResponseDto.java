package com.umc.mot.comment.dto;

import com.umc.mot.hotel.entity.Hotel;
import com.umc.mot.room.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentResponseDto {

    @Getter
    @AllArgsConstructor
    public static class Response {
        private int id;
        private String context;
        private double star;
        private List<String> photos;
        private boolean visible;// true : 보임, false : 안보임

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ListResponse {
        private int id;
        private double hotelStar;
        private String context;
        private double star;
        private List<String> photos;
        private boolean visible;// true : 보임, false : 안보임
        private String HotelName;
        private String RoomName;
        private LocalDateTime modifiedAt;
        private String PackageName;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseSellMember {
        private double hotelStar;
        private List<Comments> comments = new ArrayList<>();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Comments {
        private int commentId; // commentId
        private String name; // comment 작성한 작성자 이름
        private String context;
        private double star;
        private List<String> photos;
        private boolean visible;// true : 보임, false : 안보임
        private String RoomName = "";
        private String PackageName = "";
        private LocalDateTime modifiedAt;
        private List<Messages> messages = new ArrayList<>();
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Messages {
        private int mesageId; // mesageId
        private String content; // 답글
    }
}