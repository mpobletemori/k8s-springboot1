package pe.mpobletemori.springcloud.ms.usuarios.services;

import pe.mpobletemori.springcloud.ms.usuarios.models.entity.UsuarioEntity;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
        List<UsuarioEntity> listar();
        Optional<UsuarioEntity> buscarPorId(Long id);
        UsuarioEntity guardar(UsuarioEntity usuarioEntity);
        void eliminar(Long id);
        List<UsuarioEntity> listarPorIds(Iterable<Long> iterableId);

        Optional<UsuarioEntity> buscarPorEmail(String email);
        boolean existePorEmail(String email);


}
