package hansung.ElderCare.Server.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private Long id;

    @Column(name = "location")
    private String location;    // 기기 설치장소

    @Column(name = "device_kind")
    @Enumerated(EnumType.STRING)
    private DeviceKind deviceKind;      // 기기 종류

    @Column(name = "device_name")
    private String deviceName;      // 기기 이름
}
