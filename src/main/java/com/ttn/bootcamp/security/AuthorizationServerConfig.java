package com.ttn.bootcamp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;

    private UserDetailsService userDetailsService;

    @Autowired
    public AuthorizationServerConfig(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        super();
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    @Primary
    DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(tokenStore()).userDetailsService(userDetailsService)
                .authenticationManager(authenticationManager);
    }

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }


    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("live")
                .secret(passwordEncoder.encode("bootcamp"))
                .authorizedGrantTypes("password", "refresh_token")
                .refreshTokenValiditySeconds(30 * 24 * 3600)
                .scopes("app")
                .accessTokenValiditySeconds(7 * 24 * 60);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer authorizationServerSecurityConfigurer) throws Exception {
        authorizationServerSecurityConfigurer.allowFormAuthenticationForClients();
        authorizationServerSecurityConfigurer.checkTokenAccess("permitAll()");
    }
}