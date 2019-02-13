package cn.hephaestus.smartmeetingroom.dueros_server.service.impl;

import cn.hephaestus.smartmeetingroom.dueros_server.mapper.MeetingInfoMapper;
import cn.hephaestus.smartmeetingroom.dueros_server.model.ReserveInfo;
import cn.hephaestus.smartmeetingroom.dueros_server.service.MeetingInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MeetingInfoServiceImpl implements MeetingInfoService {
    @Autowired
    MeetingInfoMapper meetingInfoMapper;
    @Override
    public ReserveInfo[] getAllReserveInfoByUid(Integer uid, Date date) {
       ReserveInfo[] reserveInfos=meetingInfoMapper.getAllReserveInfo(uid,date);
        return reserveInfos;
    }
}
