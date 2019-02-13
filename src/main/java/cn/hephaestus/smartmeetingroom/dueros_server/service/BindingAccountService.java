package cn.hephaestus.smartmeetingroom.dueros_server.service;

import cn.hephaestus.smartmeetingroom.dueros_server.model.ReserveInfo;

import java.util.Date;


/**
 * 绑定用户账号
 */
public interface BindingAccountService {
    public ReserveInfo[] getReserveInfoByDate(Integer uid,Date date);
}
