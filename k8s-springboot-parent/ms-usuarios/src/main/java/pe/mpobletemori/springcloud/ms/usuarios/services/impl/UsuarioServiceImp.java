package pe.mpobletemori.springcloud.ms.usuarios.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pe.mpobletemori.springcloud.ms.usuarios.clients.CursoClienteRest;
import pe.mpobletemori.springcloud.ms.usuarios.models.entity.UsuarioEntity;
import pe.mpobletemori.springcloud.ms.usuarios.repositories.UsuarioRepository;
import pe.mpobletemori.springcloud.ms.usuarios.services.UsuarioService;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
public class UsuarioServiceImp implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoClienteRest cursoClienteRest;

    @Override
    public List<UsuarioEntity> listar() {
        return (List<UsuarioEntity>) usuarioRepository.findAll();
    }

    @Override
    public Optional<UsuarioEntity> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public UsuarioEntity guardar(UsuarioEntity usuarioEntity) {
        return usuarioRepository.save(usuarioEntity);
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
        cursoClienteRest.eliminarCursoUsuarioPorId(id);
    }

    @Override
    public List<UsuarioEntity> listarPorIds(Iterable<Long> iterableId) {
        return (List<UsuarioEntity>)usuarioRepository.findAllById(iterableId);
    }

    @Override
    public Optional<UsuarioEntity> buscarPorEmail(String email) {
        return usuarioRepository.buscarPorEmail(email);
    }

    @Override
    public boolean existePorEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}
