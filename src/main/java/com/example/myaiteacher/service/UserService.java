package com.example.myaiteacher.service;

import com.example.myaiteacher.entity.User;
import com.example.myaiteacher.mapper.UserMapper;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class UserService {
    UserMapper userMapper;
    public List<User> userList(){
        return null;
    }

    public String addUser(User user){
        List<String> name_list = userMapper.selectUsernameByUsername(user.getUsername());
        if(!name_list.isEmpty())
            return "用户名已存在";
        userMapper.addUser(user.getUsername(), user.getPassword(), user.getPhoneNum(), user.getEmail());
        return "";
    }
    public User login(String username, String password){
        User user = userMapper.selectUserByUsername(username);
        if(user == null){
            return null;
        }
        if(user.getPassword().equals(password))
            //HttpSession session =
            return user;
        else
            return null;
    }

    public boolean changePassword(String username, String newPassword) {
        User oldUser = userMapper.selectUserByUsername(username);
        if(oldUser == null)
            return false;
        oldUser.setPassword(newPassword);
        return userMapper.changePassword(oldUser.getUsername(), oldUser.getPassword()) != 0;
    }
}
