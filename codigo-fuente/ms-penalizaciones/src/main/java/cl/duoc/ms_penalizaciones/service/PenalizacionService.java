package cl.duoc.ms_penalizaciones.service;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import cl.duoc.ms_penalizaciones.dto.CreatePenalizacionDTO;
import cl.duoc.ms_penalizaciones.dto.PenalizacionResponseDTO;
import cl.duoc.ms_penalizaciones.exception.NotFoundException;
import cl.duoc.ms_penalizaciones.model.PenalizacionEntity;
import cl.duoc.ms_penalizaciones.repository.PenalizacionRepository;
@Service
@RequiredArgsConstructor
public class PenalizacionService {
    private final PenalizacionRepository penalizacionRepository;

    public PenalizacionResponseDTO createPenalizacion(CreatePenalizacionDTO dto) {
        PenalizacionEntity entity = PenalizacionEntity.builder()
                .idPerfil(dto.idPerfil())
                .idPrestamo(dto.idPrestamo())
                .monto(dto.monto().doubleValue())
                .estado(dto.estado())
                .build();
        PenalizacionEntity savedEntity = penalizacionRepository.save(entity);
        return new PenalizacionResponseDTO(
                savedEntity.getIdPenalizacion(),
                savedEntity.getIdPerfil(),
                savedEntity.getIdPrestamo(),
                savedEntity.getMonto(),
                savedEntity.isEstado()
        );
        
    }

      public PenalizacionResponseDTO actualizarPenalizacion(Long idPenalizacion,CreatePenalizacionDTO dto) {
        PenalizacionEntity penalizacionABuscar= penalizacionRepository.findById(idPenalizacion)
                .orElseThrow(() -> new NotFoundException("Penalización no encontrada con ID: " + idPenalizacion));

        penalizacionABuscar.setIdPerfil(dto.idPerfil());
        penalizacionABuscar.setIdPrestamo(dto.idPrestamo());
        penalizacionABuscar.setMonto(dto.monto().doubleValue());
        penalizacionABuscar.setEstado(dto.estado());
        PenalizacionEntity savedEntity = penalizacionRepository.save(penalizacionABuscar);
        return new PenalizacionResponseDTO(
                savedEntity.getIdPenalizacion(),
                savedEntity.getIdPrestamo(),
                savedEntity.getIdPerfil(),
                savedEntity.getMonto(),
                savedEntity.isEstado()
        );
        
    }
   
    public void eliminarPenalizacion(Long idPenalizacion) {
        PenalizacionEntity entity = penalizacionRepository.findById(idPenalizacion)
                .orElseThrow(() -> new NotFoundException("Penalización no encontrada con ID: " + idPenalizacion));
        penalizacionRepository.delete(entity);
    }

    public PenalizacionResponseDTO buscarPenalizacion(Long idPenalizacion) {
        PenalizacionEntity entity = penalizacionRepository.findById(idPenalizacion)
                .orElseThrow(() -> new NotFoundException("Penalización no encontrada con ID: " + idPenalizacion));
        return new PenalizacionResponseDTO(
                entity.getIdPenalizacion(),
                entity.getIdPerfil(),
                entity.getIdPrestamo(),
                entity.getMonto(),
                entity.isEstado());
    }
    
}
