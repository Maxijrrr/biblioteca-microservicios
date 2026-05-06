package cl.duoc.ms_devoluciones.service.impl;

import cl.duoc.ms_devoluciones.client.PrestamoClient;
import cl.duoc.ms_devoluciones.dto.DevolucionDTO;
import cl.duoc.ms_devoluciones.dto.PrestamoDTO;
import cl.duoc.ms_devoluciones.exception.RecursoNoEncontradoException;
import cl.duoc.ms_devoluciones.exception.ReglaNegocioException;
import cl.duoc.ms_devoluciones.exception.ServicioNoDisponibleException;
import cl.duoc.ms_devoluciones.model.Devolucion;
import cl.duoc.ms_devoluciones.repository.DevolucionRepository;
import cl.duoc.ms_devoluciones.service.DevolucionService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DevolucionServiceImpl implements DevolucionService {

    private static final Logger log = LoggerFactory.getLogger(DevolucionServiceImpl.class);

    @Autowired
    private DevolucionRepository repository;

    @Autowired
    private PrestamoClient prestamoClient;

    @Override
    public List<DevolucionDTO> listarTodas() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public DevolucionDTO obtenerPorId(Long id) {
        Devolucion devolucion = repository.findById(id)
                .orElseThrow(() -> new cl.duoc.ms_devoluciones.exception.RecursoNoEncontradoException("Devolución con ID " + id + " no encontrada"));
        return toDTO(devolucion);
    }

    @Override
    public DevolucionDTO guardar(DevolucionDTO dto) {
        validarPrestamoActivo(dto.getIdPrestamo());

        if (dto.getFechaEntrega() == null) {
            dto.setFechaEntrega(LocalDateTime.now());
        }
        Devolucion devolucion = toEntity(dto);
        return toDTO(repository.save(devolucion));
    }

    @Override
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new cl.duoc.ms_devoluciones.exception.RecursoNoEncontradoException("No se puede eliminar: Devolución con ID " + id + " no existe");
        }
        repository.deleteById(id);
    }

    private DevolucionDTO toDTO(Devolucion entity) {
        DevolucionDTO dto = new DevolucionDTO();
        dto.setIdDevolucion(entity.getIdDevolucion());
        dto.setIdPrestamo(entity.getIdPrestamo());
        dto.setFechaEntrega(entity.getFechaEntrega());
        return dto;
    }

    private Devolucion toEntity(DevolucionDTO dto) {
        Devolucion entity = new Devolucion();
        entity.setIdDevolucion(dto.getIdDevolucion());
        entity.setIdPrestamo(dto.getIdPrestamo());
        entity.setFechaEntrega(dto.getFechaEntrega());
        return entity;
    }

    private void validarPrestamoActivo(Long idPrestamo) {
        try {
            log.info("Consultando ms-prestamos para validar prestamo ID={}", idPrestamo);
            PrestamoDTO prestamo = prestamoClient.obtenerPrestamo(idPrestamo);

            if (!"ACTIVO".equalsIgnoreCase(prestamo.getEstado())) {
                log.warn("Prestamo ID={} no esta activo. Estado actual={}", idPrestamo, prestamo.getEstado());
                throw new ReglaNegocioException("Solo se pueden registrar devoluciones de prestamos activos");
            }

            log.info("Prestamo ID={} validado correctamente", idPrestamo);
        } catch (FeignException.NotFound e) {
            log.warn("Prestamo ID={} no encontrado en ms-prestamos", idPrestamo);
            throw new RecursoNoEncontradoException("Prestamo no encontrado para la devolucion");
        } catch (FeignException e) {
            log.error("Error al conectar con ms-prestamos: {}", e.getMessage());
            throw new ServicioNoDisponibleException("Servicio de prestamos no disponible en este momento");
        }
    }
}
