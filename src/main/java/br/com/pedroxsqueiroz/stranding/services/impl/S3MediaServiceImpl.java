package br.com.pedroxsqueiroz.stranding.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jboss.logging.Logger;

import br.com.pedroxsqueiroz.stranding.services.MediaService;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CompleteMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CompletedMultipartUpload;
import software.amazon.awssdk.services.s3.model.CompletedPart;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadResponse;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.UploadPartRequest;
import software.amazon.awssdk.services.s3.model.UploadPartResponse;

public class S3MediaServiceImpl implements MediaService{

	private static final Logger LOGGER = Logger.getLogger(S3MediaServiceImpl.class);
	
	private final S3Client client;
	
	private final String bucketName;
	
	private final int partSize;
	
	public S3MediaServiceImpl(S3Client client, String bucketName, int partSize)
	{
		this.client = client;
		this.bucketName = bucketName;
		this.partSize = partSize;
	}
	
	@Override
	public InputStream get(String internalId) {
		return this.client.getObject(
										GetObjectRequest.builder()
														.bucket(bucketName)
														.key(internalId)
														.build()
									);
	}

	@Override
	public String set(InputStream inputStream) {
		
		String mediaId = UUID.randomUUID().toString();
		
		CreateMultipartUploadRequest multipartRequest = CreateMultipartUploadRequest.builder()
																					.bucket(this.bucketName)
																					.key(mediaId)
																					.build();
		
		CreateMultipartUploadResponse upload = this.client.createMultipartUpload(multipartRequest);
		
		List<CompletedPart> completedParts = new ArrayList<CompletedPart>();
		
		try 
		{
			int partNumber = 0;
			
			
			
			while(inputStream.available() > 0) 
			{
				
				byte[] partBytes = new byte[ Math.min(inputStream.available(), this.partSize) ];
				
				if( inputStream.read(partBytes) > 0 ) 
				{
					UploadPartRequest part = UploadPartRequest	.builder()
							.bucket(bucketName)
							.partNumber(partNumber)
							.uploadId(upload.uploadId())
							.key(mediaId)
							.build();
					
					UploadPartResponse uploadPart = this.client.uploadPart( part, RequestBody.fromBytes(partBytes) );
					
					completedParts.add(
							CompletedPart	.builder()
							.partNumber(partNumber)
							.eTag(uploadPart.eTag())
							.build()
							);
					
					partNumber++;					
				}
				
				
			}
		
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		
		CompletedMultipartUpload finish = CompletedMultipartUpload	.builder()
																	.parts(completedParts)
																	.build();
		
		CompleteMultipartUploadRequest finishRequest = CompleteMultipartUploadRequest	.builder()
																						.bucket(bucketName)
																						.key(mediaId)
																						.uploadId(upload.uploadId())
																						.multipartUpload(finish)
																						.build();
		
		this.client.completeMultipartUpload(finishRequest);
		
		
		return mediaId;
	}

}
