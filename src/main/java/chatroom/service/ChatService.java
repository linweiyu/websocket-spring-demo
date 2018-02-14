package chatroom.service;

import chatroom.model.Message;
import chatroom.model.ResponseBase;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    @Autowired
    Gson json;

    public ResponseBase<Message> chat(String jsonStr) {
        Message message = json.fromJson(jsonStr, Message.class);
        ResponseBase<Message> responseBase = new ResponseBase<Message>(1, 201, message);
        return responseBase;
    }
}
