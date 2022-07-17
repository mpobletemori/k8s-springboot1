package pe.mpobletemori.springcloud.ms.cursos.model.entity;

import javax.persistence.*;

@Entity
@Table(name="cursos_usuarios")
public class CursoUsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="usuario_id",unique = true)
    private Long usuarioId;

    public CursoUsuarioEntity() {
    }

    public CursoUsuarioEntity(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }


    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(!(obj instanceof CursoUsuarioEntity)){
            return false;
        }
        CursoUsuarioEntity o =(CursoUsuarioEntity) obj;
        return this.usuarioId!=null && this.usuarioId.equals(o.getUsuarioId());
    }
}
