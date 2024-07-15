package com.espe.msvc_cursos.controllers;

import com.espe.msvc_cursos.models.Usuario;
import com.espe.msvc_cursos.services.CursoService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.espe.msvc_cursos.models.entity.Curso;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class CursoController {
    @Autowired
    private CursoService cursoService;

    @GetMapping("/listar")
    public List<Curso> listar(){
        return cursoService.listar();
    }

    @GetMapping("/curosPorId/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Curso> o = cursoService.porId(id);
        if(o.isPresent()){
            return ResponseEntity.ok(o.get());
    }
        return ResponseEntity.notFound().build();
    }

    @PostMapping ("/crearCurso")
    public ResponseEntity<?> crear(@RequestBody Curso curso){
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.guardar(curso));
    }

    @PutMapping("/editarCursos/{id}")
    public ResponseEntity<?> editar(@RequestBody Curso curso, @PathVariable Long id) {
        Optional<Curso> o = cursoService.porId(id);
        if (o.isPresent()) {
            Curso cursoDb = o.get();
            cursoDb.setNombre(curso.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.guardar(cursoDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminarCursos/{id}")
    public ResponseEntity<?> eliminar (@PathVariable Long id){
        Optional<Curso> o = cursoService.porId(id);
        if(o.isPresent()){
            cursoService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/agregarUsuario/{idCurso}")
    public ResponseEntity<?> agregarUsuario(@RequestBody Usuario usuario, @PathVariable Long idCurso) {
        Optional<Usuario> o;
        try {
            o = cursoService.agregarUsuario(usuario, idCurso);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "Error:" + e.getMessage()));
        }
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

}
