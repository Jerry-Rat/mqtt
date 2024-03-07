package cn.nt.mqtt.utils;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * mqtt工具类
 */
public class MqttUtil {
    /**
     * MQTT发布者
     */
    public static void mqttPublisher() {
        // MQTT 服务器信息
        //指定 MQTT 服务器的地址和端口
        String broker = "tcp://192.168.0.180:1883";
        //为 MQTT 客户端设置一个唯一的标识符。客户端ID在连接到服务器时应该是唯一的。
        String clientId = "Publisher";
        //定义要发布消息的 MQTT 主题。
        String topic = "test/topic";

        // 创建 MQTT 客户端实例
        try (MqttClient client = new MqttClient(broker, clientId)) {

            // 设置连接选项
            MqttConnectOptions options = new MqttConnectOptions();
            //设置会话为“清除”，表示不复用任何先前的会话信息。
            options.setCleanSession(true);

            // 连接到 MQTT 服务器
            client.connect(options);

            // 要发布的消息
            String payload = "你好，MQTT！";
            MqttMessage message = new MqttMessage(payload.getBytes());
            message.setQos(0);

            // 将消息发布到指定主题
            client.publish(topic, message);

            System.out.println("消息成功发布到主题：" + topic);

            // 从 MQTT 服务器断开连接
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * MQTT订阅者
     */
    public static void mqttSubscriber() {
        // MQTT 服务器信息
        String broker = "tcp://192.168.0.180:1883";
        String clientId = "Subscriber";
        String topic = "test/topic";

        try {
            // 创建 MQTT 客户端实例
            MqttClient client = new MqttClient(broker, clientId);

            // 设置连接选项
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);

            // 连接到 MQTT 服务器
            client.connect(options);

            if (client.isConnected()) {
                System.out.println("成功连接到 MQTT 服务器");
            } else {
                System.out.println("连接失败");
            }

            // 订阅指定主题并定义收到消息时的回调
            //订阅指定主题，并使用 lambda 表达式定义一个回调。当订阅的主题接收到消息时，将执行此回调。
            client.subscribe(topic, 0, (topic1, message) -> {
                String payload = new String(message.getPayload());
                System.out.println("收到消息：" + payload);
            });

            // 保持应用程序运行以接收消息
            Thread.sleep(5000); // 根据需要调整休眠时间

            // 从 MQTT 服务器断开连接
            client.disconnect();
        } catch (MqttException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
