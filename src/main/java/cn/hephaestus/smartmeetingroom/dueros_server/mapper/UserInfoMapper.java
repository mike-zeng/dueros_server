package cn.hephaestus.smartmeetingroom.dueros_server.mapper;

import cn.hephaestus.smartmeetingroom.dueros_server.model.UserInfo;
import org.apache.ibatis.annotations.*;


@Mapper
public interface UserInfoMapper {

    @Select("select * from user_info where id=#{id}")
    @Results({
            @Result(column = "phone_num",property = "phoneNum"),
            @Result(column = "image_path",property = "imagePath"),
            @Result(column = "nick_name",property = "nickName")
    })
    public UserInfo getUserInfoById(@Param("id") Integer id);
}
