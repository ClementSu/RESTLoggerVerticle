package io.vertx.example;

import io.vertx.core.AbstractVerticle;

public class StarterVerticle extends AbstractVerticle{

	// Called when verticle is deployed
	public void start() {
	    // Now deploy some other verticle:
	    vertx.deployVerticle("io.vertx.example.LoggerVerticle");
	    vertx.deployVerticle("io.vertx.example.RestVerticle");
	}

	  // Optional - called when verticle is undeployed
	  public void stop() {
	  }
	  
}
