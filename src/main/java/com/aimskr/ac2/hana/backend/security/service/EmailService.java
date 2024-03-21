package com.aimskr.ac2.hana.backend.security.service;

import com.aimskr.ac2.common.config.AutocaptureConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
@ComponentScan("com.aimskr.ac2.common")
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    private final AutocaptureConfig autocaptureConfig;
    public static final String ePw = createKey();

    private MimeMessage createMessage(String account, String mailAddress)throws Exception{
        System.out.println("보내는 대상 : "+ mailAddress);
        System.out.println("인증 번호 : "+ePw);
        MimeMessage  message = javaMailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, mailAddress);//보내는 대상
        message.setSubject("Autocapture에서 인증 메일이 도착했습니다.");//제목

        String msgg="";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1> Autocapture에서 발송한 인증 메일입니다.</h1>";
        msgg+= "<br>";
        msgg+= "<p>자동인증이 완료되었습니다.<p>";
        msgg+= "<br>";
        msgg+= "<p>아래 링크를 눌러, 새로운 비밀번호를 입력하세요!<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>비밀번호 변경 안내입니다.</h3>";
        msgg+= "<div>LINK : <strong><a href=\"";
        // 개발계, 운영계 다르게 설정
        msgg+= autocaptureConfig.getFrontUrl() + "/pages/changePass?account=" + account;
        msgg+= "&code=" + ePw;
        msgg+= "\">비밀번호 변경페이지로 이동</a></strong></div><br>";
        msgg+= "<div>";
        msgg+= "CODE : <strong>";
        msgg+= ePw+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("admin@aimslas.com","Autocapture"));//보내는 사람

        return message;
    }

    //	인증코드 만들기
    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }

        return key.toString();
    }

    public void sendSimpleMessage(String account, String mailAddress)throws Exception {
        MimeMessage message = createMessage(account, mailAddress);
        try{//예외처리
            javaMailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }

    }


    private MimeMessage createAssignMessage(String qaOwner, String receiptNo, String mailAddress)throws Exception{
        MimeMessage  message = javaMailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, mailAddress);//보내는 대상
        message.setSubject(qaOwner + ": Carrot 배정알림");//제목

        String msgg="";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1> Autocapture 배정이 있습니다.</h1>";
        msgg+= "<h1> 담당자:" + qaOwner + " </h1>";
        msgg+= "<h1> 접수번호:" + receiptNo +  "</h1>";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("admin@aimslas.com","Autocapture"));//보내는 사람

        return message;
    }

    private MimeMessage createErrorMessage(String receiptNo, List<String> mailAddress, boolean isProd)throws Exception{
        MimeMessage  message = javaMailSender.createMimeMessage();
        String stage = "운영계";
        if (!isProd){
            stage = "개발계";
        }

        message.addRecipients(RecipientType.TO, String.join(",", mailAddress));//보내는 대상
        message.setSubject(stage + ": Carrot 오류알림");//제목

        String msgg="";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1> Autocapture 오류가 발생했습니다.</h1>";
        msgg+= "<h1> 접수번호:" + receiptNo +  "</h1>";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("admin@aimslas.com","Autocapture"));//보내는 사람

        return message;
    }

    public void sendAssignAlert(String qaOwner, String receiptNo, String mailAddress)throws Exception {
        MimeMessage message = createAssignMessage(qaOwner, receiptNo, mailAddress);
        try{//예외처리
            javaMailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    public void sendErrorAlert(String receiptNo, List<String> mailAddress, boolean isProd)throws Exception {


        MimeMessage message = createErrorMessage(receiptNo, mailAddress, isProd);
        try{//예외처리
            javaMailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

}
