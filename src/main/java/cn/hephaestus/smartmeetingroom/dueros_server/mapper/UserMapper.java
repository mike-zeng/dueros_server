package cn.hephaestus.smartmeetingroom.dueros_server.mapper;

import cn.hephaestus.smartmeetingroom.dueros_server.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("select * from user where id=(select userId from dueros_account where deviceId=#{deviceId})")
    @Results(id="userMap",value={
            @Result(column = "reserve_jurisdiction",property = "reserveJurisdiction"),
    })
    User getUserByDeviceId(String deviceId);
}
