package chatroom.controller;

import chatroom.model.ResponseBase;
import chatroom.model.User;
import chatroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import javax.validation.constraints.Null;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @MessageMapping("/login")
    @SendToUser(value = "/topic/user", broadcast = false)
    public ResponseBase<User> login(String jsonStr){
        return userService.login(jsonStr);
    }

    @MessageMapping("/logout")
    @SendToUser("/topic/user")
    public ResponseBase<Null> logout(String jsonStr){
        return userService.logout(jsonStr);
    }

}
