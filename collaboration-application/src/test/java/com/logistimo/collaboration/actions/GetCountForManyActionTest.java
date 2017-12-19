package com.logistimo.collaboration.actions;

import com.logistimo.collaboration.core.models.LikeCountContainer;
import com.logistimo.collaboration.repositories.LikeRepositoryCustom;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import java.util.Collections;


/**
 * Created by kumargaurav on 08/12/17.
 */
public class GetCountForManyActionTest {

  @Mock
  LikeRepositoryCustom repo;

  GetCountForManyAction action;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    action = new GetCountForManyAction();
    action.setCustomRepository(repo);
  }

  @Test
  public void testInvoke () {
    when(repo.getLikesForMany(any(),anyString())).thenReturn(Collections.emptyList());
    Assert.assertNotNull(action.invoke(new LikeCountContainer()));
  }
}
