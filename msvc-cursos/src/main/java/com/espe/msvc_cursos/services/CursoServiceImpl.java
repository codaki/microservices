package com.espe.msvc_cursos.services;

import com.espe.msvc_cursos.clients.UsuarioClientRest;
import com.espe.msvc_cursos.models.Usuario;
import com.espe.msvc_cursos.models.entity.Curso;
import com.espe.msvc_cursos.models.entity.CursoUsuario;
import com.espe.msvc_cursos.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService{
    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    UsuarioClientRest usuarioClientRest;

    @Override
    @Transactional(readOnly = true)
    public List<Curso> listar() {
        return (List<Curso>) cursoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> porId(Long id) {
        return cursoRepository.findById(id);
    }

    @Override
    @Transactional
    public Curso guardar(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        cursoRepository.deleteById(id);
    }

    @Override
    public Boolean agregarUsuario(Usuario usuario, Long idCurso) {
        Optional<Curso> o = cursoRepository.findById(idCurso);
        if (o.isPresent()) {
            Usuario usuarioMicro = usuarioClientRest.detalle(usuario.getId());
            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMicro.getId());

            curso.addCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);
            return true;
        }
        return false;
}

//    @Override
//    public Optional <Usuario> crearUsuario(Usuario usuario, Long idCurso){
//        Optional <Curso> o = cursoRepository.findById(idCurso);
//        if(o.isPresent()){
//            Usuario usuarioMicro = usuarioClientRest.crear(usuario);
//            Curso curso = o.get();
//            CursoUsuario cursoUsuario = new CursoUsuario();
//            cursoUsuario.setUsuarioId(usuarioMicro.getId());
//
//            curso.addCursoUsuario(cursoUsuario);
//            cursoRepository.save(curso);
//        }
//        return Optional.empty();
//    }

    @Override
    public Boolean eliminarUsuario(Usuario usuario, Long idCurso) {
        Optional<Curso> o = cursoRepository.findById(idCurso);
        if (o.isPresent()) {
            Usuario usuarioMicro = usuarioClientRest.detalle(usuario.getId());
            Curso curso = o.get();
            CursoUsuario existentes = curso.getCursoUsuario().stream()
                    .filter(cu -> cu.getUsuarioId().equals(usuarioMicro.getId()))
                    .findFirst()
                    .orElse(null);
            if (existentes != null) {
                curso.removeCursoUsuario(existentes);
                cursoRepository.save(curso);
                return true;
            }
        }
   return false;
    }

}
