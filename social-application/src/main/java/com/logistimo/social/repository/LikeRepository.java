package com.logistimo.social.repository;

import com.logistimo.social.entity.Like;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by kumargaurav on 09/11/17.
 */
public interface LikeRepository extends JpaRepository<Like,String> {

  List<Like> findBySactvtyid(String sactvtyid, Pageable page);

  Long countBySactvtyid(String sactvtyid);
}
