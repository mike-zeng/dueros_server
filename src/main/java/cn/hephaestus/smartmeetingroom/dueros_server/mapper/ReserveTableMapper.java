package cn.hephaestus.smartmeetingroom.dueros_server.mapper;

import cn.hephaestus.smartmeetingroom.dueros_server.model.ReserveInfo;
import org.apache.ibatis.annotations.*;

import java.util.Date;

@Mapper
public interface ReserveTableMapper {

    @Select("select rid from reserve_table where reserve_oid=#{oid} and ( (#{startTime}>=start_time and #{startTime}<=end_time) or (#{endTime}>=start_time and #{endTime}<=end_time) )")
    public Integer[] queryAllUnUsableMeetingroom(@Param("oid") Integer oid, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    @Insert({"insert into reserve_table(start_time,end_time,rid,topic,reserve_uid,reserve_oid,reserve_did) values(#{startTime},#{endTime},#{rid},#{topic},#{reserveUid},#{reserveOid},#{reserveDid})"})
    @Options(useGeneratedKeys = true,keyProperty = "reserveId",keyColumn = "reserve_id")
    public void addReserveInfo(ReserveInfo reserveInfo);

    @Insert("insert into meeting_participant(mid,uid) values(#{mid},#{uid})")
    public boolean addParticipant(Integer mid,Integer uid);
}
