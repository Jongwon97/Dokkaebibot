package com.dokkaebi.repository;

import com.dokkaebi.domain.admin.Notice;
import com.dokkaebi.domain.admin.Qna;
import com.dokkaebi.service.TimeService;
import jakarta.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdminRepository {

  public final EntityManager entityManager;

  public Long save(Notice notice) {
    entityManager.persist(notice);
    return notice.getId();
  }

  public Long save(Qna qna) {
    entityManager.persist(qna);
    return qna.getId();
  }
  public void delete(Notice notice) {
    entityManager.remove(notice);
  }
  public void delete(Qna qna) {
    entityManager.remove(qna);
  }


  public List<Notice> getNoticeList() {
    return entityManager.createQuery(
        "SELECT n FROM Notice n", Notice.class
    ).getResultList();
  }

  public Notice getOneNotice(Long noticeId) {
    return entityManager.find(Notice.class, noticeId);
  }

  public Qna getOneQna(Long qnaId) {
    return entityManager.find(Qna.class, qnaId);
  }

  public List<Qna> getQnaList() {
    return entityManager.createQuery(
        "SELECT q FROM Qna q", Qna.class
    ).getResultList();

  }

  public Long update(Notice notice) {
    Notice attached = entityManager.find(Notice.class, notice.getId());
    attached.setTitle(notice.getTitle());
    attached.setContent(notice.getContent());
    return attached.getId();
  }
}
