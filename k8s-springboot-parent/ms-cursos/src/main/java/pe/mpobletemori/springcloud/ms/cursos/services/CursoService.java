package pe.mpobletemori.springcloud.ms.cursos.services;

import pe.mpobletemori.springcloud.ms.cursos.model.UsuarioBeans;
import pe.mpobletemori.springcloud.ms.cursos.model.entity.CursoEntity;

import java.util.List;
import java.util.Optional;

public interface CursoService {
    List<CursoEntity> listar();
    Optional<CursoEntity> buscarPorId(Long id);
    CursoEntity guardar(CursoEntity cursoEntity);
    void eliminar(Long id);

    Optional<UsuarioBeans> asignarUsuario(UsuarioBeans usuario,Long cursoId);
    Optional<UsuarioBeans> crearUsuario(UsuarioBeans usuario,Long cursoId);
    Optional<UsuarioBeans> eliminarUsuario(UsuarioBeans usuario,Long cursoId);

}
