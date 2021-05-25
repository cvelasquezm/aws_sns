package com.mrc.aws.client.impl;

import com.mrc.aws.client.SNSClient;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.CreateTopicRequest;
import software.amazon.awssdk.services.sns.model.CreateTopicResponse;
import software.amazon.awssdk.services.sns.model.ListTopicsRequest;
import software.amazon.awssdk.services.sns.model.ListTopicsResponse;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;

/**
 * This implementation take the credential from DefaultCredentialsProvider and Region from DefaultAwsRegionProviderChain
 */
public class ClientSnsCredentialProvider extends SNSClient {

    public ClientSnsCredentialProvider(){
        snsClient = SnsClient.create();
    }

    @Override public void pubTopic(String message) {
        try {
            PublishRequest request = PublishRequest.builder()
                .message(message)
                .topicArn(arn)
                .build();

            PublishResponse result = snsClient.publish(request);
            System.out.println(result.messageId() + " Message sent by " + this.getClass().getName() + ". Status was " + result.sdkHttpResponse().statusCode());

        } catch (SnsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    @Override public void listSNSTopics() {
        try {
            ListTopicsRequest request = ListTopicsRequest.builder().build();

            ListTopicsResponse result = snsClient.listTopics(request);
            System.out.println(
                "Status was " + result.sdkHttpResponse().statusCode() + "\n\nTopics\n\n" + result.topics());

            System.out.println(result.topics().get(0).topicArn());

        } catch (SnsException e) {

            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    @Override public void createSNSTopic(String topicName) {
        CreateTopicResponse result = null;
        try {
            CreateTopicRequest request = CreateTopicRequest.builder()
                .name(topicName)
                .build();

            result = snsClient.createTopic(request);
            //return result.topicArn();
        } catch (SnsException e) {

            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        //return "";
    }
}
