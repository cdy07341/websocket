package websocket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.OnMessage;
import javax.websocket.Session;

public class WebsocketWithoutAnno extends Endpoint {
	private static CopyOnWriteArraySet<WebsocketWithoutAnno> webSocketSet = new CopyOnWriteArraySet<WebsocketWithoutAnno>();
	
	private Session mySession;
	
	@Override
	public void onOpen(Session session, EndpointConfig config) {
		this.mySession = session;
        webSocketSet.add(this);     //加入set中
        mySession.addMessageHandler(new MessageHandler.Whole<String>() {  
            public void onMessage(String message) {  
//                try {  
//                    /*getBasicRemote()获得对RemoteEndpoint的一个引用。通过使用它可以马上返回一个消息给客户端 
//                     * 存在两种类型的RemoteEndpoint: RemoteEndpoint.Basic和RemoteEndpoint.Async 
//                     * RemoteEndpoint.Basic接口提供了一系列方法用于同步发送消息给客户端 
//                     * RemoteEndpoint.Async接口提供了一系列方法用于异步发送消息给客户端 
//                    */  
//                	mySession.getBasicRemote().sendText(message);  
//                } catch (IOException ioe) {  
//                    ioe.printStackTrace();  
//                }  
                
              //群发消息
                for(WebsocketWithoutAnno item: webSocketSet){
                    try {
                    	item.mySession.getBasicRemote().sendText(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                        continue;
                    }
                }
            }  
        }); 
	}
	
	
	@Override
	public void onClose(Session session, CloseReason closeReason) {
		System.out.println("close");
		super.onClose(session, closeReason);
	}
	
	@Override
	public void onError(Session session, Throwable thr) {
		System.out.println("error");
	}

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
    }
}
