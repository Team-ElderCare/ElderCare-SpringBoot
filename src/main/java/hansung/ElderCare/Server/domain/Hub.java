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
@DiscriminatorValue("Hub")
public class Hub extends Device {

    @Column(name = "hub_code")
    private String hubCode;

    @Column(name = "client_code")
    private String clientCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
