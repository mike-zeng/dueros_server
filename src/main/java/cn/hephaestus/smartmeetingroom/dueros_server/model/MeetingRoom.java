package cn.hephaestus.smartmeetingroom.dueros_server.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class MeetingRoom {
    @NotNull
    private int roomId;

    private int oid;//组织id

    private String roomName;

    private int capacity;

    private String image;

    private String address;
    //mac地址,使用md5加密
//    @NotNull
//    @Length(min = 32,max = 32)
    private String macAddress;
    //true表示可用，false表示不可用
    private boolean available;
    //设备注册时的时间
    private Timestamp registerTime;
}
