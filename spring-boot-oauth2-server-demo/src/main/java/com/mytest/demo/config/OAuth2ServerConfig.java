package com.mytest.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;


@Configuration
public class OAuth2ServerConfig {

    /**
     * ResourceId: We can give each of the ResourceServer instance to a resourceId.<br>
     * Here is the resourceId validation on OAuth2AuthenticationManager#authenticate:<br>
     *
     * <pre>
     * Collection<String> resourceIds = auth.getOAuth2Request().getResourceIds();
     * if (resourceId != null && resourceIds != null && !resourceIds.isEmpty() && !resourceIds.contains(resourceId)) {
     *     throw new OAuth2AccessDeniedException("Invalid token does not contain resource id (" + resourceId + ")");
     * }
     * </pre>
     */
    private static final String RESOURCE_ID = "resource";

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId(RESOURCE_ID).stateless(true);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().antMatchers("/private/**").authenticated();
        }
    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Value("${oauth.tokenTimeout}")
        private int expiration;

        @Autowired
        AuthenticationManager authenticationManager;

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

            PasswordEncoder encoder = new BCryptPasswordEncoder();
            /*
             * ClientId        Grant Type            scopes        ClientSecret    Resource
             * client_1        password            read        client1            resource
             * client_2        password            read,write    client2            resource
             * client_3        password            read,write    client3            resource_dummy
             * client_4        client_credentials
             *
             * Get token URL: http://localhost:8088/oauth/token?username=user_1&password=user1&grant_type=password&scope=read&client_id=client_1&client_secret=client1
             * Access URL: http://localhost:8088/public/1; http://localhost:8088/private/1?access_token=xxx
             */
            clients.inMemory()
                    // client_1 configuration
                    .withClient("client_1")
                    .resourceIds(RESOURCE_ID)
                    .authorizedGrantTypes("password", "refresh_token")
                    .accessTokenValiditySeconds(expiration)
                    .scopes("read")
                    .secret("{bcrypt}" + encoder.encode("client1"))
                    // client_2 configuration
                    .and()
                    .withClient("client_2")
                    .resourceIds(RESOURCE_ID)
                    .authorizedGrantTypes("password", "refresh_token")
                    .accessTokenValiditySeconds(expiration)
                    .scopes("read", "write")
                    .secret("{bcrypt}" + encoder.encode("client2"))
                    // client_3 configuration
                    .and()
                    .withClient("client_3")
                    .resourceIds("resource_dummy")
                    .authorizedGrantTypes("password", "refresh_token")
                    .accessTokenValiditySeconds(expiration)
                    .scopes("read", "write")
                    .secret("{bcrypt}" + encoder.encode("client3"))
            ;
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
            endpoints.authenticationManager(authenticationManager).allowedTokenEndpointRequestMethods(HttpMethod.GET,
                    HttpMethod.POST);
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
            oauthServer.allowFormAuthenticationForClients();
        }
    }
}
