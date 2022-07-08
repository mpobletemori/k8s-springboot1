package pe.mpobletemori.springcloud.ms.usuarios.repositories;

import org.springframework.data.repository.CrudRepository;
import pe.mpobletemori.springcloud.ms.usuarios.models.entity.UsuarioEntity;

import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<UsuarioEntity,Long> {

    Optional<UsuarioEntity> findByEmail(String email);
}
