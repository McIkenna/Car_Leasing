package com.Carleasing.carleasing.memorydb;


import com.Carleasing.carleasing.model.CommonUtils;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDBConfig {



/*
    @Value("${aws.access_key_id}")
    private String awsId;

    @Value("${aws.secret_access_key}")
    private String awsKey;

    @Value("${aws.region}")
    private String region;

    @Value("${aws.dynamodb_endpoint}")
    private String dynamoEndpoint;
*/

   @Bean
    public DynamoDBMapper mapper(){
        return new DynamoDBMapper(amazonDynamoDBConfig());
    }
    private AmazonDynamoDB amazonDynamoDBConfig(){

        AWSCredentials dynamoCredentials =  new BasicAWSCredentials(CommonUtils.ACCESS_KEY,CommonUtils.SECRET_KEY);
        return AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(CommonUtils.SERVICE_ENDPOINT, CommonUtils.REGION))
                .withCredentials(new AWSStaticCredentialsProvider(dynamoCredentials))
                .build();
    }

}
