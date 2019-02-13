package cn.hephaestus.smartmeetingroom.dueros_server.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReserveInfo {
    //预定id
    private Integer reserveId;
    //开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")    //加上该注解之后，后端可以解析前端传过来的字符串
    @Future //必须是将来的日期
    private Date startTime;
    //结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Future
    private Date endTime;
    //房间id
    private Integer rid;
    //预定者
    private Integer reserveUid;
    //参会者id
    private String participantStr;
    private Integer[] participants;
    //会议标题
    private String topic;
    //会议状态
    private int flag;
    //预定的公司
    private Integer reserveOid;
    //预定的部门
    private Integer reserveDid;

}
