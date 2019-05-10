/**
 * Copyright (c) www.bugull.com
 */
package cn.loumt.controller;

import cn.loumt.service.MqttGateway;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
