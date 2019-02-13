package cn.hephaestus.smartmeetingroom.dueros_server.service;

import cn.hephaestus.smartmeetingroom.dueros_server.model.User;
import cn.hephaestus.smartmeetingroom.dueros_server.model.UserInfo;

/**
 * 与用户有关的接口
 */
public interface UserService {
    public User getUser(String deviceId);

    public UserInfo getUserInfo(Integer uid);
}
