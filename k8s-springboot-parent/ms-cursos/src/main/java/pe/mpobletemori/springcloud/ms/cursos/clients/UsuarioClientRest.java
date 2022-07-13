package pe.mpobletemori.springcloud.ms.cursos.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pe.mpobletemori.springcloud.ms.cursos.model.UsuarioBeans;


@FeignClient(name = "ms-usuarios",url = "localhost:8001")
public interface UsuarioClientRest {

    @GetMapping("/{id}")
    UsuarioBeans detalle(@PathVariable Long id);

    @PostMapping("/")
    UsuarioBeans crear(@RequestBody UsuarioBeans usuario);

}
