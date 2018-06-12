package br.com.mendes.conversor.servico;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;

public class ServicoAws {
	private static String BUCKET_NAME = "conversor2";
	
	public void armazenaVideoNoAws(File arquivo) throws IOException {

		InputStream credentials = ServicoAws.class.getClassLoader().getResourceAsStream("AwsCredentials.properties");
		PropertiesCredentials awsCredentials =  new PropertiesCredentials(credentials);
        @SuppressWarnings("deprecation")
		AmazonS3 s3client = new AmazonS3Client(awsCredentials);
		s3client.setRegion(Region.getRegion(Regions.SA_EAST_1));	
		s3client.setS3ClientOptions(S3ClientOptions.builder().setPathStyleAccess(true).disableChunkedEncoding().build());				
		
        try {            
        	s3client.putObject(new PutObjectRequest(BUCKET_NAME, arquivo.getName(), arquivo).withCannedAcl(CannedAccessControlList.PublicRead));
        	System.out.println();
         } catch (AmazonServiceException ase) {
            System.out.println("Error Message:    " + ase.getMessage());
        
        } catch (AmazonClientException ace) {            
            System.out.println("Error Message: " + ace.getMessage());
        }
    }
	
	public String recuperaUrlVideoArmazenado(String nomeArquivo) throws IOException {
		InputStream credentials = ServicoAws.class.getClassLoader().getResourceAsStream("AwsCredentials.properties");
		PropertiesCredentials awsCredentials =  new PropertiesCredentials(credentials);
		@SuppressWarnings("deprecation")
		AmazonS3 s3client = new AmazonS3Client(awsCredentials);
        s3client.setRegion(Region.getRegion(Regions.SA_EAST_1));
        
        try {
	        S3Object s3object = s3client.getObject(new GetObjectRequest(BUCKET_NAME, nomeArquivo));
	        
	        return s3object.getObjectContent().getHttpRequest().getURI().toString();
        
        } catch (AmazonServiceException ase) {            
            System.out.println("Error Message:    " + ase.getMessage());
        } catch (AmazonClientException ace) {
            System.out.println("Error Message: " + ace.getMessage());
        }
		return "";
	}	
}