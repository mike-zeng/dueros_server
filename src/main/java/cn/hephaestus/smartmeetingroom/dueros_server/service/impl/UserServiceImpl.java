package cn.hephaestus.smartmeetingroom.dueros_server.service.impl;

import cn.hephaestus.smartmeetingroom.dueros_server.mapper.UserInfoMapper;
import cn.hephaestus.smartmeetingroom.dueros_server.mapper.UserMapper;
import cn.hephaestus.smartmeetingroom.dueros_server.model.User;
import cn.hephaestus.smartmeetingroom.dueros_server.model.UserInfo;
import cn.hephaestus.smartmeetingroom.dueros_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public User getUser(String deviceId) {
        User user=userMapper.getUserByDeviceId(deviceId);
        return user;
    }

    @Override
    public UserInfo getUserInfo(Integer uid) {
        return userInfoMapper.getUserInfoById(uid);
    }
}
