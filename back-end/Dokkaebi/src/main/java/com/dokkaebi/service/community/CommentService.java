package com.dokkaebi.service.community;

import static java.util.stream.Collectors.toList;
import com.dokkaebi.domain.Member;
import com.dokkaebi.domain.community.Article;
import com.dokkaebi.domain.community.Comment;
import com.dokkaebi.domain.community.CommentDTO;
import com.dokkaebi.exception.NotRegisteredException;
import com.dokkaebi.repository.MemberRepository;
import com.dokkaebi.repository.community.ArticleRepository;
import com.dokkaebi.repository.community.CommentRepository;
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
public class CommentService {

  private final CommentRepository commentRepository;
  private final MemberRepository memberRepository;
  private final ArticleRepository articleRepository;
  private final CommunityExceptionService communityExceptionService;

  public void create(Comment comment, Long articleId, Long memberId) throws NotRegisteredException {
    Optional<Article> article = articleRepository.findOne(articleId);
    Optional<Member> member = memberRepository.findById(memberId);
    article.orElseThrow(() -> new NotRegisteredException("article"));
    member.orElseThrow(() -> new NotRegisteredException("member"));
    comment.setWriter(member.get());
    comment.setArticle(article.get());
    commentRepository.save(comment);
  }

  public List<CommentDTO> getFromArticle(Long articleId) {
    List<Comment> commentList = commentRepository.findFromArticle(articleId);
    return commentList.stream()
        .map(c -> new CommentDTO(c))
        .collect(toList());
  }

  public void updateOne(Comment comment, Long commentId, Long memberId) throws RuntimeException{
    communityExceptionService.isAuthorized(commentRepository, commentId, memberId);
    commentRepository.updateOne(comment, commentId);
  }

  public void deleteOne(Long commentId, Long memberId) throws RuntimeException{
    communityExceptionService.isAuthorized(commentRepository, commentId, memberId);
    commentRepository.deleteOne(commentId);
  }
}
