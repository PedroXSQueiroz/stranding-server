package br.com.pedroxsqueiroz.stranding;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql({ 		"/migrations/V001__starting_schema.sql"
			,"/feedInitialData/initial_posts_and_users.sql" })
class StrandingApplicationTests {

	@Test
	void contextLoads() {
	}

}
