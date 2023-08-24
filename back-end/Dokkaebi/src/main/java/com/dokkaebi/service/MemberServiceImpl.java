package com.dokkaebi.service;

import static java.util.stream.Collectors.toList;

import com.dokkaebi.domain.Device;
import com.dokkaebi.domain.MemberResponseDTO;
import com.dokkaebi.mqtt.MqttService;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import javax.swing.text.html.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokkaebi.domain.Member;
import com.dokkaebi.repository.MemberRepository;

@Service
@Transactional
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberRepository mr;

	@Autowired
	private MqttService mqttService;

	// 회원 가입
	@Override
	public boolean registMember(Member member) {
		// check email & password regex
		if (!regexChecker(member.getEmail(),
				"^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
			return false;
		}
		if (!regexChecker(member.getPassword(),
				"^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,20}$")) {
			return false;
		}

		if(mr.findByEmail(member.getEmail())==null) {
			member.setIconNumber(0);
			try {
				member.setPassword(EncryptionService.encrypt(member.getPassword()));
			} catch (Exception e) {
				System.out.println("NO SUCH ALGORITHM" + e);
			}
			mr.save(member); // repository에 없어도 JpaRepository을 상속 받아 자동으로 저장됨.
			return true;
		}
		else {
			return false;
		}
	}
	
	// 회원 탈퇴
	@Override
	public boolean deleteMember(String userEmail) {
		mr.delete(mr.findByEmail(userEmail));
		return true;
	}

	// 회원 수정
	@Override
	public boolean updateMember(Member member) {
		// 영속성 컨텍스트에 저장 후, 수정 -> 자동으로 update 쿼리가 나감
		Member findMember=mr.findByEmail(member.getEmail());
		if(findMember!=null) {
			findMember.setNickname(member.getNickname());
			return true;
		}
		else {
			return false;
		}
	}

	// 로그인
	@Override
	public Member login(Member member) {
		try {
			return mr.findByEmailAndPassword(member.getEmail(), EncryptionService.encrypt(member.getPassword()));
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	// RefreshToken 저장
	@Override
	public void saveRefreshToken(Long memberId, String refreshToken) {
		Optional<Member> optionalMember = mr.findById(memberId);
		optionalMember.ifPresent(member -> member.setRefreshToken(refreshToken));
	}

	// RefreshToken 반환
	@Transactional(readOnly=true)
	@Override
	public String getRefreshToken(Long id) {
		return mr.findByRefreshToken(id);
	}

	// RefreshToken 삭제
	@Override
	public void deleteRefreshToken(Long userId) {
		Optional<Member> findMember=mr.findById(userId);
	    if (findMember.isPresent()) {
	        Member member = findMember.get();
	        member.setRefreshToken(null); // refreshToken을 null로 설정하여 삭제합니다.
	    } else {
	        // 해당 userId에 해당하는 Member가 존재하지 않는 경우 처리할 로직 추가
	    }
	}

	// 유저 정보 반환
	@Transactional(readOnly=true)
	@Override
	public MemberResponseDTO userInfo(Long memberId) {
		Optional<Member> optionalMember = mr.findById(memberId);
		return new MemberResponseDTO(optionalMember.get());
	}

	// email로 검색
	@Transactional(readOnly = true)
	public List<MemberResponseDTO> findLikeEmail(String email, Long memberId) {
		return mr.findLikeEmail(email + "%").stream()
				.filter(member -> !member.getId().equals(memberId))
				.map(member-> new MemberResponseDTO(member))
				.collect(toList());
	}

	private boolean regexChecker(String str, String regexPattern) {
		return Pattern.compile(regexPattern).matcher(str).matches();
	}

	public boolean updateNickname(Long memberId, String changeTo) {
		Member member = mr.findById(memberId).get();
		member.setNickname(changeTo);
		return true;
	}
	public boolean updatePassword(Long memberId, String changeTo, String pwBefore) {
		Member member = mr.findById(memberId).get();
		try {
			if (member.getPassword().equals(EncryptionService.encrypt(pwBefore))
					&& regexChecker(pwBefore,
					"^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,20}$")) {
				member.setPassword(EncryptionService.encrypt(changeTo));
				return true;
			}
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e);
		}
		return false;
	}
}
