package com.mrc.aws;

import com.mrc.aws.client.SNSClient;
import com.mrc.aws.client.impl.ClientSnsCredentialProvider;
import com.mrc.aws.client.impl.ClientSnsStaticCredential;
import com.mrc.aws.service.SNSService;

public class Main {

        private static SNSClient clientSnsCredentialProvider = new ClientSnsCredentialProvider();
        private static SNSClient clientSnsStaticCredential = new ClientSnsStaticCredential();

        private static SNSService snsService;

        public static void main(String[] args) {

                final String message = "{ \"message\" : \"Hello World\" }";

                snsService = new SNSService(clientSnsCredentialProvider);
                //snsService.publish(message);
                snsService.listSNSTopics();

                snsService.setSNSClient(clientSnsStaticCredential);
                //snsService.publish(message);
                snsService.listSNSTopics();


        }
}
