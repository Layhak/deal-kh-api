package co.istad.deal_kh.dealkhapi.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "dk_reset_password")
public class ResetPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
