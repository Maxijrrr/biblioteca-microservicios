package cl.duoc.ms_reservas.service.impl;

import cl.duoc.ms_reservas.dto.ReservaDTO;
import cl.duoc.ms_reservas.model.Reserva;
import cl.duoc.ms_reservas.repository.ReservaRepository;
import cl.duoc.ms_reservas.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired
    private ReservaRepository repository;

    @Override
    public List<ReservaDTO> listarTodas() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public ReservaDTO obtenerPorId(Long id) {
        Reserva reserva = repository.findById(id)
                .orElseThrow(() -> new cl.duoc.ms_reservas.exception.RecursoNoEncontradoException("Reserva con ID " + id + " no encontrada"));
        return toDTO(reserva);
    }

    @Override
    public ReservaDTO guardar(ReservaDTO dto) {
        Reserva reserva = toEntity(dto);
        return toDTO(repository.save(reserva));
    }

    @Override
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new cl.duoc.ms_reservas.exception.RecursoNoEncontradoException("No se puede eliminar: Reserva con ID " + id + " no existe");
        }
        repository.deleteById(id);
    }

    private ReservaDTO toDTO(Reserva entity) {
        ReservaDTO dto = new ReservaDTO();
        dto.setIdReserva(entity.getIdReserva());
        dto.setIdPerfil(entity.getIdPerfil());
        dto.setIsbn(entity.getIsbn());
        dto.setPrioridad(entity.getPrioridad());
        return dto;
    }

    private Reserva toEntity(ReservaDTO dto) {
        Reserva entity = new Reserva();
        entity.setIdReserva(dto.getIdReserva());
        entity.setIdPerfil(dto.getIdPerfil());
        entity.setIsbn(dto.getIsbn());
        entity.setPrioridad(dto.getPrioridad());
        return entity;
    }
}
