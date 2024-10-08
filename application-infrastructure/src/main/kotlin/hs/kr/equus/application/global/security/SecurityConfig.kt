package hs.kr.equus.application.global.security

import com.fasterxml.jackson.databind.ObjectMapper
import hs.kr.equus.application.global.security.jwt.UserRole
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsUtils

@Configuration
class SecurityConfig(
    private val objectMapper: ObjectMapper,
) {
    @Bean
    protected fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf()
            .disable()
            .cors()
            .and()
            .formLogin()
            .disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.authorizeRequests()
            .antMatchers("/")
            .permitAll()
            .antMatchers("/admin/**")
            .hasRole(UserRole.ADMIN.name)
            .antMatchers("/schools")
            .permitAll()
            .antMatchers("/score/**")
            .hasRole(UserRole.USER.name)
            .anyRequest()
            .authenticated()

        http
            .apply(FilterConfig(objectMapper))

        return http.build()
    }
}
