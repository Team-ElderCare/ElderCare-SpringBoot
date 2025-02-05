package hansung.ElderCare.Server.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
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
}
