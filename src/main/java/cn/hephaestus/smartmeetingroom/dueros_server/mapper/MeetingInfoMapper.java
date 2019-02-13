package cn.hephaestus.smartmeetingroom.dueros_server.mapper;

import cn.hephaestus.smartmeetingroom.dueros_server.model.ReserveInfo;
import org.apache.ibatis.annotations.*;

import java.util.Date;

public interface MeetingInfoMapper {

    //根据uid查询用户当日所有的会议
    @Select("select * from reserve_table where reserve_id in (select mid from meeting_participant where uid=#{uid}) and to_days(start_time)=to_days(#{date})")
    @Results(id="reserveInfoMap",value = {
            @Result(property = "reserveId",column = "reserve_id"),
            @Result(property = "startTime",column = "start_time"),
            @Result(property = "endTime",column = "end_time"),
            @Result(property = "reserveUid",column = "reserve_uid"),
            @Result(property = "reserveOid",column = "reserve_oid"),
            @Result(property = "reserveDid",column = "reserve_did"),
    })
    public ReserveInfo[] getAllReserveInfo(@Param("uid") Integer uid, @Param("date")Date date);

}
