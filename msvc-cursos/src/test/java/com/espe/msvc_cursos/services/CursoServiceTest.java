package com.espe.msvc_cursos.services;

import com.espe.msvc_cursos.models.entity.Curso;
import com.espe.msvc_cursos.repositories.CursoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CursoServiceTest {
@Autowired
    private CursoService cursoService;
@MockBean
    private CursoRepository cursoRepository;
    @BeforeEach
    void setUp() {
        Curso curso = new Curso();
        curso.setId(1L);
        curso.setNombre("Curso de prueba");

        Mockito.when(cursoRepository.findById(1L))
                .thenReturn(Optional.of(curso));
    }
    @Test
    @DisplayName("Cuando se busca un curso por su id")
    public void encontrarPorId(){
        long valorId = 1L;
       Optional<Curso> curso = cursoService.porId(valorId);
       if (curso.isPresent()) {
           assertEquals(valorId, curso.get().getId());
           System.out.println("curso.get() = " + curso.get().getNombre());
       }
    }
}