package wooteco.auth.service;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import wooteco.auth.dto.LoginMember;
import wooteco.auth.dto.MemberResponse;
import wooteco.auth.infrastructure.JwtTokenProvider;

@Service
public class AuthService {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(MemberService memberService, JwtTokenProvider jwtTokenProvider) {
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String issueToken(String email, String password) {
        MemberResponse memberResponse = memberService.logIn(email, password);
        return jwtTokenProvider.createToken(memberResponse.getId(), memberResponse.getEmail(), memberResponse.getAge());
    }

    public LoginMember parseLoginMember(String token) {
        Claims claims = jwtTokenProvider.getPayload(token);
        return new LoginMember(claims.get("id", Long.class),
                claims.get("email", String.class));
    }

    public void validate(String token) {
        jwtTokenProvider.validateToken(token);
    }
}
