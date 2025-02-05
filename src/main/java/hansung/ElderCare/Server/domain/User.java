package hansung.ElderCare.Server.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;    // 기본키

    @Column(name = "user_kakaoid")
    private Long kakaoId;       // 카카오 ID

    @Column(name = "username")
    private String userName;    // 사용자 이름

    @Enumerated(EnumType.STRING)
    @Column(name = "user_relationship")
    private RelationShip relationShip;  // 보호대상자와의 관계

    @Column(name = "user_image_url")
    private String userImageUrl;    // 사용자 프사 url

}
