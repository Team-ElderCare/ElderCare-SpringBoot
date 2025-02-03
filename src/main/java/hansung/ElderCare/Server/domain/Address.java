package hansung.ElderCare.Server.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Address {
    private String zipCode;

    private String building;

    private String detailedAddress;
}
