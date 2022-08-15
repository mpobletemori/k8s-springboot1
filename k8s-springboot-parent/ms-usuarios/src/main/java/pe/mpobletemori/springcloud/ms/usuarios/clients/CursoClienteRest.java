package pe.mpobletemori.springcloud.ms.usuarios.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "ms-cursos",url = "${ms-cursos.url}")
//Al usar spring cloud kubernete no se requiere url lo obtendra por el nombre de microservicio
@FeignClient(name = "ms-cursos")
public interface CursoClienteRest {

    @DeleteMapping("/eliminar-cursousuario/{id}")
    void eliminarCursoUsuarioPorId(@PathVariable Long id);
}
