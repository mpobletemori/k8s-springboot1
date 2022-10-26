package pe.mpobletemori.springcloud.ms.cursos.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import pe.mpobletemori.springcloud.ms.cursos.model.UsuarioBeans;

import java.util.List;

//@FeignClient(name = "ms-usuarios",url = "${ms-usuarios.url}")
//Al usar spring cloud kubernete no se requiere url lo obtendra por el nombre de microservicio
@FeignClient(name = "ms-usuarios")
public interface UsuarioClientRest {

    @GetMapping("/{id}")
    UsuarioBeans detalle(@PathVariable Long id,@RequestHeader(value="Authorization",required = true) String token);

    @PostMapping("/")
    UsuarioBeans crear(@RequestBody UsuarioBeans usuario,@RequestHeader(value="Authorization",required = true) String token);

    @GetMapping("/usuarios-por-curso")
    List<UsuarioBeans> obtenerAlumnoPorCurso(@RequestParam Iterable<Long> ids,@RequestHeader(value="Authorization",required = true) String token);

}
