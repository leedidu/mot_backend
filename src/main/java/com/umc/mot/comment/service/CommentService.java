package com.umc.mot.comment.service;

import com.umc.mot.comment.repository.CommentRepository;
import com.umc.mot.exception.BusinessLogicException;
import com.umc.mot.exception.ExceptionCode;
import com.umc.mot.comment.entity.Comment;
import com.umc.mot.hotel.entity.Hotel;
import com.umc.mot.hotel.repository.HotelRepository;
import com.umc.mot.hotel.service.HotelService;
import com.umc.mot.packagee.service.PackageService;
import com.umc.mot.purchaseMember.entity.PurchaseMember;
import com.umc.mot.reserve.entity.Reserve;
import com.umc.mot.reserve.service.ReserveService;
import com.umc.mot.room.entity.Room;
import com.umc.mot.room.service.RoomService;
import com.umc.mot.roomPackage.service.RoomPackageService;
import com.umc.mot.token.service.TokenService;
import com.umc.mot.utils.S3Uploader;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Log4j2
public class CommentService {
    
    private final CommentRepository commentRepository;
    private final TokenService tokenService;
    private final ReserveService reserveService;
    private final HotelRepository hotelRepository;
    private final HotelService hotelService;
    private final RoomService roomService;
    private final PackageService packageService;
    private final RoomPackageService roomPackageService;
    private S3Uploader s3Uploader;


    //후기작성
    public Comment createComment(Comment comment,int reserveId){
        PurchaseMember purchaseMember = tokenService.getLoginPurchaseMember();
        Reserve reserve = reserveService.findReserve(reserveId);
        if(LocalDate.now().isAfter(reserve.getCheckOut())){
            comment.setHotel(reserve.getHotel());
            comment.setPurchaseMember(purchaseMember);
            comment.setVisible(true);
            comment.setReserve(reserve);
            Hotel hotel  = hotelService.findHotel(comment.getHotel().getId());
            //호텔 comment 개수
            int comm = hotel.getCommentCount();
            comm = comm + 1 ;
            hotel.setCommentCount(comm);
            //호텔 별점
            double star = hotel.getStar();
            double commentStar = comment.getStar();
            int commentCount = hotel.getCommentCount();

            //호텔 별점 가져와서 계산한 후 저장
            double account;
            if (commentCount > 0) {
                account = ((star * (commentCount - 1)) + commentStar) / commentCount;
            } else {
                account = commentStar; // 호텔에 아직 댓글이 없는 경우
            }
            hotel.setStar(account);
            hotelRepository.save(hotel);
            return commentRepository.save(comment);
        }
        else{
            throw new IllegalArgumentException("체크아웃날짜 이후에 후기를 작성할 수 있습니다.");
        }

    }

    // Read
    public List<Comment> findCommentList() {
        PurchaseMember purchaseMember = tokenService.getLoginPurchaseMember();
        int MemberId=purchaseMember.getPurchaseMemberId();
        List<Comment> commentList=commentRepository.findCommentByPurchaseMember(MemberId);
        return commentList;
    }
    //룸찾기
    public List<Room> findRoom(int roomId){
        Room room = roomService.findRoomId(roomId);
        List<Room> room2 = new ArrayList<>();
        room2.add(room);
        return room2;

    }

    //패키지에 해당되는 룸들 가져오기
    public List<Room> findRoomPackage(int packageId){

        List<Room> room = roomPackageService.findRoomPackage(packageId);

        return room;

    }

    //코멘트에 해당되는 룸 가져오기

    public String findHotelName(int hotelId){
        Hotel hotel = hotelService.findHotel(hotelId);
        String name = hotel.getName();
        return name;

    }
    //호텔 평점
    public Double findHotelStar(int hotelId){
        Hotel hotel = hotelService.findHotel(hotelId);
        Double hotelStar = hotel.getStar();
        return hotelStar;
    }




    // Update
    public Comment patchComment(Comment comment) {
        tokenService.getLoginPurchaseMember();
        Comment findComment = verifiedComment(comment.getId());
        double star = findComment.getStar(); //기존값
        Optional.ofNullable(comment.getContext()).ifPresent(findComment::setContext);
        Optional.ofNullable(comment.getStar()).ifPresent(findComment::setStar);
        double newStar  = findComment.getStar(); //새로운값
        Hotel hotel  = hotelService.findHotel(findComment.getHotel().getId());
        double hotelStar = hotel.getStar(); //기존 평균값
        int hotelCount = hotel.getCommentCount();

        double newS = (hotelStar * hotelCount) - star + newStar;
        double newAverage = newS / hotelCount;
        hotel.setStar(newAverage);
        hotelRepository.save(hotel);

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
