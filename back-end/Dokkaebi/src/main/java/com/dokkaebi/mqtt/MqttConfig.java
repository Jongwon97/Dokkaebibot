package com.dokkaebi.mqtt;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
//@RequiredArgsConstructor
@EnableIntegration
public class MqttConfig {
  private final MqttService mqttService;
  @Autowired
  public MqttConfig(@Lazy MqttService mqttService) {
    this.mqttService = mqttService;
  }

  @Value("${mqtt.url}")
  String mqttURL;

  @Bean
  public MessageChannel mqttOutboundChannel() {
    return new DirectChannel();
  }
  @Bean
  public MqttPahoMessageDrivenChannelAdapter mqttPahoMessageDrivenChannelAdapter() {
    MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(mqttURL,
        "dokkaebiSubscriber", "test");
    adapter.setConverter(new DefaultPahoMessageConverter());
    return adapter;
  }

  @Bean
  public MessageHandler mqttInboundMessageHandler() {
    return message -> {
      mqttService.handleMessage(message);
    };
  }

  @Bean
  public IntegrationFlow mqttInbound() {
    return IntegrationFlow.from(mqttPahoMessageDrivenChannelAdapter())
        .handle(mqttInboundMessageHandler())
        .get();
  }
  @Bean
  public IntegrationFlow mqttOutboundFlow() {
    return IntegrationFlow.from(mqttOutboundChannel())
        .handle(new MqttPahoMessageHandler(mqttURL, "dokkaebiPublisher"))
        .get();
  }
}
