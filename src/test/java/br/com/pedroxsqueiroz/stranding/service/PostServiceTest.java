package br.com.pedroxsqueiroz.stranding.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy;

import br.com.pedroxsqueiroz.stranding.dtos.PostDto;
import br.com.pedroxsqueiroz.stranding.exception.MediaServiceNotAvailable;
import br.com.pedroxsqueiroz.stranding.exception.PostNotFoundException;
import br.com.pedroxsqueiroz.stranding.factories.MediaServiceFactory;
import br.com.pedroxsqueiroz.stranding.models.Post;
import br.com.pedroxsqueiroz.stranding.models.PostMedia;
import br.com.pedroxsqueiroz.stranding.models.User;
import br.com.pedroxsqueiroz.stranding.services.MediaService;
import br.com.pedroxsqueiroz.stranding.services.PostService;
import br.com.pedroxsqueiroz.stranding.services.impl.PostServiceImpl;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@Sql( 	scripts = { "/migrations/V001__starting_schema.sql"
		,"/feedInitialData/initial_posts_and_users.sql" }
		,executionPhase = ExecutionPhase.BEFORE_TEST_METHOD )
@Sql( 	scripts = { "/regrations/V001__droping_schema.sql" }
		,executionPhase = ExecutionPhase.AFTER_TEST_METHOD )
class PostServiceTest {
	
	private static final Logger LOGGER = Logger.getLogger(PostServiceTest.class.getName());
	
	public PostServiceTest( @Autowired PostService postService 
							,@Autowired MediaService mediaService) 
	{
		this.postService = postService;
		this.mediaService = mediaService;
	}
	
	public PostService postService;
	
	public MediaService mediaService;
	
	@Value("${storageObjects.accessKey}")
	private String accessKey;
	
	@Value("${storageObjects.secretKey}")
	private String secretKey;
	
	@Value("${minio.storageFolder}")
	private String storageFolder;
	
	@Value("${storageObjects.bucket}")
	private String bucket;
	
	@Value("${storageObjects.partSize}")
	private int partSize;
	
	private int STORAGE_OBJECTS_PORT = 9000;
	
	private int STORAGE_OBJECTS_AUX_PORT = 9001;
	
	private GenericContainer<?> getObjectStorage() {
		
		GenericContainer<?> objectStorage = new GenericContainer("minio/minio")
												.withExposedPorts(STORAGE_OBJECTS_PORT, STORAGE_OBJECTS_AUX_PORT, 9500)
												.withNetworkAliases("obj_storage")
												.withEnv(Map.of("MINIO_ACCESS_KEY", this.accessKey
																,"MINIO_SECRET_KEY", this.secretKey
																,"PORT","9500"))
												.withCommand("server", this.storageFolder, "--console-address", ":9500");
		
		objectStorage	.setWaitStrategy(new HttpWaitStrategy()
		                .forPort(STORAGE_OBJECTS_PORT)
		                .forPath("/minio/health/ready")
		                .withStartupTimeout(Duration.ofMinutes(2)));
	
		return objectStorage;
	}
	
	@Test
	void shouldCreateNewPostToUser() 
	{
		User dummyUser = User.builder()
			.id(UUID.fromString("c2c15f60-2f0d-11ec-8d3d-0242ac130003"))
			.build();
		
		String dummyPostContent = "O cuidado em identificar pontos críticos no novo modelo estrutural aqui preconizado oferece uma interessante oportunidade para verificação dos modos de operação convencionais.";
		
		Post post = this.postService.add(
							dummyUser
							,PostDto.builder()
								.content(dummyPostContent)
								.build()
						);
		
		assertNotNull(post.getId());
		assertEquals(dummyPostContent, post.getContent());
		assertEquals(dummyUser, post.getCreator());
		assertTrue( Instant.now().getEpochSecond() - post.getCreationDate().getEpochSecond() < 1 );
	}
	
	@Test
	void shouldAttachMediaToPost() throws MediaServiceNotAvailable, PostNotFoundException 
	{
		GenericContainer<?> objectStorage = this.getObjectStorage();
		objectStorage.start();
		
		String objectStorageHost = objectStorage.getContainerIpAddress();
		Integer objectStoragePort = objectStorage.getMappedPort(STORAGE_OBJECTS_PORT);
		String objectStorageEndpoint = "http://" +  objectStorageHost + ":" + objectStoragePort;

		MediaService mediaService = new MediaServiceFactory().s3(objectStorageEndpoint, bucket, this.partSize);
		
		( (PostServiceImpl) this.postService ).setMediaService(mediaService);
		
		
		
		List<PostMedia> medias = this.postService.attachMedias( UUID.fromString("174d6195-000b-465c-bcda-1ee5ee2da36f"), this.getDummyMedias() );
		
		final AtomicInteger mediasSuccefullyStoredCounter = new AtomicInteger();
		
		medias.forEach( media -> {			
			
			try( InputStream savedMediaReader = mediaService.get( media.getInternalId() ) ) 
			{
				byte[] savedMediaBytes = savedMediaReader.readAllBytes();
				
				assertTrue(savedMediaBytes.length > 0);
				
				try( InputStream expectedMediaReader = this.getDummyMedias().get( media.getName()) )
				{
					byte[] expectedMediaBytes = expectedMediaReader.readAllBytes();
					
					assertTrue( Arrays.equals(expectedMediaBytes, savedMediaBytes) );
					
					mediasSuccefullyStoredCounter.set(mediasSuccefullyStoredCounter.get() + 1);
				}
				
			} catch (IOException e) {
				LOGGER.severe(e.getMessage());
			}
			
		});
		
		assertEquals(medias.size(), mediasSuccefullyStoredCounter.get());
		
	}

	private Map<String, InputStream> getDummyMedias() {
		ClassLoader classLoader = this.getClass().getClassLoader();
		return Map.of(  
				"amo_paçoca.jpg", classLoader.getResourceAsStream("media/amo_paçoca.jpg")
				,"dancing_like_a_boss.gif", classLoader.getResourceAsStream("media/dancing_like_a_boss.gif")
			);
	}
}
