package pe.mpobletemori.springcloud.ms.usuarios.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.mpobletemori.springcloud.ms.usuarios.models.entity.UsuarioEntity;
import pe.mpobletemori.springcloud.ms.usuarios.services.UsuarioService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<?> crear(@Valid @RequestBody UsuarioEntity usuario , BindingResult result){
        if(result.hasErrors()){
            return validarParams(result);
        }
        if(usuarioService.existePorEmail(usuario.getEmail())){
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("mensaje", "Ya existe un usuario con ese correo electronico!"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody UsuarioEntity usuario, BindingResult result,@PathVariable Long id){
        if(result.hasErrors()){
            return validarParams(result);
        }

        Optional<UsuarioEntity> usuarioOptional = usuarioService.buscarPorId(id);
        if(usuarioOptional.isPresent()){
            UsuarioEntity usuarioBD = usuarioOptional.get();
            if(!usuarioBD.getEmail().equalsIgnoreCase(usuario.getEmail()) && usuarioService.buscarPorEmail(usuario.getEmail()).isPresent()){
                return ResponseEntity
                        .badRequest()
                        .body(Map.of("mensaje", "Ya existe un usuario con ese correo electronico!"));
            }
            usuarioBD.setNombre(usuario.getNombre());
            usuarioBD.setEmail(usuario.getEmail());
            usuarioBD.setPassword(usuario.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(usuarioBD));
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<Map<String, String>> validarParams(BindingResult result) {
        Map<String,String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err->{
            errores.put(err.getField(),String.format("El campo %s  %s",err.getField(),err.getDefaultMessage()));
        });
        return ResponseEntity.badRequest().body(errores);
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

    @GetMapping("/usuarios-por-curso")
    public ResponseEntity<?> obtenerAlumnoPorCurso(@RequestParam List<Long> ids){
            return ResponseEntity.ok(usuarioService.listarPorIds(ids));
    }

    @Autowired
    private Environment env;

    @GetMapping("/testk8s-envs")
    public Map<String,String> testK8sEnvs(){
        return Map.of("myPodName",env.getProperty("MY_POD_NAME", "pob1"),
                      "myPodIP",env.getProperty("MY_POD_IP", "pobIP"));
    }

}
