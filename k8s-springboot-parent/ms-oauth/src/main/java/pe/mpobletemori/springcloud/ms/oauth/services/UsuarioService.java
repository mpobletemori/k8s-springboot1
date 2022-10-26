package pe.mpobletemori.springcloud.ms.oauth.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pe.mpobletemori.springcloud.ms.oauth.models.UsuarioBeans;

import java.util.Collections;

@Service
public class UsuarioService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioService.class);
    @Autowired
    private WebClient.Builder webClient;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       try {
           UsuarioBeans usuario = webClient
                   .build()
                   .get()
                   .uri("http://ms-usuarios/login",uri -> uri.queryParam("email", email)
                           .build())
                   .accept(MediaType.APPLICATION_JSON)
                   .retrieve()
                   .bodyToMono(UsuarioBeans.class).block();

           LOGGER.info("Usuario Login: "+usuario.getEmail());
           LOGGER.info("Usuario nombre: "+usuario.getNombre());
           LOGGER.info("Usuario PWD: "+usuario.getPassword());
           return new User(email,usuario.getPassword(),
                   true,
                   true,
                   true,
                   true,
                   Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));

       }catch (RuntimeException e){
           String error= "Error en el login, no existe el usuario "+email+" en el sistema";
           LOGGER.error(error);
           LOGGER.error("detalle error",e);
           throw new UsernameNotFoundException(error);
       }
    }
}
