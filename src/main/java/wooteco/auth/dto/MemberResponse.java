package wooteco.auth.dto;

import wooteco.auth.domain.Member;

public class MemberResponse {
    private Long id;
    private String email;
    private Integer age;

    public MemberResponse() {
    }

    public MemberResponse(Long id, String email, Integer age) {
        this.id = id;
        this.email = email;
        this.age = age;
    }

    public static MemberResponse of(Member member) {
        return new MemberResponse(member.getId(), member.getEmail(), member.getAge());
    }

    public static MemberResponse from(LoginMember loginMember) {
        return new MemberResponse(loginMember.getId(), loginMember.getEmail(), loginMember.getAge());
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Integer getAge() {
        return age;
    }
}
