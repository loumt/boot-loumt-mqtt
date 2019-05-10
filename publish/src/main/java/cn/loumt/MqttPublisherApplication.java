/**
 * Copyright (c) www.bugull.com
 */
package cn.loumt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

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
@SpringBootApplication
public class MqttPublisherApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(MqttPublisherApplication.class);

        String[] beanDefinitionNames = ctx.getBeanDefinitionNames();

        Stream<String> beanDefinitionNameStream = Stream.of(beanDefinitionNames);

        System.out.println("Publish ................");
        beanDefinitionNameStream.filter(beanDefinitionName -> beanDefinitionName.indexOf("mqtt") != -1)
                .forEach(beanDefinitionName -> System.out.println(beanDefinitionName));
    }
}
