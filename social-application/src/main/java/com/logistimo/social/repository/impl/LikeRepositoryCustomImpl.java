package com.logistimo.social.repository.impl;

import com.logistimo.social.core.model.LikeCountModel;
import com.logistimo.social.core.model.LikeModel;
import com.logistimo.social.core.model.PageParam;
import com.logistimo.social.repository.LikeRepositoryCustom;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by kumargaurav on 22/11/17.
 */

@Repository
@Transactional(readOnly = true)
public class LikeRepositoryCustomImpl implements LikeRepositoryCustom {

  @PersistenceContext
  EntityManager em;

  @Override
  public List<LikeCountModel> getLikesForMany(List<LikeCountModel> models,String user) {

    String squery = "select sc.OBJID, sc.OBJTY, sc.CONTXTID, count(sl.id), sum (case when sl.liker in (:user) then 1 else 0 end) as liked"
        + " from SOCIALLIKE sl join SOCIALACTIVITY sa on (sa.id = sl.SACTVTYID) join SOCIALCONTEXT sc on (sc.id= sa.SCONTXTID) "
        + " where (sc.OBJID, sc.OBJTY, sc.CONTXTID) in (";
    String groupbyQuery =") group by sa.id";

    StringBuilder builder = new StringBuilder();
    int c = 0;
    for(LikeCountModel model: models) {
      c++;
      builder.append("( ");
      builder.append("\"").append(model.getObjectId()).append("\"").append(", ");
      builder.append("\"").append(model.getObjectType()).append("\"").append(", ");
      builder.append("\"").append(model.getContextId()).append("\"");
      builder.append(" )");
      if (c != models.size()) {
        builder.append(" , ");
      }
    }
    Query q = em.createNativeQuery(squery + builder.toString() + groupbyQuery);
    q.setParameter("user",user);
    List<Object[]> list = q.getResultList();
    return convert(list);
  }

  @Override
  public List<LikeModel> getLikesByObj(String objectId, String objectType, PageParam param) {

    String query = "select sc.OBJID, sc.OBJTY, sc.CONTXTID, sc.CONTXTTY, sc.CONTXTATTR, sl.LIKER, sl.CRON from SOCIALLIKE sl"
        +" join SOCIALACTIVITY sa on (sa.id = sl.SACTVTYID) join SOCIALCONTEXT sc on (sc.id= sa.SCONTXTID) where sc.OBJID = :objId and sc.OBJTY=:objTy "
        + " ORDER BY sl.CRON desc limit :page,:size";
    Query q = em.createNativeQuery(query);
    q.setParameter("objId",objectId);
    q.setParameter("objTy",objectType);
    q.setParameter("page",param.getPage());
    q.setParameter("size",param.getSize());
    List<Object[]> list = q.getResultList();
    return convertToLikeModel(list);
  }

  @Override
  public Long countLikesByObj(String objectId, String objectType) {
    String query = "select count(*) from SOCIALLIKE sl"
        +" join SOCIALACTIVITY sa on (sa.id = sl.SACTVTYID) join SOCIALCONTEXT sc on (sc.id= sa.SCONTXTID) "
        + " where sc.OBJID = :objId and sc.OBJTY=:objTy ";
    Query q = em.createNativeQuery(query);
    q.setParameter("objId",objectId);
    q.setParameter("objTy",objectType);
    return ((BigInteger) q.getSingleResult()).longValue();
  }

  @Override
  public List<LikeModel> getLikesByObjAndContxt(String objectId, String objectType,
                                                String contextId, PageParam param) {

    String query = "select sc.OBJID, sc.OBJTY, sc.CONTXTID, sc.CONTXTTY, sc.CONTXTATTR, sl.LIKER, sl.CRON from SOCIALLIKE sl"
                   +" join SOCIALACTIVITY sa on (sa.id = sl.SACTVTYID) join SOCIALCONTEXT sc on (sc.id= sa.SCONTXTID) where sc.OBJID = :objId and sc.OBJTY=:objTy "
                   + " and sc.CONTXTID = :contxtId ORDER BY sl.CRON desc limit :page,:size";
    Query q = em.createNativeQuery(query);
    q.setParameter("objId",objectId);
    q.setParameter("objTy",objectType);
    q.setParameter("contxtId",contextId);
    q.setParameter("page",param.getPage());
    q.setParameter("size",param.getSize());
    List<Object[]> list = q.getResultList();
    return convertToLikeModel(list);
  }

  @Override
  public Long countLikesByObjAndContxt(String objectId, String objectType, String contextId) {

    String query = "select count(*) from SOCIALLIKE sl"
        +" join SOCIALACTIVITY sa on (sa.id = sl.SACTVTYID) join SOCIALCONTEXT sc on (sc.id= sa.SCONTXTID) "
        + " where sc.OBJID = :objId and sc.OBJTY=:objTy and sc.CONTXTID = :contxtId ";
    Query q = em.createNativeQuery(query);
    q.setParameter("objId",objectId);
    q.setParameter("objTy",objectType);
    q.setParameter("contxtId",contextId);
    return ((BigInteger) q.getSingleResult()).longValue();
  }

  private List<LikeCountModel> convert (List<Object[]> result) {

    List<LikeCountModel> list = new ArrayList<>();
    LikeCountModel m  = null;
    for(Object[] row: result) {
      m = new LikeCountModel();
      m.setObjectId((String)row[0]);
      m.setObjectType((String)row[1]);
      m.setContextId((String)row[2]);
      m.setCount(((BigInteger)row[3]).intValue());
      m.setLiked(!((((BigDecimal)row[4]).intValue()) == 0));
      list.add(m);
    }
    return list;
  }

  private List<LikeModel> convertToLikeModel (List<Object[]> result) {

    List<LikeModel> list = new ArrayList<LikeModel>();
    LikeModel m  = null;
    for(Object[] row: result) {
      m = new LikeModel();
      m.setObjectId((String)row[0]);
      m.setObjectType((String)row[1]);
      m.setContextId((String)row[2]);
      m.setContextType((String)row[3]);
      m.setContextAttributes((String)row[4]);
      m.setLiker((String)row[5]);
      m.setCron(((Timestamp)row[6]).toString());
      list.add(m);
    }
    return list;
  }
}
