package com.dokkaebi.service.community;

import static java.util.stream.Collectors.toList;

import com.dokkaebi.domain.Member;
import com.dokkaebi.domain.community.Article;
import com.dokkaebi.domain.community.ArticleDTO;
import com.dokkaebi.domain.community.ArticleSimpleDTO;
import com.dokkaebi.exception.NotAuthorizedException;
import com.dokkaebi.exception.NotRegisteredException;
import com.dokkaebi.repository.community.ArticleRepository;
import com.dokkaebi.repository.MemberRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {

  private final MemberRepository memberRepository;
  private final ArticleRepository articleRepository;
  private final CommunityExceptionService communityExceptionService;

  public Long create(Article article, Long memberId) throws NotRegisteredException {
    Optional<Member> member = memberRepository.findById(memberId);
    article.setWriter(member.get());
    return articleRepository.save(article);
  }

  public List<ArticleSimpleDTO> getPopular(int start, int num) {
    List<Article> temp = articleRepository.findPopular(start, num);
    return temp.stream()
        .map(o -> new ArticleSimpleDTO(o))
        .collect(toList());
  }

  public List<ArticleSimpleDTO> getNew(int start, int num) {
    List<Article> temp = articleRepository.findNew(start, num);
    return temp.stream()
        .map(o -> new ArticleSimpleDTO(o))
        .collect(toList());
  }
  public List<ArticleDTO> getAll() {
    List<Article> temp = articleRepository.findAll();
    return temp.stream()
        .map(o -> new ArticleDTO(o))
        .collect(toList());
  }

  public ArticleDTO getOne(Long articleId, Long memberId) {
    // CHECK EXCEPTION
    Optional<Article> article = articleRepository.findOne(articleId);
    article.orElseThrow(() -> new NotRegisteredException("article"));
    ArticleDTO articleDTO = new ArticleDTO(article.get());
    articleDTO.setLiked(article.get().getLikeList().stream()
        .anyMatch((like) -> {
                return like.getMember().getId().equals(memberId);
            }));
    articleDTO.setScrapped(article.get().getScrapList().stream()
        .anyMatch((scrap) -> scrap.getMember().getId().equals(memberId)));
    return articleDTO;
  }

  public ArticleDTO getOne(Long articleId) {
    // CHECK EXCEPTION
    Optional<Article> article = articleRepository.findOne(articleId);
    article.orElseThrow(() -> new NotRegisteredException("article"));
    return new ArticleDTO(article.get());
  }


  public void deleteOne(Long articleId, Long memberId) throws NotAuthorizedException {
    communityExceptionService.isAuthorized(articleRepository, articleId, memberId);
    articleRepository.deleteOne(articleId);
  }
  public Long updateOne(Article article, Long articleId, Long memberId) throws NotAuthorizedException{

    communityExceptionService.isAuthorized(articleRepository, articleId, memberId);
    return articleRepository.updateOne(article);
  }

  public List<ArticleDTO> findLikeTitle(String articleTitle) {
    return articleRepository.findLikeTitle(articleTitle).stream()
        .map((a)->{return new ArticleDTO(a);})
        .collect(toList());
  }
}
