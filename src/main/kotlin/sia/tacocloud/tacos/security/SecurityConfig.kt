package sia.tacocloud.tacos.security

import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.servlet.handler.HandlerMappingIntrospector

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder? = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    fun securityFilterChain(http: HttpSecurity, inspector: HandlerMappingIntrospector): SecurityFilterChain {
        val matcherBuilder = MvcRequestMatcher.Builder(inspector)

        return http
            .authorizeHttpRequests {
                it.requestMatchers(
                    matcherBuilder.pattern("/design"),
                    matcherBuilder.pattern("/orders")
                ).hasRole("USER")
                    .requestMatchers(
                        matcherBuilder.pattern("/"),
                        matcherBuilder.pattern("/**"),
                        AntPathRequestMatcher("/h2-console/**")
                    ).permitAll()
            }
            .formLogin {
                it.loginPage("/login")
                    .defaultSuccessUrl("/design", true)
            }
            .logout {
                it.logoutSuccessUrl("/")
            }
            .csrf { it.ignoringRequestMatchers(AntPathRequestMatcher("/h2-console/**")) }
            .headers { it.frameOptions {option -> option.sameOrigin()} }
            .build()
    }

}