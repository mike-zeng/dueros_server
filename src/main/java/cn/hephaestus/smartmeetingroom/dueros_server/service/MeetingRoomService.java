package cn.hephaestus.smartmeetingroom.dueros_server.service;

import cn.hephaestus.smartmeetingroom.dueros_server.model.MeetingRoom;

import java.util.Date;

public interface MeetingRoomService {
    public MeetingRoom[] getAllUsableMeetingRooms(Integer oid, Date startTime, Date endTime, Integer num);

}
