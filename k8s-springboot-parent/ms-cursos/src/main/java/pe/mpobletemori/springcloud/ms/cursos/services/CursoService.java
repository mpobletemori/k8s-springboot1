package pe.mpobletemori.springcloud.ms.cursos.services;

import pe.mpobletemori.springcloud.ms.cursos.model.UsuarioBeans;
import pe.mpobletemori.springcloud.ms.cursos.model.entity.CursoEntity;

import java.util.List;
import java.util.Optional;

public interface CursoService {
    List<CursoEntity> listar();
    Optional<CursoEntity> buscarPorId(Long id);
    Optional<CursoEntity> buscarPorIdConUsuarios(Long id,String token);
    CursoEntity guardar(CursoEntity cursoEntity);
    void eliminar(Long id);
    void eliminarCursoUsuarioPorId(Long id);

    Optional<UsuarioBeans> asignarUsuario(UsuarioBeans usuario,Long cursoId,String token);
    Optional<UsuarioBeans> crearUsuario(UsuarioBeans usuario,Long cursoId,String token);
    Optional<UsuarioBeans> eliminarUsuario(UsuarioBeans usuario,Long cursoId,String token);

}
