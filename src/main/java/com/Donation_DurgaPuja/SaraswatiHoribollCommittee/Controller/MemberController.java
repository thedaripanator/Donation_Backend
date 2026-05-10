package com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Controller;

import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Model.Member;
import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Service.CloudinaryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.Donation_DurgaPuja.SaraswatiHoribollCommittee.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MemberController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private MemberRepository memberRepository;

    @PostMapping("/upload-member")
    public Member uploadMember(
            @RequestParam("file") MultipartFile file,
            @RequestParam String name,
            @RequestParam String position) {

        System.out.println("UPLOAD HIT");

        String imageUrl = cloudinaryService.uploadFile(file);
        System.out.println("IMAGE URL = " + imageUrl);

        Member member = new Member();
        member.setName(name);
        member.setPosition(position);
        member.setImageUrl(imageUrl);

        Member saved = memberRepository.save(member);

        System.out.println("SAVED ID = " + saved.getId());

        return saved;
    }
    @GetMapping("/members")
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @DeleteMapping("/delete_members/{id}")
    public void deletemember(@PathVariable Long id) {
        memberRepository.deleteById(id);
    }
}