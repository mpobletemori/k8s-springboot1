package pe.mpobletemori.springcloud.ms.cursos.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.mpobletemori.springcloud.ms.cursos.clients.UsuarioClientRest;
import pe.mpobletemori.springcloud.ms.cursos.model.UsuarioBeans;
import pe.mpobletemori.springcloud.ms.cursos.model.entity.CursoEntity;
import pe.mpobletemori.springcloud.ms.cursos.model.entity.CursoUsuarioEntity;
import pe.mpobletemori.springcloud.ms.cursos.repositories.CursoRepository;
import pe.mpobletemori.springcloud.ms.cursos.services.CursoService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioClientRest usuarioClientRest;

    @Override
    public List<CursoEntity> listar() {
        return (List<CursoEntity>)cursoRepository.findAll();
    }

    @Override
    public Optional<CursoEntity> buscarPorId(Long id) {
        return cursoRepository.findById(id);
    }

    @Override
    public Optional<CursoEntity> buscarPorIdConUsuarios(Long id,String token) {
        Optional<CursoEntity> o = cursoRepository.findById(id);

        if(o.isPresent()){
            CursoEntity cursoEntity = o.get();
            if(!cursoEntity.getCursoUsuarios().isEmpty()){
                List<Long> ids = cursoEntity.getCursoUsuarios()
                        .stream().map(cu -> cu.getUsuarioId())
                        .collect(Collectors.toList());
                List<UsuarioBeans> usuarioBeans = usuarioClientRest.obtenerAlumnoPorCurso(ids,token);
                cursoEntity.setUsuarios(usuarioBeans);
            }
            return Optional.of(cursoEntity);
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public CursoEntity guardar(CursoEntity cursoEntity) {
        return cursoRepository.save(cursoEntity);
    }

    @Transactional
    @Override
    public void eliminar(Long id) {
        cursoRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void eliminarCursoUsuarioPorId(Long id) {
         cursoRepository.eliminarCursoUsuarioPorId(id);
    }

    @Transactional
    @Override
    public Optional<UsuarioBeans> asignarUsuario(UsuarioBeans usuario, Long cursoId,String token) {
        Optional<CursoEntity> o = cursoRepository.findById(cursoId);
        if(o.isPresent()){
            CursoEntity cursoEntity = o.get();
            UsuarioBeans usuarioMS = usuarioClientRest.detalle(usuario.getId(),token);
            cursoEntity.addCursoUsuario(new CursoUsuarioEntity(usuarioMS.getId()));
            cursoRepository.save(cursoEntity);
            return Optional.of(usuario);
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public Optional<UsuarioBeans> crearUsuario(UsuarioBeans usuario, Long cursoId,String token) {

        Optional<CursoEntity> o = cursoRepository.findById(cursoId);
        if(o.isPresent()){
            CursoEntity cursoEntity = o.get();
            UsuarioBeans usuarioNuevoMS = usuarioClientRest.crear(usuario,token);
            cursoEntity.addCursoUsuario(new CursoUsuarioEntity(usuarioNuevoMS.getId()));
            cursoRepository.save(cursoEntity);
            return Optional.of(usuario);
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public Optional<UsuarioBeans> eliminarUsuario(UsuarioBeans usuario, Long cursoId,String token) {

        Optional<CursoEntity> o = cursoRepository.findById(cursoId);
        if(o.isPresent()){
            CursoEntity cursoEntity = o.get();
            UsuarioBeans usuarioMS = usuarioClientRest.detalle(usuario.getId(),token);
            cursoEntity.removeCursoUsuario(new CursoUsuarioEntity(usuarioMS.getId()));
            cursoRepository.save(cursoEntity);
            return Optional.of(usuario);
        }
        return Optional.empty();

    }
}
