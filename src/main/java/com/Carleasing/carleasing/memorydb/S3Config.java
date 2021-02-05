package com.Carleasing.carleasing.memorydb;

import com.Carleasing.carleasing.model.CommonUtils;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {


    public AmazonS3 s3clientConfig() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(CommonUtils.ACCESS_KEY, CommonUtils.SECRET_KEY);
        return AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(CommonUtils.REGION))
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();

    }
}