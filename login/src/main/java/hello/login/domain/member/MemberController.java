package hello.login.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberRepository memberRepository;

    @GetMapping("/add")
    public String addForm(@ModelAttribute("member") Member member){
        return "members/addMemberForm";
    }

    @PostMapping("/add")
    public String addForm(@Valid @ModelAttribute("member") Member member, BindingResult result){
        if(result.hasErrors()){
            return "member/addMemberForm";
        }
        memberRepository.save(member);
        return "redirect:/";
    }


}
