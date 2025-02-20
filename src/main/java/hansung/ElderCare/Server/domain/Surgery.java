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
public class Surgery extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "surgery_id")
    private Long id;

    @Column(name = "surgery_name")
    private String name;
}
