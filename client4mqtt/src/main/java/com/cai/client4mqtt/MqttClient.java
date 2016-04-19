package com.cai.client4mqtt;

import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.hawtdispatch.Dispatch;
import org.fusesource.hawtdispatch.DispatchQueue;
import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.Callback;
import org.fusesource.mqtt.client.CallbackConnection;
import org.fusesource.mqtt.client.FutureConnection;
import org.fusesource.mqtt.client.Listener;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.Message;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
import org.fusesource.mqtt.client.Tracer;
import org.fusesource.mqtt.codec.MQTTFrame;

public class MqttClient {
	public static void main(String[] args) {
		try {
			MQTT mqtt = Config.getMQTT();

			// 使用回调式API
			final CallbackConnection callbackConnection = mqtt.callbackConnection();

			// 连接监听
			callbackConnection.listener(new Listener() {

				// 接收订阅话题发布的消息
				public void onPublish(UTF8Buffer topic, Buffer payload, Runnable onComplete) {
					System.out.println("==="+topic+"====receive msg=========" + new String(payload.toByteArray()));
					onComplete.run();
				}

				// 连接失败
				public void onFailure(Throwable value) {
					System.out.println("===========connect failure===========");
					callbackConnection.disconnect(null);
				}

				// 连接断开
				public void onDisconnected() {
					System.out.println("====mqtt disconnected=====");

				}

				// 连接成功
				public void onConnected() {
					System.out.println("====mqtt connected=====");

				}
			});

			// 连接
			callbackConnection.connect(new Callback() {

				// 连接失败
				public void onFailure(Throwable value) {
					System.out.println("============连接失败：" + value.getLocalizedMessage() + "============");
				}

				// 连接成功
				public void onSuccess(Object value) {

					// 订阅主题
					Topic[] topics = { new Topic("/foo", QoS.AT_LEAST_ONCE) };
					callbackConnection.subscribe(topics, new Callback() {

						// 订阅主题失败
						public void onFailure(Throwable value) {
							System.out.println("========订阅失败=======");
							callbackConnection.disconnect(null);
						}

						public void onSuccess(Object value) {
							System.out.println("========订阅成功=======");
							
						}
					});

					// 发布消息
					callbackConnection.publish("foo", ("Hello1 ").getBytes(), QoS.EXACTLY_ONCE, true, new Callback() {

						public void onFailure(Throwable value) {
							System.out.println("========消息发布失败=======");
							callbackConnection.disconnect(null);
						}

						public void onSuccess(Object value) {
							System.out.println("===========消息发布成功============");
						}
					});
					
				}
			});

			while (true) {

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}