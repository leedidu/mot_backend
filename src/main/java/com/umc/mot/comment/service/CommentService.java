package com.umc.mot.comment.service;

import com.umc.mot.comment.repository.CommentRepository;
import com.umc.mot.exception.BusinessLogicException;
import com.umc.mot.exception.ExceptionCode;
import com.umc.mot.comment.entity.Comment;
import com.umc.mot.hotel.entity.Hotel;
import com.umc.mot.packagee.entity.Package;
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
    private final ReserveRepository reserveRepository;
    private final RoomService roomService;
    private final PackageService packageService;


    //작성할 수 있는 예약 리스트 보여주기
    public List<Reserve> reserveList() {
        PurchaseMember purchaseMember = tokenService.getLoginPurchaseMember();
        //구매자아이디로 예약에서 해당하는 reserve값을 다 들고왔다.
        List<Reserve> reserveList = reserveRepository.findReserveByPurchaseMember(purchaseMember.getPurchaseMemberId());
        List<Reserve> reserveList1 = new ArrayList<>();

        List<Room> room = new ArrayList<>();
        List<Package> pa = new ArrayList<>();


        for(int i=0;i<reserveList.size();i++){ //구매자가 예약한 예약들 중에서 checkout을 찾아서 현재시간보다 후인시간에만 list에 넣도록 했다.
            Reserve reserve = reserveList.get(i);
            if(LocalDate.now().isAfter(reserve.getCheckOut())){
                reserveList1.add(reserve);
            }
        }

        if(reserveList1.isEmpty()){ //만약 위의 리스트가 비어있으면 예약안된다
            throw new IllegalArgumentException("후기를 작성할 수 있는 예약이 없습니다. ");
        }
        else{ //리스트가 비어있지 않다면 예약가능한 객실또는 패키지 넘겨주기
            for(int j=0;j<reserveList1.size();j++){ //후기를 작성할 수 있는 예약들을 리스트 형태로 받았다.
                int reserveID = reserveList1.get(j).getId(); //후기를 작성할 수 있는 예약 아이디 가져온다.
                Optional<Integer> roomId = reserveRepository.findRoomByReserveId(reserveID); //예약아이디로 룸을 찾는다
                Optional<Integer> packageId = reserveRepository.findPackageByReserveId(reserveID);//예약아이디로 패키지를 찾는다.
                int convertedPackageId = packageId.get();
                int convertedRoomId = roomId.get();

                if(roomId.isEmpty()){//룸이 비어있다면
                    //패키지에서 다시 찾는다.
                    if(packageId.isEmpty()){ //패키지가 비어있다면
                        throw new IllegalArgumentException("예약한 방과 패키지가 없습니다. ");
                    }else{
                        //룸이 비어있고 패키지가 비어있지 않다면
                        Package aPackage = packageService.findPackage(convertedPackageId);
                        pa.add(aPackage);

                    }

                }else{//룸이 존재하고 패키지도 존재한다면
                    Room room1 = roomService.findRoomId(convertedRoomId);
                    room.add(room1);
                }


            }
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
