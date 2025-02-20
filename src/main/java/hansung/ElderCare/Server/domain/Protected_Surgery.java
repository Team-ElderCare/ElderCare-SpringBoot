package hansung.ElderCare.Server.domain;

import hansung.ElderCare.Server.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Protected_Surgery extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "protected_surgery_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "protected_id")
    private Protected Protected;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "surgery_id")
    private Surgery surgery;
}
