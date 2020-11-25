package com.example.demo;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class SSEController {

	
//	ExecutorService executor = Executors.newSingleThreadExecutor();
	ExecutorService executor = Executors.newCachedThreadPool();
	
	@GetMapping(value="/sse/connect", produces=MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter get(HttpServletRequest req) throws IOException, InterruptedException
	{
		System.out.println(req.getParameter("userId"));
		SseEmitter  sse = new SseEmitter();
		 executor.execute(() -> {
			 for (int i = 0; i < 10; i++) {
				 SseEmitter.SseEventBuilder sseEventBuilder = SseEmitter.event()
						 .id(""+i) // You can give nay string as id
						 .name("customEventName")
						 .data("message " + i);
//					.reconnectTime(10000); //reconnect time in millis
				 try {
					sse.send(sseEventBuilder);
					TimeUnit.SECONDS.sleep(1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		 });
		return sse;
	}
	
	
}
