package com.coders.laundry.service;

import com.coders.laundry.domain.entity.MemberEntity;
import com.coders.laundry.dto.LoginResponse;
import com.coders.laundry.repository.MemberRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginService {
    private MemberRepository memberRepository;
    private TokenService tokenService;

    @Autowired
    LoginService(MemberRepository memberRepository, TokenService tokenService){
        this.memberRepository = memberRepository;
        this.tokenService = tokenService;
    }

    //회원존재여부확인
    public boolean existMember(String phoneNum){
        MemberEntity loginMember = memberRepository.selectByPhoneNumber(phoneNum);
        if (loginMember == null)
            return false;
        else
            return true;
    }
    //비밀번호 일치여부 확인
    public boolean matchPW(String phoneNum, String pw){
        MemberEntity loginMember = memberRepository.selectByPhoneNumber(phoneNum);
        //사용자가 입력한 비밀번호와 DB에 저장된 비밀번호 비교
        return BCrypt.checkpw(pw, loginMember.getPassword());
    }

    //로그인성공 시, responsebody에 들어갈 값 반환(회원정보, 토큰발급)
    public LoginResponse getLoginResponse(String phoneNum){
        //로그인한 회원객체 정보 가져오기
        MemberEntity member = memberRepository.selectByPhoneNumber(phoneNum);
        //토큰 발급
        String token = tokenService.createToken(member.getMemberId());
        LoginResponse loginResponse = LoginResponse.builder()
                .memberEntity(member)
                .token(token)
                .build();
        return loginResponse;
    }
}
