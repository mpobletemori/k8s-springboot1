package pe.mpobletemori.springcloud.ms.cursos.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pe.mpobletemori.springcloud.ms.cursos.model.entity.CursoEntity;

public interface CursoRepository extends CrudRepository<CursoEntity,Long> {

    @Modifying
    @Query("delete from CursoUsuario cu where cu.usuarioId=?1")
    void eliminarCursoUsuarioPorId(Long id);
}
