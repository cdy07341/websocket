package websocket;

import java.util.HashSet;
import java.util.Set;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpointConfig;

public class WebsocketConfig implements ServerApplicationConfig {

	public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> scanned) {
		Set<ServerEndpointConfig> result = new HashSet<ServerEndpointConfig>();

		if (scanned.contains(WebsocketWithoutAnno.class)) {
			result.add(ServerEndpointConfig.Builder.create(WebsocketWithoutAnno.class, "/websocket.servlet").build());
		}

		return result;
	}

	public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> scanned) {
		// TODO Auto-generated method stub
		return null;
	}

}
