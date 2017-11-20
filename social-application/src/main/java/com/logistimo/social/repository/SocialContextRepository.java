package com.logistimo.social.repository;

import com.logistimo.social.entity.SocialContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by kumargaurav on 09/11/17.
 */
public interface SocialContextRepository extends JpaRepository<SocialContext,String> {

  @Query(value = "SELECT * FROM SOCIALCONTEXT WHERE OBJID=:objectid AND OBJTY=:objectty AND CONTXTID=:contextid AND CONTXTTY=:contextty", nativeQuery = true)
  public SocialContext findContextByObjectAndContxt(@Param(value = "objectid") String objectid, @Param(value = "objectty") String objectty,
                                                    @Param(value = "contextid") String contextid, @Param(value = "contextty") String contextty);

}
