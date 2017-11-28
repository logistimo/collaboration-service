package com.logistimo.social.repository;

import com.logistimo.social.core.model.LikeCountModel;
import com.logistimo.social.core.model.LikeModel;
import com.logistimo.social.core.model.PageParam;

import java.util.List;

/**
 * Created by kumargaurav on 22/11/17.
 */
public interface LikeRepositoryCustom {

  List<LikeCountModel> getLikesForMany(List<LikeCountModel> models, String user);

  List<LikeModel> getLikesByObj(String objectId, String objectType,PageParam param);

  Long countLikesByObj(String objectId, String objectType);

  List<LikeModel> getLikesByObjAndContxt(String objectId, String objectType, String contextId, PageParam param);

  Long countLikesByObjAndContxt(String objectId, String objectType, String contextId);
}
