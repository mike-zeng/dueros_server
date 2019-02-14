package cn.hephaestus.smartmeetingroom.dueros_server.controller;


import cn.hephaestus.smartmeetingroom.dueros_server.bot.AmeetingBot;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class AmeetingController {


    @RequestMapping(value = "/ameeting",method = RequestMethod.HEAD)
    public void doHead(HttpServletResponse response){
        response.setStatus(204);
    }
    @RequestMapping(value = "/ameeting",method = RequestMethod.POST)
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(204);
        AmeetingBot bot;
        bot=new AmeetingBot(request);
        try {
            String responseJson=bot.run();
            response.setCharacterEncoding("UTF-8");
            response.getWriter().append(responseJson);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
