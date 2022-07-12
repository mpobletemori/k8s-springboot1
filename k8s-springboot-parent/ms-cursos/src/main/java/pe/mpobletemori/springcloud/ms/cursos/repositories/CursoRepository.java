package pe.mpobletemori.springcloud.ms.cursos.repositories;

import org.springframework.data.repository.CrudRepository;
import pe.mpobletemori.springcloud.ms.cursos.model.entity.CursoEntity;

public interface CursoRepository extends CrudRepository<CursoEntity,Long> {
}
