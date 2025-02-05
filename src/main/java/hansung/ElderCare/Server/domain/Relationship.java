package hansung.ElderCare.Server.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public enum Relationship {
    PARENT, CHILDREN, SIBLING, COUSIN, FRIEND, CAREGIVER, ETC
}
