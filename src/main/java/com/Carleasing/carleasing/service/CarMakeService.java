package com.Carleasing.carleasing.service;


import com.Carleasing.carleasing.exception.VehicleException;
import com.Carleasing.carleasing.model.CarMake;
import com.Carleasing.carleasing.model.CommonUtils;
import com.Carleasing.carleasing.repository.CarMakeRepository;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class CarMakeService implements CarMakeRepository {

    private Logger logger = LoggerFactory.getLogger(CarMakeService.class);


    @Autowired
    DynamoDBMapper mapper;

    @Autowired
    AmazonS3 s3client;


    @Override
    public CarMake save(MultipartFile multipartFile, CarMake carMake){
        try{
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            String fileUrl = CommonUtils.S3SERVICE_ENDPOINT + "/" + CommonUtils.BUCKET_NAME + "/" + fileName;

           // S3Link s3Link = mapper.createS3Link(CommonUtils.REGION,CommonUtils.BUCKET_NAME, fileUrl);

            /*String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(s3Link.toString())
                    .toUriString();*/
            carMake.setCarImageUrl(fileUrl);
            mapper.save(carMake);
            s3client.putObject(
                    new PutObjectRequest(CommonUtils.BUCKET_NAME, fileName , file));
        }/*catch (Exception e) {
			e.printStackTrace();
		}*/
        catch (AmazonServiceException ase) {
            logger.info("Caught an AmazonServiceException from GET requests, rejected reasons:");
            logger.info("Error Message:    " + ase.getMessage());
            logger.info("HTTP Status Code: " + ase.getStatusCode());
            logger.info("AWS Error Code:   " + ase.getErrorCode());
            logger.info("Error Type:       " + ase.getErrorType());
            logger.info("Request ID:       " + ase.getRequestId());


        } catch (AmazonClientException ace) {
            logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
        } catch (IOException ioe) {
            logger.info("IOE Error Message: " + ioe.getMessage());

        }
        return  carMake;

    }


    public CarMake findCarMake(String makeId) {
        try{

            return mapper.load(CarMake.class, makeId);

        } catch (Exception e){
            throw new VehicleException("This customer '" + makeId + " does not exist");
        }
    }

    @Override
    public String deleteCarMake(String makeId) {
        try{
            CarMake make = mapper.load(CarMake.class, makeId);
            mapper.delete(make);
            String fileName = make.getCarImageUrl().substring(make.getCarImageUrl().lastIndexOf("/") + 1);
            s3client.deleteObject(new DeleteObjectRequest(CommonUtils.BUCKET_NAME + "/" ,fileName) );
            return "CarMake deleted !!";
        }
        catch(Exception ex){
            throw new VehicleException("This Vehicle Cannot be deleted");
        }
    }

    @Override
    public String updateCarMake(MultipartFile multipartFile, CarMake make) {
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            String fileUrl = CommonUtils.S3SERVICE_ENDPOINT + "/" + CommonUtils.BUCKET_NAME + "/" + fileName;

            // S3Link s3Link = mapper.createS3Link(CommonUtils.REGION,CommonUtils.BUCKET_NAME, fileUrl);

            /*String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(s3Link.toString())
                    .toUriString();*/
            make.setCarImageUrl(fileUrl);
            mapper.save(make, buildExpression(make));
            s3client.putObject(
                    new PutObjectRequest(CommonUtils.BUCKET_NAME, fileName , file));
            return "record Updated";
        }
        catch(Exception e){
            throw new VehicleException("This user '" + make.getMake() + "' already exist");
        }
    }

    private DynamoDBSaveExpression buildExpression(CarMake carMake){

        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedMap=new HashMap<>();
        expectedMap.put("makeId", new ExpectedAttributeValue((new AttributeValue().withS(carMake.getMakeId()))));
        dynamoDBSaveExpression.setExpected(expectedMap);
        return dynamoDBSaveExpression;
    }


    @Override
    public Iterable<CarMake> findAll() {
        return mapper.scan(CarMake.class, new DynamoDBScanExpression());
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

}
