package com.mrc.aws.client;

import com.mrc.aws.configuration.ConfigurationProperties;
import software.amazon.awssdk.services.sns.SnsClient;

public abstract class SNSClient {

    protected String arn = ConfigurationProperties.INSTANCE.getProperty("aws.sns.topicArn");
    protected String topic = null;
    protected String endpoint = null;
    protected String region = null;
    protected String accessKeyId = null;
    protected String secretAccessKey = null;

    protected SnsClient snsClient = null;

    public abstract void pubTopic(String message);
    public abstract void listSNSTopics();
    public abstract void createSNSTopic(String topicName);
}
