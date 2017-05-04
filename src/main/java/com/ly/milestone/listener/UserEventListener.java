package com.ly.milestone.listener;

import com.ly.milestone.model.User;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by lyc28724 on 2017/4/14.
 */
@Component
public class UserEventListener {

    @Async
    @EventListener
    public void userHandler(User user){
        // do sth.
    }
}
