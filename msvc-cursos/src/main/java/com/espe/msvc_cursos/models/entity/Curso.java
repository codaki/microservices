package com.espe.msvc_cursos.models.entity;
import com.espe.msvc_cursos.models.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cursos")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private List<CursoUsuario> cursoUsuario;

    @Transient
    private List<Usuario> usuarios;

    public Curso(){
        cursoUsuario = new ArrayList<>();
        usuarios = new ArrayList<>();
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
    public List<CursoUsuario> getCursoUsuario() {
        return cursoUsuario;
    }
    public void setCursoUsuario(List<CursoUsuario> cursoUsuario) {
        this.cursoUsuario = cursoUsuario;
    }
    public void addCursoUsuario(CursoUsuario cursoUsuario){
        this.cursoUsuario.add(cursoUsuario);
    }
    public void removeCursoUsuario(CursoUsuario cursoUsuario){
        this.cursoUsuario.remove(cursoUsuario);
    }
}
