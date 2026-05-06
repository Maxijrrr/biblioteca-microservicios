 package cl.duoc.ms_perfiles.service;

import cl.duoc.ms_perfiles.MsPerfilesApplication;
import javax.naming.NameNotFoundException;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.duoc.ms_perfiles.dto.CreatePerfilDTO;
import cl.duoc.ms_perfiles.dto.PerfilResponseDTO;
import cl.duoc.ms_perfiles.exception.NotFoundException;
import cl.duoc.ms_perfiles.persistence.entity.PerfilEntity;
import cl.duoc.ms_perfiles.persistence.repository.PerfilRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PerfilService {
   
    private final PerfilRepository perfilRepository;

   
    

    public PerfilResponseDTO crearPerfil(CreatePerfilDTO dto){
        PerfilEntity obj=PerfilEntity.builder()
                            .rut(dto.rut())
                            .nombre(dto.nombre())
                            .carrera(dto.carrera())
                            .correo(dto.correo())
                            .build();

       PerfilEntity guardado = perfilRepository.save(obj);
       return new PerfilResponseDTO (
                guardado.getIdPerfil(),
                guardado.getRut(),
                guardado.getNombre(),
                guardado.getCorreo(),
                guardado.getCarrera()
        );

    }
    public PerfilResponseDTO BuscarPerfilPorRut (String rut){
        PerfilEntity perfil = perfilRepository.findByRut(rut)
        .orElseThrow(()-> new NotFoundException("Perfil no encontrado"));
        return new PerfilResponseDTO(perfil.getIdPerfil(),perfil.getRut(),perfil.getNombre()
    ,perfil.getCorreo(),perfil.getCarrera());
    }

    public PerfilResponseDTO buscarPerfilPorId(Long id) {
        PerfilEntity perfil = perfilRepository.findById(id)
        .orElseThrow(()-> new NotFoundException("Perfil no encontrado"));
        return new PerfilResponseDTO(perfil.getIdPerfil(),perfil.getRut(),perfil.getNombre()
    ,perfil.getCorreo(),perfil.getCarrera());
    }

    public List<PerfilResponseDTO> listarTodos() {
        return perfilRepository.findAll().stream()
        .map(perfil -> new PerfilResponseDTO(perfil.getIdPerfil(), perfil.getRut(), perfil.getNombre(),
                perfil.getCorreo(), perfil.getCarrera()))
        .toList();
    }

    public void eliminarPerfilPorRut (String rut){
        PerfilEntity perfil = perfilRepository.findByRut(rut)
        .orElseThrow(()-> new NotFoundException("Rut no existe"));
        perfilRepository.delete(perfil);
    }
    @Transactional
    public PerfilResponseDTO ActualizarPerfil (Long Id,CreatePerfilDTO actualizarDatos){
        PerfilEntity perfil = perfilRepository.findById(Id)
        .orElseThrow(()-> new NotFoundException("Perfil no encontrado"));

        perfil.setRut(actualizarDatos.rut());
        perfil.setNombre(actualizarDatos.nombre());
        perfil.setCorreo(actualizarDatos.correo());
        perfil.setCarrera(actualizarDatos.carrera());
        perfil=perfilRepository.save(perfil);

        return new PerfilResponseDTO(
            perfil.getIdPerfil(),
            perfil.getRut(),
            perfil.getNombre(),
            perfil.getCorreo(),
            perfil.getCarrera()
        ) ;

        
    }
}
