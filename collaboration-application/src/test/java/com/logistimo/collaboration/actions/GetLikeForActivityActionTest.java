package com.logistimo.collaboration.actions;

import com.logistimo.collaboration.core.models.GetLikeResponseModel;
import com.logistimo.collaboration.repositories.LikeRepositoryCustom;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by kumargaurav on 08/12/17.
 */
public class GetLikeForActivityActionTest {

  @Mock
  LikeRepositoryCustom repo;

  GetLikeForActivityAction action;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    action = new GetLikeForActivityAction();
    action.setCustomRepository(repo);
  }


  @Test
  public void testInvokeForCountOnly () {
    when(repo.countLikesByObj(anyString(),anyString())).thenReturn(5l);
    GetLikeResponseModel response = action.invoke(anyString(),anyString(),null,true,0,0);
    Assert.assertNotNull(response);
    Assert.assertEquals(5,response.getTotal());
    Assert.assertNull(response.getLikes());
  }

  @Test
  public void testInvokeForCountWithLikes () {
    when(repo.countLikesByObj(anyString(),anyString())).thenReturn(5l);
    when(repo.getLikesByObj(anyString(),anyString(),any())).thenReturn(Collections.emptyList());
    GetLikeResponseModel response = action.invoke(anyString(),anyString(),null,false,0,0);
    Assert.assertNotNull(response);
    Assert.assertEquals(5,response.getTotal());
    Assert.assertNotNull(response.getLikes());
  }
}
