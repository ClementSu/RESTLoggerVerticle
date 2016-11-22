package io.vertx.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject; 

public class LoggerVerticle extends AbstractVerticle{
	public void start() {
		Logger logger = LoggerFactory.getLogger(EventBus.class); 
		 
	    
	    EventBus eb = vertx.eventBus();
	    eb.consumer("JSONChannel", message -> {
	    	logger.info(message.body()); 
	    	vertx.setTimer(2000, reply -> { 
	    		
	    		String jsonReply = "{\"Status\":\"Successfully logged JSON\"}";

	    		message.reply(jsonReply);
	    	});
	    	
	    }); 

	}
	
}
