package com.qin.qinrpc.example.consumer;

import com.qin.qinrpc.example.common.model.User;
import com.qin.qinrpc.example.common.service.UserService;
import com.qin.qinrpc.bootstrap.ConsumerBootstrap;
import com.qin.qinrpc.proxy.ServiceProxyFactory;

/**
 * 服务消费者示例
 */
public class ConsumerExample {

    public static void main(String[] args) {
        // 服务提供者初始化
        ConsumerBootstrap.init();

        // 获取代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("zhangsan");
        // 第一次调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
        // 第二次调用
        User newUser2 = userService.getUser(user);
        if (newUser2 != null) {
            System.out.println(newUser2.getName());
        } else {
            System.out.println("user == null");
        }
        // 第三次调用
        User newUser3 = userService.getUser(user);
        if (newUser3 != null) {
            System.out.println(newUser3.getName());
        } else {
            System.out.println("user == null");
        }
    }
}
