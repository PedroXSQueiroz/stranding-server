package br.com.pedroxsqueiroz.stranding.factories;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Strings;

import br.com.pedroxsqueiroz.stranding.services.MediaService;
import br.com.pedroxsqueiroz.stranding.services.impl.S3MediaServiceImpl;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;

@Configuration
public class MediaServiceFactory {

	private static final Logger LOGGER = Logger.getLogger(MediaServiceFactory.class);

	@Bean(name = "s3")
	public MediaService s3(	@Value("${storageObjects.endpoint:-}") String endpoint,
							@Value("${storageObjects.bucket}") String bucketName,
							@Value("${storageObjects.partSize}") int partSize) {
		
		List<String> missingParams = Map
				.of("endpoint", endpoint, 
					"bucketName", bucketName)
				.entrySet()
				.stream()
				.filter(param -> Strings.isNullOrEmpty(param.getValue()))
				.map(param -> param.getKey())
				.collect(Collectors.toList());

		if (!missingParams.isEmpty()) {
			
			LOGGER.error(
					String.format(	"Impossible to create media service, missing %s parameters",
									String.join(", ", missingParams)
								)
						);

			return null;
		}
		
		S3ClientBuilder clientBuilder = S3Client	.builder()
													.region(Region.AWS_GLOBAL);
		
		if(!endpoint.equals("-")) 
		{
			clientBuilder = clientBuilder.endpointOverride(URI.create(endpoint));
		}
		
		S3Client client = clientBuilder.build();
		
		client.createBucket(
				CreateBucketRequest	.builder()
									.bucket(bucketName)
									.build()
							);
						
		S3MediaServiceImpl s3MediaServiceImpl = new S3MediaServiceImpl(
																client
																, bucketName
																, partSize);
		return s3MediaServiceImpl;
	}

}
