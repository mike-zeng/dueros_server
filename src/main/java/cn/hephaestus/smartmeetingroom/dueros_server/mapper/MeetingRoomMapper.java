package cn.hephaestus.smartmeetingroom.dueros_server.mapper;

import cn.hephaestus.smartmeetingroom.dueros_server.model.MeetingRoom;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MeetingRoomMapper {


    @Select("select * from meeting_room where oid=#{oid}")
    @Results({
            @Result(column = "room_id",property = "roomId"),
            @Result(column = "room_name",property = "roomName"),
            @Result(column = "mac_address",property ="macAddress"),
            @Result(column = "register_time",property = "registerTime")
    })
    public MeetingRoom[] getMeetingRoomList(@Param("oid") Integer oid);

}
