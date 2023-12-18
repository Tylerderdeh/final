package kz.iitu.lab2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "director")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "director", cascade = CascadeType.ALL)
    private List<DirectorTranslate> directorTranslates;

    private String bin;

    @ManyToOne
    @JoinColumn(name = "establishments_id")
    private Establishments establishments;

}
