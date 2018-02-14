package chatroom.service;

import chatroom.model.ResponseBase;
import chatroom.model.User;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {
    static Map<String, User> users = new ConcurrentHashMap<>();
    @Autowired
    Gson json;
    public ResponseBase<User> login(String jsonStr){
        User user = json.fromJson(jsonStr, User.class);
        user.setId();
        users.put(user.getId(), user);
        ResponseBase<User> responseBase = new ResponseBase<>(1, 100, user);
        return responseBase;
    }

    public ResponseBase<Null> logout(String jsonStr){
        User user = json.fromJson(jsonStr, User.class);
        ResponseBase<Null> responseBase = new ResponseBase<>();
        responseBase.setType(101);
        if(!users.containsKey(user.getId())){
            responseBase.setSignal(0);
        }else{
            users.remove(user.getId());
            responseBase.setSignal(1);
        }
        return responseBase;
    }
}
