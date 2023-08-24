package com.dokkaebi.service;

import com.dokkaebi.domain.admin.Notice;
import com.dokkaebi.domain.admin.NoticeDTO;
import com.dokkaebi.domain.admin.Qna;
import com.dokkaebi.repository.AdminRepository;
import com.dokkaebi.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

  private final MemberRepository memberRepository;
  private final AdminRepository adminRepository;
  public Long postNotice(Notice notice) {
    return adminRepository.save(notice);
  }

  public List<NoticeDTO> getNoticeList() {
    return adminRepository.getNoticeList()
        .stream().map((notice -> {
          return new NoticeDTO(notice);
        })).toList();
  }

  public NoticeDTO getNoticeDetail(Long noticeId) {
    return new NoticeDTO(adminRepository.getOneNotice(noticeId));
  }

  public void deleteNotice(Long noticeId) {
    Notice notice = adminRepository.getOneNotice(noticeId);
    adminRepository.delete(notice);
  }

  public Long updateNotice(Notice notice) {
    return adminRepository.update(notice);
  }

  public Long postQna(Qna qna, Long memberId) {
    qna.setWriterEmail(memberRepository.findById(memberId).get().getEmail());
    return adminRepository.save(qna);
  }


  public List<Qna> getQnaList() {
    return adminRepository.getQnaList();
  }

  public Qna getQnaDetail(Long qnaId) {
    return adminRepository.getOneQna(qnaId);
  }

  public void deleteQna(Long qnaId) {
    Qna qna = adminRepository.getOneQna(qnaId);
    adminRepository.delete(qna);
  }



}
