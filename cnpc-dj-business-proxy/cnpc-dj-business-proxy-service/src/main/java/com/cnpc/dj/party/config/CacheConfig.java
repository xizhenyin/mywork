//package com.cnpc.dj.party.config;
//
//import java.io.IOException;
//import java.util.Properties;
//
//import javax.security.auth.callback.Callback;
//import javax.security.auth.callback.CallbackHandler;
//import javax.security.auth.callback.NameCallback;
//import javax.security.auth.callback.PasswordCallback;
//import javax.security.auth.callback.UnsupportedCallbackException;
//import javax.security.sasl.AuthorizeCallback;
//import javax.security.sasl.RealmCallback;
//
//import org.infinispan.client.hotrod.RemoteCacheManager;
//import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.cnpc.dj.cache.CacheManager;
//import com.cnpc.dj.cache.CacheManagerImpl;
//
//
////@Configuration
////@EnableConfigurationProperties(CacheConfigProps.class)
//public class CacheConfig { 
//
//	@Autowired
//    private CacheConfigProps cacheConfigProps;
//
//
//    @Bean
//    public CacheManager cacheManager() {
//        //System.out.println("######props:"+cacheConfigProps.getProperties());
//		Properties configurationPropertiesToUse = cacheConfigProps.getProperties();
//		ConfigurationBuilder clientBuilder = new ConfigurationBuilder();
//
//		clientBuilder.withProperties(configurationPropertiesToUse);
//
//		// extend functions,such as authentication...
//		if (configurationPropertiesToUse.containsKey("infinispan.extend.auth.realm")) {
//			clientBuilder
//					.security()
//					.authentication()
//					.callbackHandler(
//							new SecurityCallbackHandler(
//									configurationPropertiesToUse
//											.getProperty("infinispan.extend.auth.realm"),
//									configurationPropertiesToUse
//											.getProperty("infinispan.extend.auth.username"),
//									configurationPropertiesToUse
//											.getProperty("infinispan.extend.auth.password")));
//		}
//
//		RemoteCacheManager nativeRemoteCacheManager = new RemoteCacheManager(
//				clientBuilder.build(), true);
//		
//		//System.out.println("Finished creating new instance of RemoteCacheManager");
//		//System.out.println("RemoteCacheManager Configuration:"+nativeRemoteCacheManager.getConfiguration());
//
//		return new CacheManagerImpl(nativeRemoteCacheManager);
//    }
//
//    
//    
//	/**
//	 * SecurityCallbackHandler处理登陆认证。 扩展infinispan属性：
//	 * infinispan.extend.auth.realm=ApplicationRealm
//	 * infinispan.extend.auth.username=jdguser
//	 * infinispan.extend.auth.password=welcome-1
//	 * 
//	 * @author admin
//	 *
//	 */
//	class SecurityCallbackHandler implements CallbackHandler {
//		private String realm;
//		private String username;
//		private String password;
//
//		SecurityCallbackHandler(String realm, String username, String password) {
//			this.realm = realm;
//			this.username = username;
//			this.password = password;
//		}
//
//		@Override
//		public void handle(Callback[] callbacks) throws IOException,
//				UnsupportedCallbackException {
//			for (Callback callback : callbacks) {
//				if (callback instanceof NameCallback) {
//					NameCallback nameCallback = (NameCallback) callback;
//					nameCallback.setName(username);
//				} else if (callback instanceof PasswordCallback) {
//					PasswordCallback passwordCallback = (PasswordCallback) callback;
//					passwordCallback.setPassword(password.toCharArray());
//				} else if (callback instanceof AuthorizeCallback) {
//					AuthorizeCallback authorizeCallback = (AuthorizeCallback) callback;
//					authorizeCallback.setAuthorized(authorizeCallback
//							.getAuthenticationID().equals(
//									authorizeCallback.getAuthorizationID()));
//				} else if (callback instanceof RealmCallback) {
//					RealmCallback realmCallback = (RealmCallback) callback;
//					realmCallback.setText(realm);
//				} else {
//					throw new UnsupportedCallbackException(callback);
//				}
//
//			}
//
//		}
//	}
//}
