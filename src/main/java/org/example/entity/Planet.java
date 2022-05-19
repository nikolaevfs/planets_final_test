package org.example.entity;

import lombok.*;
import org.example.dto.PlanetDto;

import javax.persistence.*;

@Entity
@Table(name = "planets")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Planet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "lord_id")
    private Lord lord;

    public static Planet dtoToPlanet(PlanetDto planetDto){
        return new Planet(null, planetDto.getName(), null);
    }

}
