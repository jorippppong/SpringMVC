package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

    //@GetMapping("/")
    public String home() {
        return "home";
    }

    //@GetMapping("/")
    public String homeLogin(
            @CookieValue(name = "memberId", required = false) Long memberId,
            Model model
    ){
        if(memberId == null){
            return "home";
        }

        //쿠키가 있는 사용자
        Member loginMember = memberRepository.findById(memberId);
        // 없는 member
        if(loginMember == null){
            return "home";
        }

        // 성공 로직 (member 정보 담아서 보내기)
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    //@GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model){
        //세션 관리자에 저장된 세션 정보 조회
        Member member = (Member)sessionManager.getSession(request);
        if(member == null){
            return "home";
        }

        //로그인
        model.addAttribute(member);
        return "loginHome";
    }

    //@GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model){
        //세션이 없으면 홈으로~
        HttpSession session = request.getSession(false);
        if(session == null){
            return "home";
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        // 세션에 회원 데이터가 없으면 홈으로~
        if(loginMember == null){
            return "home";
        }

        // 세션이 유지되면 로그인홈으로
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    @GetMapping("/")
    public String homeLoginV3Spring(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
            Model model)
    {
        // 세션에 회원 데이터가 없으면 홈으로~
        if(loginMember == null){
            return "home";
        }

        // 세션이 유지되면 로그인홈으로
        model.addAttribute("member", loginMember);
        return "loginHome";
    }


}