package cn.hephaestus.smartmeetingroom.dueros_server.service;

import cn.hephaestus.smartmeetingroom.dueros_server.model.ReserveInfo;

import java.util.Date;

public interface MeetingInfoService {
    public ReserveInfo[] getAllReserveInfoByUid(Integer uid, Date date);
}
