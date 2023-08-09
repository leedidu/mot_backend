package com.umc.mot.utils;

import com.umc.mot.exception.BusinessLogicException;
import com.umc.mot.exception.ExceptionCode;
import com.umc.mot.token.entity.CertificationPhone;
import com.umc.mot.token.repository.CertificationPhoneRepository;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Balance;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import net.nurigo.sdk.message.model.Message;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.Random;

@Configuration
public class SendMessage {
    private final String apiKey;

    private final String apiSecret;

    private final String sendPhoneNumber; // 발신번호

    private final DefaultMessageService messageService;

    private final CertificationPhoneRepository certificationPhoneRepository;

    public SendMessage(CertificationPhoneRepository certificationPhoneRepository) {
        this.apiKey = System.getenv("CERTIFICATION_NUMBER_ID");
        this.apiSecret = System.getenv("CERTIFICATION_NUMBER_PW");
        this.sendPhoneNumber = System.getenv("PHONE_NUMBER");
        this.messageService = NurigoApp.INSTANCE.initialize(this.apiKey, this.apiSecret, "https://api.coolsms.co.kr");
        this.certificationPhoneRepository = certificationPhoneRepository;
    }

    // 단일 메세지 전송
    public CertificationPhone sendMessage(String receivePhoneNumber) {
        Message message = new Message();
        int randomNumber = randomNumber();

        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        message.setFrom(sendPhoneNumber); // 발신자
        message.setTo(receivePhoneNumber); // 수신자
        message.setText("[MOT] 인증번호는 " + randomNumber + " 입니다.");

        // 메세지 전송
//        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));

        // DB 저장
        CertificationPhone certificationPhone = new CertificationPhone();
        certificationPhone.setPhoneNumber(receivePhoneNumber);
        certificationPhone.setRandomNumber(randomNumber);
        certificationPhoneRepository.save(certificationPhone);
        return certificationPhone;
    }

    // 인증번호 인증
    public boolean checkCertificationNumber(CertificationPhone certificationPhone) {
        Optional<CertificationPhone> optionalCertificationPhone = certificationPhoneRepository.findByPhoneNumber(certificationPhone.getPhoneNumber());
        CertificationPhone findCertificationPhone = optionalCertificationPhone.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.CERTIFICATION_NUMBER)
        );

        boolean check = true;
        if(findCertificationPhone.getRandomNumber() != certificationPhone.getRandomNumber()) check = false; // 인증번호 불일치

        return check;
    }

    // 랜덤번호 생성하기
    public int randomNumber() {
        Random random = new Random();
        int num = 0;
        num += (random.nextInt(8)+1) * 1000;
        num += random.nextInt(9) * 100;
        num += random.nextInt(9) * 10;
        num += random.nextInt(9);

        return num; // 1000 ~ 9999
    }

    // 잔고 확인
    public Balance getBalance() {
        Balance balance = this.messageService.getBalance();
        return balance;
    }
}
