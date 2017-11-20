package com.logistimo.social.event.handler;

import com.logistimo.social.core.event.Event;

/**
 * Created by kumargaurav on 10/11/17.
 */
public interface Handler<E extends Event> {

   void handle(E event);
}