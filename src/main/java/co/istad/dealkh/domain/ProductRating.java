package co.istad.dealkh.domain;


import co.istad.dealkh.audit.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "dk_product_rating")
public class ProductRating extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double ratingValue = 0;

    private boolean isRated = false;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;
}
