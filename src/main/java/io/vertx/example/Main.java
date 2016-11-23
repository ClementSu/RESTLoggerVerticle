package io.vertx.example;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.ext.unit.TestOptions;
import io.vertx.ext.unit.TestSuite;
import io.vertx.ext.unit.report.ReportOptions;

public class Main {

	public static void main(String[] args) {

		Vertx vertx = Vertx.vertx();

		vertx.deployVerticle("io.vertx.example.StarterVerticle");

		TestSuite suite = TestSuite.create("the_test_suite");
		suite.test("TestPostStatus", context -> {
			HttpClientOptions options = new HttpClientOptions().setDefaultPort(8080);
			HttpClient client = vertx.createHttpClient(options);

			String body = "{\"SomeJSONHeader\":\"Sample JSON\"}";

			client.post("/logs", response -> {
				System.out.println("Received response");
				context.assertEquals(200, response.statusCode());
			}).putHeader("content-length", "1000").putHeader("content-type", "application/json").write(body).end();

			client.close();

		});

		suite.test("TestPostJSON", context -> {
			HttpClientOptions options = new HttpClientOptions().setDefaultPort(8080);
			HttpClient client = vertx.createHttpClient(options);

			String body = "{\"SomeJSONHeader\":\"Sample JSON\"}";

			client.post("/logs", response -> {
				System.out.println("Received response");
				response.bodyHandler(msg -> {
					context.assertEquals("{\"Status\":\"Successfully logged JSON\"}", msg.toString());
				});

			}).putHeader("content-length", "1000").putHeader("content-type", "application/json").write(body).end();

			client.close();
		});

		suite.run(new TestOptions().addReporter(new ReportOptions().setTo("console")));

	}

}
