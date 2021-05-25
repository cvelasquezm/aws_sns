package com.mrc.aws.service;

import com.mrc.aws.client.SNSClient;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SNSService {

    private SNSClient snsClient;

    public SNSService(SNSClient snsClient) {
        this.snsClient = snsClient;
    }

    public void setSNSClient(SNSClient snsClient) {
        this.snsClient = snsClient;
    }

    public void publish(String message){
        this.snsClient.pubTopic(message);
    }

    public void listSNSTopics(){
        snsClient.listSNSTopics();
    }

    public void create(String topicName){
        snsClient.createSNSTopic(topicName);
    }
}
