package com.logistimo.collaboration.repositories;

import com.logistimo.collaboration.entities.Like;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by kumargaurav on 09/11/17.
 */
public interface LikeRepository extends JpaRepository<Like,String> {

  List<Like> findBySactvtyid(String sactvtyid, Pageable page);

  Long countBySactvtyid(String sactvtyid);


  @Query(value = "select * from SOCIALLIKE sl join SOCIALACTIVITY sa on (sl.SACTVTYID = sa.id) "
      + "join SOCIALCONTEXT sc on (sc.id = sa.SCONTXTID ) "
      + "where sc.OBJID= ?1 and sc.OBJTY=?2  ORDER BY ?#{#pageable}", nativeQuery = true)
  List<Like> findLikersByObj(String objId, String objTy, Pageable pageable);


  @Query(value = "select count(*) from SOCIALLIKE sl join SOCIALACTIVITY sa on (sl.SACTVTYID = sa.id) "
      + "join SOCIALCONTEXT sc on (sc.id = sa.SCONTXTID ) "
      + "where sc.OBJID= ?1 and sc.OBJTY=?2 ", nativeQuery = true)
  Long countLikersByObj(String objId, String objTy);

  @Query(value = "select * from SOCIALLIKE sl join SOCIALACTIVITY sa on (sl.SACTVTYID = sa.id) "
      + "join SOCIALCONTEXT sc on (sc.id = sa.SCONTXTID ) "
      + "where sc.OBJID= ?1 and sc.OBJTY=?2 and sc.CONTXTID=?3 ORDER BY ?#{#pageable}", nativeQuery = true)
  List<Like> findLikersByObjAndContxt(String objId, String objTy, String contextId, Pageable pageable);


  @Query(value = "select count(*) from SOCIALLIKE sl join SOCIALACTIVITY sa on (sl.SACTVTYID = sa.id) "
      + "join SOCIALCONTEXT sc on (sc.id = sa.SCONTXTID ) "
      + "where sc.OBJID= ?1 and sc.OBJTY=?2 and sc.CONTXTID=?3", nativeQuery = true)
  Long countLikersByObjAndContxt(String objId, String objTy, String contextId);


  @Query(value = "select count(*) from SOCIALLIKE sl join SOCIALACTIVITY sa on (sl.SACTVTYID = sa.id) "
      + "join SOCIALCONTEXT sc on (sc.id = sa.SCONTXTID ) "
      + "where sc.OBJID= ?1 and sc.OBJTY=?2 and sl.LIKER=?3 ", nativeQuery = true)
  Long countLikersByObjAndUser(String objId, String objTy, String user);


  @Query(value = "select count(*) from SOCIALLIKE sl join SOCIALACTIVITY sa on (sl.SACTVTYID = sa.id) "
      + "join SOCIALCONTEXT sc on (sc.id = sa.SCONTXTID ) "
      + "where sc.OBJID= ?1 and sc.OBJTY=?2 and sc.CONTXTID=?3 and sl.LIKER=?4 ", nativeQuery = true)
  Long countLikersByObjAndContxtAndUser(String objId, String objTy, String contextId, String user);


}
