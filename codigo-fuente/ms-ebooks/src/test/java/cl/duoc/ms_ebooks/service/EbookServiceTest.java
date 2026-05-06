package cl.duoc.ms_ebooks.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.ms_ebooks.dto.EbookResponseDTO;
import cl.duoc.ms_ebooks.model.Ebook;
import cl.duoc.ms_ebooks.repository.EbookRepository;

@ExtendWith(MockitoExtension.class)
class EbookServiceTest {

    @Mock
    private EbookRepository repository;

    @InjectMocks
    private EbookService service;

    @Test
    void listarActivosDevuelveDtos() {
        Ebook ebook = new Ebook();
        ebook.setId(1L);
        ebook.setTitulo("Clean Architecture");
        ebook.setAutor("Robert C. Martin");
        ebook.setIsbn("9780134494166");
        ebook.setCategoria("Software");

        when(repository.findByActivoTrue()).thenReturn(List.of(ebook));

        List<EbookResponseDTO> resultado = service.listarActivos();

        assertEquals(1, resultado.size());
        assertEquals("Clean Architecture", resultado.get(0).getTitulo());
        assertEquals("9780134494166", resultado.get(0).getIsbn());
    }
}
