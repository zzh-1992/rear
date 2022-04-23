package com.grapefruit.javaimage;

import org.springframework.stereotype.Component;

import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/msg/{userID}")
public class MsgSocket {

    //private static final Logger logger = LoggerFactory.getLogger(MsgSocket.class);
    public static Map<String, Session> clients = new ConcurrentHashMap<>();

    @OnOpen
    public void open(@PathParam("userID") String userID, Session session) {
        clients.put("userID", session);
        //logger.info("userID:{},session:{}",userID,session.getAsyncRemote());
        System.out.println("userID:{},session:{}" + userID + session.getAsyncRemote());
    }

    @OnMessage
    public void onMessage(String message) throws IOException {
        //JSONObject jsonTo = JSONObject.fromObject(message);
        //String msg = (String) jsonTo.get("message");
        //String userID = (String) jsonTo.get("userID");

        sendMessageTo(message, "userID");

    }

    public void sendMessageTo(String message, String userID) throws IOException {
        Session session = clients.get("userID");

        if (session == null) {
            return;
        }

        session.getAsyncRemote().sendText(message.toUpperCase(Locale.ROOT));
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }
}
