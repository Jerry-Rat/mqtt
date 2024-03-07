package cn.nt.mqtt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MqttApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(MqttApplication.class, args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
