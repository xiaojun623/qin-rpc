package com.qin.qinrpc.examplespringbootprovider;

import cn.hutool.core.util.RandomUtil;
import com.qin.qinrpc.example.common.model.User;
import com.qin.qinrpc.example.common.service.UserService;
import com.qin.qinrpc.springboot.starter.annotation.RpcService;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 */
@Service
@RpcService
public class UserServiceImpl implements UserService {

    public User getUser(User user) {
        String name = user.getName();
        String numbers = RandomUtil.randomNumbers(5);
        String newName = name +numbers;
        System.out.println("用户原始名称：" + name);
        System.out.println("用户生成名称：" + newName);
        user.setName(newName);
        return user;
    }
}
