package chatroom.controller;

import chatroom.model.Message;
import chatroom.model.ResponseBase;
import chatroom.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @Autowired
    ChatService chatService;
    @MessageMapping("/talk")
    @SendTo("/topic/chat")
    public ResponseBase<Message> chat(String message){
        return chatService.chat(message);
    }
}
