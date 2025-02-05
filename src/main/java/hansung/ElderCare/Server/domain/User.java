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

    @Column(name = "user_email")
    private String email;       // 사용자 이메일

    @Column(name = "username")
    private String userName;    // 사용자 이름

    @Column(name = "user_phone_number")
    private String phoneNumber;     // 사용자 전화번호

    @Column(name = "user_relationship")
    @Enumerated(EnumType.STRING)
    private Relationship relationship;        // 사용자와 보호대상자 간 관계

    @Column(name = "user_image_url")
    private String userImageUrl;    // 사용자 프사 url

}
