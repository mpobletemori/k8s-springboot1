package pe.mpobletemori.springcloud.ms.usuarios.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-cursos",url = "localhost:8002")
public interface CursoClienteRest {

    @DeleteMapping("/eliminar-cursousuario/{id}")
    void eliminarCursoUsuarioPorId(@PathVariable Long id);
}
