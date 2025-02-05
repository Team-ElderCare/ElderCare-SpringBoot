package hansung.ElderCare.Server.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UA_UD_UP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UA_UD_UP_id")
    private Long id;      // UA_UD_UP 기본키

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;      // User 외래키

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "protected_id")
    private Protected Protected;        // Protected 외래키
}
