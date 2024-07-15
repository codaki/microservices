package com.espe.msvc_cursos.repositories;

import org.springframework.data.repository.CrudRepository;
import com.espe.msvc_cursos.models.entity.Curso;

public interface CursoRepository extends CrudRepository<Curso,Long> {
}
