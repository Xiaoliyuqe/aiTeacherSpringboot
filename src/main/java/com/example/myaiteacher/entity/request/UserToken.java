package com.example.myaiteacher.entity.request;

import com.example.myaiteacher.entity.User;
import lombok.Data;


@Data
public class UserToken {
    private User user;
    private String token;
}
