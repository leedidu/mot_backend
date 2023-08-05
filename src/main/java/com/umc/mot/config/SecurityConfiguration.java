package com.umc.mot.config;

import com.umc.mot.oauth2.filter.JwtAuthenticationFilter;
import com.umc.mot.oauth2.handler.*;
import com.umc.mot.oauth2.utils.CustomAuthorityUtils;
import com.umc.mot.sellMember.service.SellMemberService;
import com.umc.mot.token.service.TokenService;
import com.umc.mot.utils.CustomCookie;
import com.umc.mot.oauth2.filter.JwtVerificationFilter;
import com.umc.mot.oauth2.jwt.JwtTokenizer;
import com.umc.mot.purchaseMember.service.PurchaseMemberService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

/**
 * JWT 검증 기능 추가
 */
@Configuration
@AllArgsConstructor
@EnableWebSecurity(debug = true)
public class SecurityConfiguration {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final TokenService tokenService;
    private final SellMemberService sellMemberService;
    private final PurchaseMemberService purchaseMemberService;
    private final CustomCookie cookie;
    private final OAuth2MemberSuccessHandler oAuth2MemberSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin()
                .and()
                .csrf().disable()
//                .cors(withDefaults())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
                .accessDeniedHandler(new MemberAccessDeniedHandler())
                .and()
                .apply(new CustomFilterConfigurer())
                .and()
                .logout()
                .logoutUrl("/logout") // 로그아웃 처리 URL(기본값)
                .invalidateHttpSession(true) // 로그아웃 성공 시 세션 제거
                .clearAuthentication(true) // 로그아웃 시 권한 제거
                .permitAll() // 모두 허용
                .logoutSuccessHandler(new MemberLogoutSuccessHandler(tokenService)) // 로그아웃 성공 후 핸들러
                .and()
                .authorizeHttpRequests(authorize -> authorize // url authorization 전체 추가
//                                .antMatchers(HttpMethod.POST, "/*/coffees").hasRole("ADMIN")
//                                .antMatchers(HttpMethod.GET, "/*/coffees/**").hasAnyRole("USER", "ADMIN")
//                                .antMatchers(HttpMethod.GET, "/*/coffees").permitAll()
                                .anyRequest().permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(oAuth2MemberSuccessHandler)
                );

        return http.build();
    }

//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("*"));
//        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PATCH", "DELETE"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

    // 추가
    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            // 로그인
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer, tokenService);
            jwtAuthenticationFilter.setFilterProcessesUrl("/login");          // login url

            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils, tokenService, sellMemberService, purchaseMemberService, cookie); // google OAuth2

            builder
                    .addFilter(jwtAuthenticationFilter) // 로그인
                    .addFilterAfter(jwtVerificationFilter, OAuth2LoginAuthenticationFilter.class); // google OAuth2
        }
    }
}
