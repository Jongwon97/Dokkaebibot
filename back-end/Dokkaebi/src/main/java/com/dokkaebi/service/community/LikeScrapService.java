package com.dokkaebi.service.community;

import com.dokkaebi.domain.Member;
import com.dokkaebi.domain.community.Article;
import com.dokkaebi.domain.community.Like;
import com.dokkaebi.domain.community.Scrap;
import com.dokkaebi.exception.NotRegisteredException;
import com.dokkaebi.repository.MemberRepository;
import com.dokkaebi.repository.community.ArticleRepository;
import com.dokkaebi.repository.community.LikeScrapRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LikeScrapService {

  private final ArticleRepository articleRepository;
  private final MemberRepository memberRepository;
  private final LikeScrapRepository likeScrapRepository;

  public void toggleLike(Long articleId, Long memberId) throws NotRegisteredException {
    Optional<Member> optionalMember = memberRepository.findById(memberId);

    Optional<Article> optionalArticle = articleRepository.findOne(articleId);
    optionalArticle.orElseThrow(() -> new NotRegisteredException("article"));
    Optional<Like> optionalLike = likeScrapRepository.findOneLike(articleId, memberId);
    if (optionalLike.isEmpty()) {
      likeScrapRepository.saveOneLike(new Like(optionalArticle.get(), optionalMember.get()));
    } else {
      likeScrapRepository.deleteOneLike(optionalLike.get());
    }
  }
  public void toggleScrap(Long articleId, Long memberId) throws NotRegisteredException {
    Optional<Member> optionalMember = memberRepository.findById(memberId);

    Optional<Article> optionalArticle = articleRepository.findOne(articleId);
    optionalArticle.orElseThrow(() -> new NotRegisteredException("article"));
    // 있으면 좋아요 없으면 삭제...
    Optional<Scrap> optionalLike = likeScrapRepository.findOneScrap(articleId, memberId);
    if (optionalLike.isEmpty()) {
      likeScrapRepository.saveOneScrap(new Scrap(optionalArticle.get(), optionalMember.get()));
    } else {
      likeScrapRepository.deleteOneScrap(optionalLike.get());
    }
  }
}
