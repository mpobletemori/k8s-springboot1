package pe.mpobletemori.springcloud.ms.cursos.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.mpobletemori.springcloud.ms.cursos.model.UsuarioBeans;
import pe.mpobletemori.springcloud.ms.cursos.model.entity.CursoEntity;
import pe.mpobletemori.springcloud.ms.cursos.repositories.CursoRepository;
import pe.mpobletemori.springcloud.ms.cursos.services.CursoService;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public List<CursoEntity> listar() {
        return (List<CursoEntity>)cursoRepository.findAll();
    }

    @Override
    public Optional<CursoEntity> buscarPorId(Long id) {
        return cursoRepository.findById(id);
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

    @Override
    public Optional<UsuarioBeans> asignarUsuario(UsuarioBeans usuario, Long cursoId) {
        return Optional.empty();
    }

    @Override
    public Optional<UsuarioBeans> crearUsuario(UsuarioBeans usuario, Long cursoId) {
        return Optional.empty();
    }

    @Override
    public Optional<UsuarioBeans> eliminarUsuario(UsuarioBeans usuario, Long cursoId) {
        return Optional.empty();
    }
}
