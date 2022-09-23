package pe.mpobletemori.springcloud.ms.usuarios;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                //Configurar resource Server
                .antMatchers("/authorized").permitAll()
                //configurar acceso por scope
                .antMatchers(HttpMethod.GET,"/","/{id}").hasAnyAuthority("SCOPE_read","SCOPE_write")
                .antMatchers(HttpMethod.POST,"/").hasAuthority("SCOPE_write")
                .antMatchers(HttpMethod.PUT,"/{id}").hasAuthority("SCOPE_write")
                .antMatchers(HttpMethod.DELETE,"/{id}").hasAuthority("SCOPE_write")
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .oauth2Login(oauth2Login->oauth2Login.loginPage("/oauth2/authorization/ms-usuarios-client"))
                .oauth2Client(Customizer.withDefaults())
                .oauth2ResourceServer().jwt();

        return http.build();
    }


}
