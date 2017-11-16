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
        webSocketSet.add(this);     //����set��
        mySession.addMessageHandler(new MessageHandler.Whole<String>() {  
            public void onMessage(String message) {  
//                try {  
//                    /*getBasicRemote()��ö�RemoteEndpoint��һ�����á�ͨ��ʹ�����������Ϸ���һ����Ϣ���ͻ��� 
//                     * �����������͵�RemoteEndpoint: RemoteEndpoint.Basic��RemoteEndpoint.Async 
//                     * RemoteEndpoint.Basic�ӿ��ṩ��һϵ�з�������ͬ��������Ϣ���ͻ��� 
//                     * RemoteEndpoint.Async�ӿ��ṩ��һϵ�з��������첽������Ϣ���ͻ��� 
//                    */  
//                	mySession.getBasicRemote().sendText(message);  
//                } catch (IOException ioe) {  
//                    ioe.printStackTrace();  
//                }  
                
              //Ⱥ����Ϣ
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
     * �յ��ͻ�����Ϣ����õķ���
     * @param message �ͻ��˷��͹�������Ϣ
     * @param session ��ѡ�Ĳ���
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("���Կͻ��˵���Ϣ:" + message);
    }
}
