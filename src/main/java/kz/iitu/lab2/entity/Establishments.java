package kz.iitu.lab2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "establishments")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Establishments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String location;

    private String grade;

    @Column(name = "work_time")
    private String workTime;

    private String description;

    @OneToMany(mappedBy = "establishments", cascade = CascadeType.ALL)
    private List<Director> directors;
}
