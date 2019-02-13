package cn.hephaestus.smartmeetingroom.dueros_server.service.impl;

import cn.hephaestus.smartmeetingroom.dueros_server.mapper.ReserveTableMapper;
import cn.hephaestus.smartmeetingroom.dueros_server.model.ReserveInfo;
import cn.hephaestus.smartmeetingroom.dueros_server.service.ReserveInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReserveInfoServiceImpl implements ReserveInfoService {

    @Autowired
    private ReserveTableMapper reserveTableMapper;

    @Override
    public void addReserveInfo(ReserveInfo reserveInfo) {
        reserveTableMapper.addReserveInfo(reserveInfo);
        return;
    }

    @Override
    public boolean addParticipant(Integer mid, Integer uid) {
        return reserveTableMapper.addParticipant(mid,uid);
    }

}
