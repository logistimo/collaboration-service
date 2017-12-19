package com.logistimo.collaboration.actions;

import com.logistimo.collaboration.core.models.LikerResponseModel;
import com.logistimo.collaboration.repositories.LikeRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

/**
 * Created by kumargaurav on 08/12/17.
 */
public class GetLikerForActivityActionTest {

  @Mock
  LikeRepository likeRepository;

  GetLikerForActivityAction action;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    action = new GetLikerForActivityAction();
    action.setLkRepository(likeRepository);
  }


  @Test
  public void testInvokeWithNoLikers () {
    when(likeRepository.countLikersByObj(anyString(),anyString())).thenReturn(0l);
    LikerResponseModel res = action.invoke(anyString(),anyString(),null,0,5);
    Assert.assertNull(res.getLikers());
  }

  @Test
  public void testInvokeWithLikers () {
    when(likeRepository.countLikersByObj(anyString(),anyString())).thenReturn(5l);
    when(likeRepository.findLikersByObj(anyString(),anyString(),any())).thenReturn(Collections.emptyList());
    LikerResponseModel res = action.invoke(anyString(),anyString(),null,0,5);
    Assert.assertEquals(5,res.getTotal());
    Assert.assertNotNull(res.getLikers());
  }
}
