package cn.hephaestus.smartmeetingroom.dueros_server.bot;

import cn.hephaestus.smartmeetingroom.dueros_server.model.MeetingRoom;
import cn.hephaestus.smartmeetingroom.dueros_server.model.ReserveInfo;
import cn.hephaestus.smartmeetingroom.dueros_server.model.User;
import cn.hephaestus.smartmeetingroom.dueros_server.model.UserInfo;
import cn.hephaestus.smartmeetingroom.dueros_server.service.*;
import cn.hephaestus.smartmeetingroom.dueros_server.utils.SpringUtil;
import com.baidu.dueros.bot.BaseBot;

import com.baidu.dueros.data.request.IntentRequest;
import com.baidu.dueros.data.request.LaunchRequest;
import com.baidu.dueros.data.request.SessionEndedRequest;
import com.baidu.dueros.data.response.OutputSpeech;
import com.baidu.dueros.data.response.card.TextCard;
import com.baidu.dueros.model.Response;
import com.fasterxml.jackson.databind.JsonMappingException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AmeetingBot extends BaseBot {

    HttpServletRequest request=null;
    public AmeetingBot(HttpServletRequest request) throws IOException{
        super(request);
        this.request=request;
    }


    @Override
    protected Response onInent(IntentRequest intentRequest) {
        User user=null;
        String intentName=intentRequest.getIntentName();

        //必须先获取用户信息
        UserService userService=SpringUtil.getBean(UserService.class);
        user=userService.getUser(getDeviceId());
        UserInfo userInfo=userService.getUserInfo(user.getId());

        //绑定用户账号
        if (intentName.equals("bindingAccount")){
            if (user!=null){
                return getSimpleResponse("设备已绑定，您可以开始使用功能或者前往App解除绑定");
            }
            //获取用户手机号
            RedisService redisService=SpringUtil.getBean(RedisService.class);
            String phoneNumber= getSlot("phoneNumber");
            if (phoneNumber==null){
                ask("phoneNumber");
                return getSimpleResponse("请告诉我你想绑定的手机号");
            }

            //获取dueros设备用户身份信息
            String deviceId=getDeviceId();

            //获取验证码并加入redis缓存，过期时间为5分钟
            Integer code=(int)((Math.random()*9+1)*10000);
            redisService.set("dueros:"+phoneNumber+":"+code,deviceId,300);

            //发放验证码
            return getSimpleResponse("你的验证码为:"+code+",五分钟内有效，请尽快在APP中填写");
        }

        if (user==null){
            return getSimpleResponse("请先在APP内绑定设备才能享受服务");
        }

        //查询会议信息
        if (intentName.equals("inquireMeeting")){

            String dateStr=getSlot("date");
            if (dateStr==null){
                ask("date");
                return getSimpleResponse("请告诉我你想查询哪一天的会议");
            }
            //构造日期
            SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            Date date=null;
            try {
                date=sDateFormat.parse(dateStr);
            }catch (Exception e){
                e.printStackTrace();
            }

            //查询会议
            MeetingInfoService meetingInfoService=SpringUtil.getBean(MeetingInfoService.class);
            ReserveInfo[] reserveInfo=meetingInfoService.getAllReserveInfoByUid(user.getId(),date);

            StringBuilder ret=new StringBuilder();
            if (reserveInfo.length==0){
                return getSimpleResponse("你当天没有会议");
            }

            sDateFormat=new SimpleDateFormat("HH:mm");

            ret.append("会议助手提醒你,你在");
            for (ReserveInfo r:reserveInfo){
                ret.append(sDateFormat.format(r.getStartTime())+"到"+sDateFormat.format(r.getEndTime())+"有一个主题为"+r.getTopic()+"的会议,");
            }

            return getSimpleResponse(ret.toString());
        }

        //预定会议室
        if (intentName.equals("bookMeeeting")){
            String dateStr=getSlot("date");
            String startTimeStr=getSlot("start");
            String endTimeStr=getSlot("end");
            String countStr=getSlot("count");
            String topic=getSlot("topic");

            if (dateStr==null){
                ask("date");
                return getSimpleResponse("请问你想预定哪一天的会议");
            }
            if (startTimeStr==null){
                ask("start");
                return getSimpleResponse("请告诉我会议开始的时间");
            }
            if (endTimeStr==null){
                ask("end");
                return getSimpleResponse("请告诉我会议结束的时间");
            }
            if (countStr==null){
                ask("count");
                return getSimpleResponse("请告诉大概有多少人参加会议，以方便我为你找到容量足够的会议室");
            }
            if (topic==null){
                ask("topic");
                return getSimpleResponse("请告诉我会议的主题");
            }

            Integer count=null;
            try {
                count=Integer.parseInt(countStr);
            }catch (Exception e){
                ask("count");
                return getSimpleResponse("请告诉大概有多少人参加会议，以方便我为你找到容量足够的会议室");
            }


            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date start=simpleDateFormat.parse(dateStr+" "+startTimeStr);
                Date end=simpleDateFormat.parse(dateStr+" "+endTimeStr);
                MeetingRoomService meetingRoomService=SpringUtil.getBean(MeetingRoomService.class);
                MeetingRoom[] meetingRooms=meetingRoomService.getAllUsableMeetingRooms(userInfo.getOid(),start,end,count);
                if (meetingRooms==null||meetingRooms.length==0){
                    return getSimpleResponse("当前时间段内没有合适的会议室，你可以更换时间段");
                }

                Integer mid=meetingRooms[(int)((Math.random()*9+1)*10000)%meetingRooms.length].getRoomId();

                ReserveInfo reserveInfo=new ReserveInfo();
                reserveInfo.setRid(mid);
                reserveInfo.setStartTime(start);
                reserveInfo.setEndTime(end);
                reserveInfo.setReserveUid(user.getId());
                reserveInfo.setReserveDid(userInfo.getDid());
                reserveInfo.setReserveOid(userInfo.getOid());
                reserveInfo.setTopic(topic);
                reserveInfo.setReserveId(null);

                ReserveInfoService reserveInfoService=SpringUtil.getBean(ReserveInfoService.class);
                reserveInfoService.addReserveInfo(reserveInfo);
                if (reserveInfo.getReserveId()!=null){
                    reserveInfoService.addParticipant(reserveInfo.getReserveId(),user.getId());
                    return getSimpleResponse("预定成功，你可以在App中管理该会议");
                }else {
                    return getSimpleResponse("预定失败，请稍后再试");
                }
            }catch (Exception e){
                e.printStackTrace();
                return getSimpleResponse("预定失败，请稍后再试");
            }
        }

        return getSimpleResponse("很抱歉，我无法为你解答这个问题");
    }

    @Override
    protected Response onLaunch(LaunchRequest launchRequest) {
        return getSimpleResponse("你好，会议助手很高兴为你服务");
    }

    @Override
    protected Response onSessionEnded(SessionEndedRequest sessionEndedRequest) {
        return getSimpleResponse("感谢你的使用");
    }

    public Response getSimpleResponse(String msg){
//        TextCard card=new TextCard();
//        card.setContent(msg);
        return new Response(new OutputSpeech(OutputSpeech.SpeechType.PlainText,msg));
    }

}
