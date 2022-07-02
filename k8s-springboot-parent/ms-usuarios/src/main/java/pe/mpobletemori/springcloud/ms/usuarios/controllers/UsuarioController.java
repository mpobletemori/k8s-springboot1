package pe.mpobletemori.springcloud.ms.usuarios.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.mpobletemori.springcloud.ms.usuarios.models.entity.UsuarioEntity;
import pe.mpobletemori.springcloud.ms.usuarios.services.UsuarioService;

import java.util.List;
import java.util.Optional;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public List<UsuarioEntity> listar(){
        return usuarioService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioEntity> detalle(@PathVariable  Long id){
        Optional<UsuarioEntity> usuario = usuarioService.buscarPorId(id);
        if(usuario.isPresent()){
            return ResponseEntity.ok(usuario.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<UsuarioEntity> crear(@RequestBody UsuarioEntity usuario){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioEntity> editar(@PathVariable Long id,@RequestBody UsuarioEntity usuario){
        Optional<UsuarioEntity> usuarioOptional = usuarioService.buscarPorId(id);
        if(usuarioOptional.isPresent()){
            UsuarioEntity usuarioBD = usuarioOptional.get();
            usuarioBD.setNombre(usuario.getNombre());
            usuarioBD.setEmail(usuario.getEmail());
            usuarioBD.setPassword(usuario.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(usuarioBD));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>  eliminar(@PathVariable Long id){
        Optional<UsuarioEntity> usuarioOptional = usuarioService.buscarPorId(id);
        if (usuarioOptional.isPresent()){
            usuarioService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
