package co.istad.dealkh.audit;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * The Auditable class is a base class for entity auditing. It captures the audit information
 * for entities such as the creation date, last modification date, and the users who created
 * and last modified the entities.
 *
 * <p>This class uses the following annotations:
 * <ul>
 * <li>{@code @Getter} and {@code @Setter} from Lombok to generate getter and setter methods.</li>
 * <li>{@code @NoArgsConstructor} from Lombok to generate a no-argument constructor.</li>
 * <li>{@code @MappedSuperclass} from JPA to indicate that this class is a base class whose properties are inherited by other entity classes.</li>
 * <li>{@code @EntityListeners} to specify that the {@code AuditingEntityListener} should be used for auditing purposes.</li>
 * <li>{@code @Temporal(TemporalType.TIMESTAMP)} to indicate that the date should be stored with a timestamp.</li>
 * <li>{@code @CreatedDate} to indicate that the field should be populated with the creation date of the entity.</li>
 * <li>{@code @LastModifiedDate} to indicate that the field should be populated with the last modification date of the entity.</li>
 * <li>{@code @CreatedBy} to indicate that the field should be populated with the username of the creator of the entity.</li>
 * <li>{@code @LastModifiedBy} to indicate that the field should be populated with the username of the last modifier of the entity.</li>
 * </ul>
 * </p>
 *
 * <p>The fields in this class are:
 * <ul>
 * <li>{@code createdAt} - The timestamp when the entity was created.</li>
 * <li>{@code updatedAt} - The timestamp when the entity was last modified.</li>
 * <li>{@code createdBy} - The username of the user who created the entity.</li>
 * <li>{@code updatedBy} - The username of the user who last modified the entity.</li>
 * </ul>
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private LocalDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;

}