package Tasam.apiserver.controller;


import Tasam.apiserver.dto.AddParticipationDto;
import Tasam.apiserver.dto.response.ParticipatedReserveResponseDto;
import Tasam.apiserver.response.DefaultRes;
import Tasam.apiserver.response.StatusCode;
import Tasam.apiserver.service.ParticipationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/participation")
public class ParticipationController {

    private final ParticipationService participationService;


    //참여 하기

    @PostMapping("/add")
    public ResponseEntity addParticipate(@RequestBody AddParticipationDto addParticipationDto,@RequestParam(name = "userUid") String userUid){
        Long participationId = participationService.addParticipation(addParticipationDto, userUid);

        return participationId != null ?
                new ResponseEntity(DefaultRes.res(StatusCode.OK, "경기 참여 완료"), HttpStatus.OK) :
                new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "잘못된 요청"), HttpStatus.OK);
    }


    // 참여 취소
    @DeleteMapping("/delete/{reservationId}")
    public ResponseEntity deleteParticipation(@PathVariable Long reservationId,@RequestParam(name = "userUid") String userUid){

        Long participationId = participationService.deleteParticipation(reservationId, userUid);


        return participationId != null ?

                new ResponseEntity(DefaultRes.res(StatusCode.OK, "경기 참여 취소"), HttpStatus.OK):
                new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "잘못된 요청"), HttpStatus.OK);
    }

    // 참여상태 확인
    @GetMapping("/check/{reservationId}")
    public ResponseEntity checkParticipation(@PathVariable Long reservationId,@RequestParam(name = "userUid") String userUid){



        String status = participationService.checkParticipation(reservationId, userUid);


        switch (status){
            case "1" : return new ResponseEntity(DefaultRes.res(StatusCode.OK, "암구호 보여주기","암구호 보여주기"), HttpStatus.OK);
            case "2" :  return new ResponseEntity(DefaultRes.res(StatusCode.OK, "참여취소","참여취소"), HttpStatus.OK);
            case "3" : return new ResponseEntity(DefaultRes.res(StatusCode.OK, "참여하기","참여하기"), HttpStatus.OK);
            default: return new ResponseEntity(DefaultRes.res(StatusCode.OK, "마감","마감"), HttpStatus.OK);
        }


    }


}
