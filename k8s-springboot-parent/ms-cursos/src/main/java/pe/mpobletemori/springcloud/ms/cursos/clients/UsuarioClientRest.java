package pe.mpobletemori.springcloud.ms.cursos.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import pe.mpobletemori.springcloud.ms.cursos.model.UsuarioBeans;

import java.util.List;


@FeignClient(name = "ms-usuarios",url = "localhost:8001")
public interface UsuarioClientRest {

    @GetMapping("/{id}")
    UsuarioBeans detalle(@PathVariable Long id);

    @PostMapping("/")
    UsuarioBeans crear(@RequestBody UsuarioBeans usuario);

    @GetMapping("/usuarios-por-curso")
    List<UsuarioBeans> obtenerAlumnoPorCurso(@RequestParam Iterable<Long> ids);

}
