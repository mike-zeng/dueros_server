package cn.hephaestus.smartmeetingroom.dueros_server.service;

import cn.hephaestus.smartmeetingroom.dueros_server.model.ReserveInfo;


public interface ReserveInfoService {

    public void addReserveInfo(ReserveInfo reserveInfo);

    public boolean addParticipant(Integer mid,Integer uid);
}
