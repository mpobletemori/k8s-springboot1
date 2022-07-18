package pe.mpobletemori.springcloud.ms.cursos.controllers;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.mpobletemori.springcloud.ms.cursos.model.UsuarioBeans;
import pe.mpobletemori.springcloud.ms.cursos.model.entity.CursoEntity;
import pe.mpobletemori.springcloud.ms.cursos.services.CursoService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public ResponseEntity<List<CursoEntity>> listar(){
        return ResponseEntity.ok(cursoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?>  detalle(@PathVariable Long id){
        Optional<CursoEntity> cursoEntityOptional = cursoService.buscarPorIdConUsuarios(id);
        if (cursoEntityOptional.isPresent()){
            return ResponseEntity.ok(cursoEntityOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<?> crear(@Valid @RequestBody CursoEntity cursoEntity, BindingResult result){
        if(result.hasErrors()){
            return validarParams(result);
        }
          return ResponseEntity
                  .status(HttpStatus.CREATED)
                  .body(cursoService.guardar(cursoEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody CursoEntity cursoEntity, BindingResult result,@PathVariable Long id){
        if(result.hasErrors()){
            return validarParams(result);
        }

        Optional<CursoEntity> cursoEntityOptional = cursoService.buscarPorId(id);
        if(cursoEntityOptional.isPresent()){
            CursoEntity cursoEntityDB = cursoEntityOptional.get();
            cursoEntityDB.setNombre(cursoEntity.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.guardar(cursoEntityDB));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<CursoEntity> cursoEntityOptional = cursoService.buscarPorId(id);
        if(cursoEntityOptional.isPresent()){
            cursoService.eliminar(cursoEntityOptional.get().getId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    @PutMapping("/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody UsuarioBeans usuario,@PathVariable Long cursoId){
        Optional<UsuarioBeans> o;
        try {
            o = cursoService.asignarUsuario(usuario, cursoId);
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje",
                            "No existe el usuario por el id o error en comunicacion :"+e.getMessage()));
        }


        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuario(@RequestBody UsuarioBeans usuario,@PathVariable Long cursoId){
        Optional<UsuarioBeans> o;
        try {
            o = cursoService.crearUsuario(usuario, cursoId);
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje",
                            "No se pudo crear usuario o error en comunicacion :"+e.getMessage()));
        }
        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-usuario/{cursoId}")
    public ResponseEntity<?> eliminarUsuario(@RequestBody UsuarioBeans usuario,@PathVariable Long cursoId){
        Optional<UsuarioBeans> o;
        try {
            o = cursoService.eliminarUsuario(usuario, cursoId);
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje",
                            "No existe el usuario por el id o error en comunicacion :"+e.getMessage()));
        }


        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(o.get());
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



}
