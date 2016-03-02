package woodysmart.hello;


import it.xpug.woodysmart.util.TestWithALiveServer;
import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class T0_HelloWorldIT extends TestWithALiveServer
{

	@Test
	public void helloWorld() throws Exception {
		get("/hello");

		assertStatus(200);
		assertThat(responseBody(), containsString("Hello, world!"));
	}

	@Test
	public void notFound() throws Exception {
		get("/notexistent");

		assertStatus(404);
		assertThat(responseBody(), containsString("Ooops! Not found!"));
	}

	@Test
	public void helloWithNameParameter() throws Exception {
		params.put("name", "Woody");
		get("/hello");

		assertStatus(200);
		assertThat(responseBody(), containsString("Hello, Woody!"));
	}

	@Test
	public void helloOnlyWorksWithGetMethod() throws Exception {
		post("/hello");

		assertStatus(405); // method not allowed
		assertThat(responseBody(), containsString("Method not allowed"));
	}
}
