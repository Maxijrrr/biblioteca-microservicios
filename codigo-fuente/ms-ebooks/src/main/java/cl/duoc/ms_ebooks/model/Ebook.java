package cl.duoc.ms_ebooks.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ebooks")
@Data
public class Ebook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String titulo;
    private String autor;
    private String categoria;
    
    @Column(unique = true)
    private String isbn;
    
    private boolean disponible = true;
    private boolean activo = true; 
}