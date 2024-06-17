package co.istad.dealkh.domain;

import co.istad.dealkh.audit.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "dk_reset_passwords")
public class ResetPassword extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    Integer opt;

    Date expireDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
