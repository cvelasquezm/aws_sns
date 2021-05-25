package com.mrc.aws.client.impl;

import com.mrc.aws.client.SNSClient;
import com.mrc.aws.configuration.ConfigurationProperties;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.auth.credentials.SystemPropertyCredentialsProvider;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.ListTopicsResponse;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * This class work with StaticCredentialsProvider and take the credentials from a property file.
 */
public class ClientSnsStaticCredential extends SNSClient {

    public ClientSnsStaticCredential() {
        loadCredentials();

        //Some Client Configuration
        ClientOverrideConfiguration clientOverrideConfiguration = ClientOverrideConfiguration.builder()
            .apiCallTimeout(Duration.of(60, ChronoUnit.SECONDS))
            .build();

        //Working with specific credentials
        AwsBasicCredentials basicCredentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);

        try {
            snsClient = SnsClient.builder()
                .endpointOverride(new URI(endpoint))
                .region(Region.of(region))
                .overrideConfiguration(clientOverrideConfiguration)
                .credentialsProvider(StaticCredentialsProvider.create(basicCredentials))
                .build();
        } catch (URISyntaxException e) {
            System.out.println("The Endpoint is invalid");
        } catch (Exception e) {
            System.out.println("There was an error trying to create the SNS Client");
        }
    }

    private void loadCredentials() {
        topic = ConfigurationProperties.INSTANCE.getProperty("aws.sns.topic");
        endpoint = ConfigurationProperties.INSTANCE.getProperty("aws.sns.endpoint");
        region = ConfigurationProperties.INSTANCE.getProperty("aws.sns.region");
        accessKeyId = ConfigurationProperties.INSTANCE.getProperty("aws.sns.accessKeyId");
        secretAccessKey = ConfigurationProperties.INSTANCE.getProperty("aws.sns.secretAccessKey");
    }

    @Override public void pubTopic(String message) {
        PublishRequest request = PublishRequest.builder()
            .message(message)
            .topicArn(arn)
            .build();

        PublishResponse result = snsClient.publish(request);
        System.out.println(result.messageId() + " Message sent by " + this.getClass().getName() + ". Status was " + result.sdkHttpResponse().statusCode());
    }

    @Override public void listSNSTopics() {
        final ListTopicsResponse listTopicsResponse = snsClient.listTopics();
        listTopicsResponse.topics().forEach(topic -> {
            System.out.println(topic);
        });

    }

    @Override public void createSNSTopic(String topicName) {

    }
}
