package com.umc.mot.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.util.List;

public class CommentRequestDto {

    @Getter
    @AllArgsConstructor
    public static class Post {
        private int id;
        private String context;
        private String imageUrl;
        private int star;
        private int memberId;
        private boolean visible; // true : 보임, false : 안보임
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch {
        private int id;
        private String context;
        private String imageUrl;
        private int star;
        private int memberId;
        private boolean visible; // true : 보임, false : 안보임
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class PatchImage {
        private int commentId;
        private List<MultipartFile> images;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class PatchVisible {
        private int commentId;
        private boolean visible;
    }
}
