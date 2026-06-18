package cl.duoc.ms_penalizaciones.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.ms_penalizaciones.repository.PenalizacionRepository;
import cl.duoc.ms_penalizaciones.dto.PenalizacionResponseDTO;

import cl.duoc.ms_penalizaciones.exception.RecursoNoEncontradoException;
import cl.duoc.ms_penalizaciones.model.PenalizacionEntity;

@ExtendWith(MockitoExtension.class)
 class PenalizacionServiceTest { 
    @Mock
    private PenalizacionRepository penalizacionRepository;
    @InjectMocks
    private PenalizacionService penalizacionService;
    @Test
    void buscarPenalizacionDevuelveDtoCuandoExiste() {
        PenalizacionEntity penalizacion = PenalizacionEntity.builder()
                .idPenalizacion(1L)
                .idPerfil(1L)
                .monto(5000.0)
                .estado(true)
                .build();
        when(penalizacionRepository.findById(1L))
                .thenReturn(Optional.of(penalizacion));
        PenalizacionResponseDTO response = penalizacionService.buscarPenalizacion(1L);
        assertEquals(1L, response.idPenalizacion());
        assertEquals(1L, response.idPerfil());
        assertEquals(5000.0, response.monto());
        assertEquals(true, response.estado());
        verify(penalizacionRepository).findById(1L);
    }
    @Test
    void buscarPenalizacionDevuelveNotFoundCuandoNoExiste() {

        when(penalizacionRepository.findById(2L))
                .thenReturn(Optional.empty());
        assertThrows(RecursoNoEncontradoException.class,()-> penalizacionService.buscarPenalizacion(2L));
        verify(penalizacionRepository).findById(2L);

    }
    @Test
    void eliminarPenalizacionEliminaCuandoExiste() {
        PenalizacionEntity penalizacion = PenalizacionEntity.builder()
                .idPenalizacion(1L)
                .idPerfil(1L)
                .monto(5000.0)
                .estado(true)
                .build();
        when(penalizacionRepository.findById(1L))
                .thenReturn(Optional.of(penalizacion));
        penalizacionService.eliminarPenalizacion(1L);
        verify(penalizacionRepository).findById(1L);
        verify(penalizacionRepository).delete(any(PenalizacionEntity.class));
    }
}
