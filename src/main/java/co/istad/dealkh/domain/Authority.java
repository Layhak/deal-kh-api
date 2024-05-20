package co.istad.dealkh.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "dk_authorities")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
}