package cl.duoc.ms_prestamos.service;

import cl.duoc.ms_prestamos.model.Prestamo;
import cl.duoc.ms_prestamos.dto.PrestamoDTO;
import cl.duoc.ms_prestamos.dto.PrestamoResponseDTO;
import cl.duoc.ms_prestamos.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import feign.FeignException;
import cl.duoc.ms_prestamos.client.PerfilClient;
import cl.duoc.ms_prestamos.client.InventarioClient;
import cl.duoc.ms_prestamos.dto.PerfilDTO;
import cl.duoc.ms_prestamos.exception.RecursoNoEncontradoException;
import cl.duoc.ms_prestamos.exception.ServicioNoDisponibleException;
import java.util.Map;

@Service
public class PrestamoService {

    private static final Logger log = LoggerFactory.getLogger(PrestamoService.class);

    @Autowired
    private PrestamoRepository repository;

    @Autowired
    private PerfilClient perfilClient;

    @Autowired
    private InventarioClient inventarioClient;

    public PrestamoResponseDTO crearPrestamo(PrestamoDTO dto) {
        log.info("Iniciando creacion de prestamo para perfil ID={} y libro ISBN={}", dto.getIdPerfil(), dto.getIsbn());
        
        // 1. Validar Perfil
        try {
            log.info("Consultando ms-perfiles...");
            PerfilDTO perfil = perfilClient.obtenerPerfil(dto.getIdPerfil());
            log.info("Perfil validado correctamente: {}", perfil.getNombre());
        } catch (FeignException.NotFound e) {
            log.warn("Perfil ID={} no encontrado", dto.getIdPerfil());
            throw new RecursoNoEncontradoException("Perfil no encontrado para el prestamo");
        } catch (FeignException e) {
            log.error("Error al conectar con ms-perfiles: {}", e.getMessage());
            throw new ServicioNoDisponibleException("Servicio de perfiles no disponible en este momento");
        }

        // 2. Validar Inventario
        try {
            log.info("Consultando ms-inventario...");
            List<Map<String, Object>> stocks = inventarioClient.buscarStockPorIsbn(dto.getIsbn());
            if (stocks == null || stocks.isEmpty()) {
                throw new RecursoNoEncontradoException("El libro no esta registrado en el inventario");
            }
            
            boolean hayStock = stocks.stream().anyMatch(s -> (Integer) s.get("stockDisponible") > 0);
            if (!hayStock) {
                log.warn("No hay stock disponible para el ISBN={}", dto.getIsbn());
                throw new RuntimeException("No hay stock disponible para este libro");
            }
            log.info("Stock validado correctamente para ISBN={}", dto.getIsbn());
            // En un flujo real avanzado, aqui se llamaria a un endpoint POST de ms-inventario para descontar 1 unidad
        } catch (FeignException e) {
            log.error("Error al conectar con ms-inventario: {}", e.getMessage());
            throw new ServicioNoDisponibleException("Servicio de inventario no disponible en este momento");
        }

        Prestamo p = new Prestamo();
        p.setIdPerfil(dto.getIdPerfil());
        p.setIsbn(dto.getIsbn());
        p.setFechaPrestamo(LocalDateTime.now());
        p.setFechaDevolucionEsperada(LocalDateTime.now().plusDays(7));
        p.setEstado("ACTIVO");
        
        Prestamo guardado = repository.save(p);
        log.info("Prestamo guardado exitosamente con ID={}", guardado.getId());
        return toResponseDTO(guardado);
    }

    public PrestamoResponseDTO renovarPrestamo(Long id) {
        Prestamo p = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));
        
        if (!"ACTIVO".equals(p.getEstado())) {
            throw new RuntimeException("Solo se pueden renovar préstamos activos");
        }
        
        p.setFechaDevolucionEsperada(p.getFechaDevolucionEsperada().plusDays(7));
        return toResponseDTO(repository.save(p));
    }

    public PrestamoResponseDTO finalizarPrestamo(Long id) {
        Prestamo p = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));
        p.setEstado("DEVUELTO");
        return toResponseDTO(repository.save(p));
    }

    public PrestamoResponseDTO obtenerPorId(Long id) {
        Prestamo prestamo = repository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Prestamo no encontrado"));
        return toResponseDTO(prestamo);
    }

    public List<PrestamoResponseDTO> listarTodos() {
        return repository.findAll().stream().map(this::toResponseDTO).toList();
    }

    public List<PrestamoResponseDTO> obtenerVencidos() {
        return repository.findByEstadoAndFechaDevolucionEsperadaBefore("ACTIVO", LocalDateTime.now())
            .stream().map(this::toResponseDTO).toList();
    }

    public List<PrestamoResponseDTO> listarPorAlumno(Long idPerfil) {
        return repository.findByIdPerfil(idPerfil).stream().map(this::toResponseDTO).toList();
    }

    private PrestamoResponseDTO toResponseDTO(Prestamo prestamo) {
        PrestamoResponseDTO dto = new PrestamoResponseDTO();
        dto.setIdPrestamo(prestamo.getId());
        dto.setIdPerfil(prestamo.getIdPerfil());
        dto.setIsbn(prestamo.getIsbn());
        dto.setFechaPrestamo(prestamo.getFechaPrestamo());
        dto.setFechaDevolucionEsperada(prestamo.getFechaDevolucionEsperada());
        dto.setEstado(prestamo.getEstado());
        return dto;
    }
}
