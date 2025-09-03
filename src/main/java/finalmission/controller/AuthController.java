package finalmission.controller;

import finalmission.dto.request.LoginRequest;
import finalmission.entity.Member;
import finalmission.service.AuthService;
import finalmission.service.MemberService;
import finalmission.util.CookieUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증 API", description = "로그인 및 토큰 발급 API")
@RestController
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;

    public AuthController(AuthService authService, MemberService memberService) {
        this.authService = authService;
        this.memberService = memberService;
    }

    @Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인하여 쿠키에 JWT 토큰을 저장합니다.")
    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @RequestBody LoginRequest request,
            HttpServletResponse response) {
        Member member = memberService.findByEmailAndPassword(request);
        String accessToken = authService.createTokenByMember(member);

        CookieUtil.addCookie("token", accessToken, response);

        return ResponseEntity.ok().build();
    }
}