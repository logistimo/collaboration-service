package com.logistimo.social.repository;

import com.logistimo.social.core.model.SocialLikerModel;
import com.logistimo.social.entity.SocialContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by kumargaurav on 09/11/17.
 */
public interface SocialContextRepository extends JpaRepository<SocialContext,String> {

  @Query(value = "SELECT * FROM SOCIALCONTEXT WHERE OBJID= ?1 AND OBJTY= ?2 AND CONTXTID = ?3 AND CONTXTTY = ?4", nativeQuery = true)
  SocialContext findContextByObjectAndContxt(String objectid, String objectty, String contextid, String contextty);

  List<SocialContext> findByObjidAndObjty(String objid, String objty);

}
