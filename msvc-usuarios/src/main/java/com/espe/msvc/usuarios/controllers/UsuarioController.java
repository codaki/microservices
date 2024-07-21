package com.espe.msvc.usuarios.controllers;

import com.espe.msvc.usuarios.models.entity.Usuario;
import com.espe.msvc.usuarios.services.UsuarioService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.*;

@RestController
@CrossOrigin(origins = "*")
public class UsuarioController {
    @Autowired
    private UsuarioService service;
    @GetMapping("/usuarios")
    public List<Usuario> listar(){
        return service.findAll();
    }
@GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Usuario> o = service.porId(id);
        if(o.isPresent()){
            return ResponseEntity.ok().body(o.get());
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping("/saveUser")
    public ResponseEntity<?> save(@Valid @RequestBody Usuario usuario, BindingResult result) {
        if (result.hasErrors()){
            return validar(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuario));
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }

@PostMapping("/crearUsuario")
    public ResponseEntity<?> guardar(@RequestBody Usuario usuario, BindingResult result){
     if(result.hasErrors()){
    return validar(result);
    }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuario));
    }
    @PutMapping("/actualizarUsuario/{id}")
    public ResponseEntity<?> editar(@RequestBody Usuario usuario, @PathVariable Long id){
        Optional<Usuario> o = service.porId(id);
        if(o.isPresent()){
            Usuario usuarioDb = o.get();
            usuarioDb.setNombre(usuario.getNombre());
            usuarioDb.setEmail(usuario.getEmail());
            usuarioDb.setPassword(usuario.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuarioDb));
        }

        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/eliminarUsuario/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Usuario> o = service.porId(id);
        if(o.isPresent()){
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
//    private static ResponseEntity<?> validar1(BindingResult result) {
//       Map<String,String> errores = new HashMap<>();
//         result.getFieldErrors().forEach(err -> {
//              errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
//         });
//            return ResponseEntity.badRequest().body(errores);
//    }
}
