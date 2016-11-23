package io.vertx.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;

public class RestVerticle extends AbstractVerticle {
	public void start() {

		vertx.createHttpServer().requestHandler(request -> {
			if (request.uri().equals("/logs")) {
				HttpMethod method = request.method();
				MultiMap headers = request.headers();
				String mediaType = headers.get("Content-Type");
				if (method.name().equals("POST") && mediaType.equals("application/json")) {
					request.bodyHandler(body -> {
						String requestBody = body.toString();

						EventBus eb = vertx.eventBus();

						eb.send("JSONChannel", requestBody, reply -> {
							if (reply.succeeded()) {

								HttpServerResponse response = request.response();
								response.putHeader("content-type", "application/json");
								response.setStatusCode(200);
								request.response().end(reply.result().body().toString());
							} else {
								request.response().end("Failed to log JSON");
							}

						});
					});
				} else {
					request.response().end("Please POST JSON to /log endpoint");
				}

			} else {
				request.response().end("Verticle App");
			}

		}).listen(8080);

	}

}
