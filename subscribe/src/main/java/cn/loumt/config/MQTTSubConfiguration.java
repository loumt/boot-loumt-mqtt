/**
 * Copyright (c) www.bugull.com
 */
package cn.loumt.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.*;

import java.util.stream.Stream;

/**
 * USED TO:
 * Log File:
 *
 * @author cn.loumt(cn.loumt@sanlogic.com)
 * @project mqtt-demo
 * @package cn.cn.loumt
 * @date 2019/5/9
 */
@Slf4j
@Configuration
@IntegrationComponentScan
public class MQTTSubConfiguration {
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

    @Value("${spring.mqtt.default.qos}")
    private int defaultQoS;


    private static final Integer MAX_IN_FLIGHT = 100000000;


    @Bean
    public MqttConnectOptions getMqttConnectionOptions() {
        try {
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
        } catch (Exception e) {
            System.out.println("=================================");
            System.out.println(e);
            System.out.println("=================================");
        }
        return null;
    }

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getMqttConnectionOptions());
        return factory;
    }


    /*********************
     * 接收端
     *******************/

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound() {
        String[] inboundTopics = topics.split(",");
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(clientId, mqttClientFactory(), inboundTopics);
        adapter.setCompletionTimeout(completionTimeout);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(defaultQoS);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }


    @Bean
    //ServiceActivator注解表明当前方法用于处理MQTT消息，inputChannel参数指定了用于接收消息信息的channel。
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                System.out.println("Receive Message : " + message.getPayload().toString());

                System.out.println("==================Header Start================");
                MessageHeaders headers = message.getHeaders();
//                Object replyChannel = headers.getReplyChannel();
                headers.keySet().stream().forEach(key -> {
                    Object o = headers.get(key);
                    System.out.println(key + " : " + o);
                });
                System.out.println("==================Header End================");

            }
        };
    }
}
