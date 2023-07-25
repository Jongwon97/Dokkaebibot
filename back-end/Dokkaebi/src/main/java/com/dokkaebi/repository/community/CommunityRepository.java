package com.dokkaebi.repository.community;

import com.dokkaebi.domain.community.Article;
import com.dokkaebi.domain.community.Comment;
import com.dokkaebi.domain.community.CommunityEntity;
import java.util.Optional;

public interface CommunityRepository {

  public Long findMemberId(Long entityId);

  public Optional<? extends CommunityEntity> findOne(Long entityId);

}
