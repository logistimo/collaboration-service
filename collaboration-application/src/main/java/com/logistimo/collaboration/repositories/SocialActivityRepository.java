package com.logistimo.collaboration.repositories;

import com.logistimo.collaboration.entities.SocialActivity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by kumargaurav on 09/11/17.
 */
public interface SocialActivityRepository extends JpaRepository<SocialActivity,String> {

  @Query(value = "SELECT * FROM SOCIALACTIVITY WHERE SCONTXTID=:scontxtid AND ACTIVITYTY=:actvityty ", nativeQuery = true)
  public SocialActivity findActivityByContxtAndtype(@Param(value = "scontxtid") String scontxtid, @Param(value = "actvityty") String actvityty);

}
