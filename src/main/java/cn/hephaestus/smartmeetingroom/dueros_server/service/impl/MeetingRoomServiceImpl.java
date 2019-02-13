package cn.hephaestus.smartmeetingroom.dueros_server.service.impl;

import cn.hephaestus.smartmeetingroom.dueros_server.mapper.MeetingInfoMapper;
import cn.hephaestus.smartmeetingroom.dueros_server.mapper.MeetingRoomMapper;
import cn.hephaestus.smartmeetingroom.dueros_server.mapper.ReserveTableMapper;
import cn.hephaestus.smartmeetingroom.dueros_server.model.MeetingRoom;
import cn.hephaestus.smartmeetingroom.dueros_server.service.MeetingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MeetingRoomServiceImpl implements MeetingRoomService {
    @Autowired
    MeetingRoomMapper meetingRoomMapper;

    @Autowired
    ReserveTableMapper reserveTableMapper;

    @Override
    public MeetingRoom[] getAllUsableMeetingRooms(Integer oid, Date startTime, Date endTime, Integer num) {
        List<MeetingRoom> list=new ArrayList<>();
        MeetingRoom[] meetingRooms=meetingRoomMapper.getMeetingRoomList(oid);//获取所有的会议室
        Integer[] integers=reserveTableMapper.queryAllUnUsableMeetingroom(oid,startTime,endTime);//该公司当前时段不可用的会议室id
        Set<Integer> set=new HashSet<>();
        for (Integer integer:integers){
            set.add(integer);
        }
        for (MeetingRoom meetingRoom:meetingRooms){
            if ( (!set.contains(meetingRoom.getRoomId()) )&&meetingRoom.getCapacity()>=num&&meetingRoom.isAvailable()){
                meetingRoom.setMacAddress(null);
                list.add(meetingRoom);
            }
        }
        MeetingRoom[] arr=new MeetingRoom[list.size()];
        list.toArray(arr);
        return arr;
    }
}
