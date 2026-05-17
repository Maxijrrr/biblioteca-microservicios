package cl.duoc.ms_penalizaciones.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import cl.duoc.ms_penalizaciones.dto.CreatePenalizacionDTO;
import cl.duoc.ms_penalizaciones.dto.PenalizacionResponseDTO;
import cl.duoc.ms_penalizaciones.exception.NotFoundException;
import cl.duoc.ms_penalizaciones.exception.ServicioNoDisponibleException;
import cl.duoc.ms_penalizaciones.exception.RecursoNoEncontradoException;
import cl.duoc.ms_penalizaciones.model.PenalizacionEntity;
import cl.duoc.ms_penalizaciones.repository.PenalizacionRepository;
import cl.duoc.ms_penalizaciones.dto.PerfilDTO;
import cl.duoc.ms_penalizaciones.dto.PerfilResponse;
import cl.duoc.ms_penalizaciones.client.PerfilClient;
import cl.duoc.ms_penalizaciones.client.PrestamoClient;
import cl.duoc.ms_penalizaciones.dto.PrestamoDTO;
import feign.FeignException;

@Service
@RequiredArgsConstructor
public class PenalizacionService {
        private final PenalizacionRepository penalizacionRepository;

        private final PerfilClient perfilClient;
        private final PrestamoClient prestamoClient;

        public PenalizacionResponseDTO createPenalizacion(CreatePenalizacionDTO dto) {

                validarPerfil(dto.idPerfil());
                validarPrestamo(dto.idPrestamo());

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
                                savedEntity.isEstado());

        }

        public PenalizacionResponseDTO actualizarPenalizacion(Long idPenalizacion, CreatePenalizacionDTO dto) {
                PenalizacionEntity penalizacionABuscar = penalizacionRepository.findById(idPenalizacion)
                                .orElseThrow(() -> new RecursoNoEncontradoException(
                                                "Penalización no encontrada con ID: " + idPenalizacion));

                validarPerfil(dto.idPerfil());
                validarPrestamo(dto.idPrestamo());

                penalizacionABuscar.setIdPerfil(dto.idPerfil());
                penalizacionABuscar.setIdPrestamo(dto.idPrestamo());
                penalizacionABuscar.setMonto(dto.monto().doubleValue());
                penalizacionABuscar.setEstado(dto.estado());
                PenalizacionEntity savedEntity = penalizacionRepository.save(penalizacionABuscar);
                return new PenalizacionResponseDTO(
                                savedEntity.getIdPenalizacion(),
                                savedEntity.getIdPerfil(),
                                savedEntity.getIdPrestamo(),
                                savedEntity.getMonto(),
                                savedEntity.isEstado());

        }

        public void eliminarPenalizacion(Long idPenalizacion) {
                PenalizacionEntity entity = penalizacionRepository.findById(idPenalizacion)
                                .orElseThrow(() -> new RecursoNoEncontradoException(
                                                "Penalización no encontrada con ID: " + idPenalizacion));
                penalizacionRepository.delete(entity);
        }

        public PenalizacionResponseDTO buscarPenalizacion(Long idPenalizacion) {
                PenalizacionEntity entity = penalizacionRepository.findById(idPenalizacion)
                                .orElseThrow(() -> new RecursoNoEncontradoException(
                                                "Penalización no encontrada con ID: " + idPenalizacion));
                return new PenalizacionResponseDTO(
                                entity.getIdPenalizacion(),
                                entity.getIdPerfil(),
                                entity.getIdPrestamo(),
                                entity.getMonto(),
                                entity.isEstado());
        }

        private void validarPerfil(Long idPerfil) {
    try {
        PerfilResponse perfil = perfilClient.buscarPorId(idPerfil);
        System.out.println("Respuesta del servicio de perfiles: " + perfil);

        if (perfil == null || perfil.data() == null) {
            throw new RecursoNoEncontradoException("Perfil no encontrado con ID: " + idPerfil);
        }

    } catch (FeignException.NotFound e) {
        System.out.println("404 - Perfil no encontrado: " + e.getMessage());
        throw new RecursoNoEncontradoException("Perfil no encontrado con ID: " + idPerfil);

    } catch (FeignException e) {
        System.out.println("FeignException status: " + e.status());
        System.out.println("FeignException message: " + e.getMessage());
        System.out.println("FeignException content: " + e.contentUTF8());
        throw new ServicioNoDisponibleException("No se pudo conectar con el servicio de perfiles.");
    }
}

        private void validarPrestamo(Long idPrestamo) {
                try {
                        PrestamoDTO prestamo = prestamoClient.obtenerPrestamo(idPrestamo);

                        if (prestamo == null) {
                                throw new RecursoNoEncontradoException("Préstamo no encontrado con ID: " + idPrestamo);
                        }

                } catch (FeignException.NotFound e) {
                        throw new RecursoNoEncontradoException("Préstamo no encontrado con ID: " + idPrestamo);

                } catch (FeignException e) {
                        throw new ServicioNoDisponibleException("No se pudo conectar con el servicio de préstamos.");
                }
        }

}
