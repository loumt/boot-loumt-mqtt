/**
 * Copyright (c) www.bugull.com
 */
package cn.loumt.controller;

import cn.loumt.service.MqttGateway;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * USED TO:
 * Log File:
 *
 * @author cn.loumt(cn.loumt@sanlogic.com)
 * @project mqtt-demo
 * @package cn.cn.loumt
 * @date 2019/5/9
 */
@RestController
@Slf4j
@RequestMapping("/mqtt")
public class MqttController {

    @Autowired
    private MqttGateway mqttGateway;


    @GetMapping("/test")
    public String test(){
        return "test..........";
    }

    @GetMapping("/send")
    public JSONObject sendMqtt(@RequestParam String sendData){
        JSONObject resultJson = new JSONObject();
        mqttGateway.sendToMqtt(sendData);
        resultJson.put("send",sendData);
        resultJson.put("success",true);
        return resultJson;
    }

    @PostMapping("/save")
    public JSONObject saveMqttMsg(@RequestBody String sendData){
        JSONObject resultJson = new JSONObject();
        System.out.println("sendData: " + sendData);
        resultJson.put("save",sendData);
        resultJson.put("success",true);
        return resultJson;
    }

}
