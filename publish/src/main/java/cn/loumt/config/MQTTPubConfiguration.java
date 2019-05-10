/**
 * Copyright (c) www.bugull.com
 */
package cn.loumt.config;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * USED TO:
 * Log File:
 *
 * @author cn.loumt(cn.loumt@sanlogic.com)
 * @project mqtt-demo
 * @package cn.cn.loumt
 * @date 2019/5/9
 */
@Configuration
@IntegrationComponentScan
public class MQTTConfiguration {

    @Value("${spring.mqtt.username}")
    private String username;

    @Value("${spring.mqtt.password}")
    private String password;

    @Value("${spring.mqtt.topics}")
    private String topics;

    @Value("${spring.mqtt.client.id}")
    private String clientId;

    @Value("${spring.mqtt.url}")
    private String hostUrl;

    @Value("${spring.mqtt.completionTimeout}")
    private int completionTimeout;

    private static final Integer MAX_IN_FLIGHT = 100000000;


    @Bean
    public MqttConnectOptions getMqttConnectionOptions() {
        MqttConnectOptions options = new MqttConnectOptions();

        if (StringUtils.isEmpty(username)) {
            options.setUserName(null);
        } else {
            options.setUserName(username);
        }

        if (StringUtils.isEmpty(password)) {
            options.setUserName(null);
        } else {
            options.setUserName(password);
        }

        options.setKeepAliveInterval(2);
        options.setServerURIs(new String[]{hostUrl});
        options.setConnectionTimeout(completionTimeout);
        options.setMaxInflight(MAX_IN_FLIGHT);
        return options;
    }

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getMqttConnectionOptions());
        return factory;
    }


    /*********************
     * 发送端
     *******************/
    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }


    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        System.out.println("ClientId:" + clientId);
        //clientId使用随机数产生
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(clientId, mqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(topics);
        //信息是否保留,若为true,该信息保存在服务器,每个订阅该频道的客户端都会收到该信息
        messageHandler.setDefaultRetained(false);
        return messageHandler;
    }

}
