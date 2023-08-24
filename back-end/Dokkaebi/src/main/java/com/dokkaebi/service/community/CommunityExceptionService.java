package com.dokkaebi.service.community;

import com.dokkaebi.exception.NotAuthorizedException;
import com.dokkaebi.repository.MemberRepository;
import com.dokkaebi.repository.community.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommunityExceptionService {

  private final MemberRepository memberRepository;

  public void isAuthorized(
      CommunityRepository communityRepository,
      Long entityId, Long memberId) {

    if (!memberId.equals(communityRepository.findMemberId(entityId))) {
      if (memberRepository.findById(memberId).isPresent()
          && !memberRepository.findById(memberId).get().isAdmin()) {
        throw new NotAuthorizedException();
      }
    }
  }
}
