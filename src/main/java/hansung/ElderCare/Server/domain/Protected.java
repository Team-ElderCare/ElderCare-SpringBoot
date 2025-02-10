package hansung.ElderCare.Server.domain;

import hansung.ElderCare.Server.domain.enums.BloodType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Protected {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "protected_id")
    private Long id;        // 보호대상자 기본키

    @Column(name = "protected_name")
    private String name;       // 보호대상자 이름

    @Column(name = "protected_birthDate")
    private LocalDate birthDate;        // 보호대상자 생년월일

    @Column(name = "protected_nickname")
    private String nickname;       // 보호대상자 닉네임

    @Column(name = "protected_phone_number")
    private String phoneNumber;     // 보호대상자 전화번호

    @Embedded
    @Column(name = "protected_address")
    private Address address;    // 보호대상자 주소

    @Column(name = "protected_height")
    private int height;     // 보호대상자 키

    @Column(name = "protected_weight")
    private int weight;     // 보호대상자 몸무게

    @Enumerated(EnumType.STRING)
    @Column(name = "protected_blood_type")
    private BloodType bloodType;    // 보호대상자 혈액형

    @Column(name = "protected_image_url")
    private String protectedImageUrl;    // 사용자 프사 url
}
