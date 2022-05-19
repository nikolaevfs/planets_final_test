package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.example.dto.LordDto;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "lords")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Lord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer age;
    @OneToMany(mappedBy = "lord")
    @JsonIgnore
    private Set<Planet> planets = new HashSet<>();

    public static Lord dtoToLord(LordDto lordDTO){
        return new Lord(null, lordDTO.getName(), lordDTO.getAge(), null);
    }

    public static LordDto lordToDto(Lord lord){
        return new LordDto(lord.getName(), lord.getAge());
    }
}
