package pe.mpobletemori.springcloud.ms.cursos.model.entity;

import pe.mpobletemori.springcloud.ms.cursos.model.UsuarioBeans;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="cursos")
public class CursoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nombre;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    //columna de enlace para definir clave foranea
    @JoinColumn(name = "curso_id")
    private List<CursoUsuarioEntity> cursoUsuarios;

    //ignorar variable como parte del mapeo de hibernate
    @Transient
    private List<UsuarioBeans> usuarios;

    public CursoEntity() {
        this.cursoUsuarios = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<CursoUsuarioEntity> getCursoUsuarios() {
        return cursoUsuarios;
    }

    public void setCursoUsuarios(List<CursoUsuarioEntity> cursoUsuarios) {
        this.cursoUsuarios = cursoUsuarios;
    }

    public void addCursoUsuario(CursoUsuarioEntity cursoUsuarioEntity){
        this.cursoUsuarios.add(cursoUsuarioEntity);
    }

    public void removeCursoUsuario(CursoUsuarioEntity cursoUsuarioEntity){
        this.cursoUsuarios.remove(cursoUsuarioEntity);
    }


}
