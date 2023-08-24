package com.dokkaebi.controller.community;

import com.dokkaebi.domain.community.Article;
import com.dokkaebi.domain.community.ArticleDTO;
import com.dokkaebi.domain.community.ArticleSimpleDTO;
import com.dokkaebi.exception.NotAuthorizedException;
import com.dokkaebi.exception.NotRegisteredException;
import com.dokkaebi.service.JwtServiceImpl;
import com.dokkaebi.service.community.ArticleService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequestMapping("/api/community/article")
@RestController
@RequiredArgsConstructor
public class ArticleController {

  private final ArticleService articleService;
  private final JwtServiceImpl jwtService;

  @Value("${front.host}")
  String frontHost;

  @Value("${front.scheme}")
  String frontScheme;

  // CREATE
  @GetMapping("/create/check")
  public ResponseEntity<?> beforeCreateArticle() {

    UriComponents uriComponents = UriComponentsBuilder.newInstance()
        .scheme(frontScheme)
        .host(frontHost)
        .path("/community/article/create")
        .build();

    // get redirect location to header
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("Location", uriComponents.toString());
    // redirect to create page with 302
    return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);
  }

  @PostMapping("/create/check")
  public ResponseEntity<?> createArticle(
      @RequestBody Article article,
      HttpServletRequest httpServletRequest){

    Long memberId = (Long) httpServletRequest.getAttribute("id");
    try {
      Long articleId = articleService.create(article, memberId);
      MultiValueMap<String, Long> body = new LinkedMultiValueMap<>();
      body.add("id", articleId);
      return new ResponseEntity<>(body, HttpStatus.OK);
    } catch (NotRegisteredException | NotAuthorizedException exception) {
      return ResponseEntity.badRequest().body(exception);
    }
  }

  // READ
  @GetMapping("/")
  public ResponseEntity<?> getAll() {
    List<ArticleDTO> dtoList = articleService.getAll();
    return new ResponseEntity<>(dtoList, HttpStatus.OK);

  }

  @GetMapping("/popular/{start}/{num}")
  public ResponseEntity<?> getPopular(
      @PathVariable int start,
      @PathVariable int num) {
    List<ArticleSimpleDTO> simpleDTOList = articleService.getPopular(start, num);
    return new ResponseEntity<>(simpleDTOList, HttpStatus.OK);
  }

  @GetMapping("/new/{start}/{num}")
  public ResponseEntity<?> getNew(
      @PathVariable int start,
      @PathVariable int num) {
    List<ArticleSimpleDTO> simpleDTOList = articleService.getNew(start, num);
    return new ResponseEntity<>(simpleDTOList, HttpStatus.OK);
  }

  @GetMapping("/search")
  public ResponseEntity<?> searchArticle(
      @RequestParam("title") String articleTitle) {

    return new ResponseEntity<>(articleService.findLikeTitle(articleTitle), HttpStatus.OK);
  }

  @GetMapping("/{id}/zzz")
  public ResponseEntity<?> readArticleDetail(
      @PathVariable Long id,
      HttpServletRequest httpServletRequest) {

    String accessToken=httpServletRequest.getHeader("accessToken"); // 헤더에서 토큰 꺼냄
    Long memberId = jwtService.getIdFromToken(accessToken);
    try {
      return new ResponseEntity<>(articleService.getOne(id, memberId), HttpStatus.OK);
    } catch (NotRegisteredException exception) {
      return ResponseEntity.badRequest().body(exception);
    }
  }

  // UPDATE
  @GetMapping("/{id}/check")
  public ResponseEntity<?> beforeUpdateArticle(
      @PathVariable Long id) {

    UriComponents uriComponents = UriComponentsBuilder.newInstance()
        .scheme(frontScheme)
        .host(frontHost)
        .path("/community/article/update")
        .build();

    // redirect
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("Location", uriComponents.toString());

    // send dto find by id
    MultiValueMap<String, ArticleDTO> body = new LinkedMultiValueMap<>();
    ArticleDTO articleDTO = articleService.getOne(id);
    body.add("article", articleDTO);

    // redirect to create page with 302
    return new ResponseEntity<>(body, httpHeaders, HttpStatus.FOUND);
  }

  @PutMapping("/{id}/check")
  public ResponseEntity<?> updateArticle(
      @RequestBody Article article,
      @PathVariable Long id,
      HttpServletRequest httpServletRequest) {


    Long memberId = (Long) httpServletRequest.getAttribute("id");
    try {
      Long articleId = articleService.updateOne(article, id, memberId);
      MultiValueMap<String, Long> body = new LinkedMultiValueMap<>();
      body.add("id", articleId);
      return new ResponseEntity<>(body, HttpStatus.OK);
    } catch (NotRegisteredException exception) {
      return ResponseEntity.badRequest().body(exception);
    }

  }

  // DELETE
  @DeleteMapping("/{id}/check")
  public ResponseEntity<?> deleteArticle(
      @PathVariable("id") Long articleId,
      HttpServletRequest httpServletRequest) {

    Long memberId = (Long) httpServletRequest.getAttribute("id");
    try {
      articleService.deleteOne(articleId, memberId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (NotRegisteredException exception) {
      return ResponseEntity.badRequest().body(exception);
    }
  }
}
