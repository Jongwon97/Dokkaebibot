package com.dokkaebi.service.community;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokkaebi.domain.Member;
import com.dokkaebi.domain.community.Invite;
import com.dokkaebi.domain.community.InviteRequestDTO;
import com.dokkaebi.domain.community.InviteResponseDTO;
import com.dokkaebi.domain.studyroom.StudyRoom;
import com.dokkaebi.exception.NotRegisteredException;
import com.dokkaebi.repository.MemberRepository;
import com.dokkaebi.repository.StudyRoom.StudyRoomRepository;
import com.dokkaebi.repository.community.InviteRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class InviteService {

  private final InviteRepository inviteRepository;
  private final MemberRepository memberRepository;
  private final StudyRoomRepository studyRoomRepository;
  public void create(InviteRequestDTO inviteRequestDTO) {
    Optional<Member> sender = memberRepository.findById(inviteRequestDTO.getSenderId());
    Optional<Member> receiver = Optional.ofNullable(memberRepository.findByEmail(inviteRequestDTO.getReceiverEmail()));

    sender.orElseThrow(() -> new NotRegisteredException("sender"));
    receiver.orElseThrow(() -> new NotRegisteredException("receiver"));

    // do nothing if sender == receiver
    if (sender.get().equals(receiver.get())) return;

    Invite invite = new Invite();
    invite.setSender(sender.get());
    invite.setReceiver(receiver.get());

    if (inviteRequestDTO.getStudyRoomId() != null) {
      Optional<StudyRoom> studyRoom = studyRoomRepository.findById(inviteRequestDTO.getStudyRoomId());
      studyRoom.orElseThrow(() -> new NotRegisteredException("studyroom"));
      invite.setStudyRoom(studyRoom.get());
    }

    inviteRepository.save(invite);
  }

  public void deleteOne(Long inviteId, Long memberId) {
    Optional<Member> optionalReceiver = memberRepository.findById(memberId);
    Optional<Invite> optionalInvite = inviteRepository.findOne(inviteId);

    optionalReceiver.orElseThrow(() -> new NotRegisteredException("RECEIVER"));
    optionalInvite.orElseThrow(() -> new NotRegisteredException("INVITE"));

    if (memberId.equals(
        optionalInvite.get().getReceiver().getId()
    )) {
      inviteRepository.deleteOne(optionalInvite.get());
    }
  }

  public List<InviteResponseDTO> findSent(Long memberId) {
    List<Invite> inviteList = inviteRepository.findSent(memberId);
    return inviteList.stream().map((i) -> {
      return new InviteResponseDTO(i);})
        .collect(toList());
  }

  public List<InviteResponseDTO> findReceived(Long memberId) {
    List<Invite> inviteList = inviteRepository.findReceived(memberId);
    List<InviteResponseDTO> toReturn = inviteList.stream()
        .map((i) -> {return new InviteResponseDTO(i);}).toList();
    // check read
    inviteList.forEach((invite) -> invite.setNotRead(false));
    return toReturn;
  }
}
