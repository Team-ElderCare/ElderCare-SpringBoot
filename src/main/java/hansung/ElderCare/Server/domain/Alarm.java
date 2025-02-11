package hansung.ElderCare.Server.domain;

import hansung.ElderCare.Server.domain.enums.Days;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "alarm_name")
    private String name;

    @Column(name = "time")
    @Temporal(TemporalType.TIME)
    private List<LocalTime> alarmTimes = new ArrayList<>();        // 알람 시간

    @Column(name = "day")
    @Enumerated(EnumType.STRING)
    private Days day;       // 알람 요일

    @Column(name = "is_taken")
    private boolean isTaken;        // 약물 복용 여부

    @Column(name = "status")
    private boolean status;     // 알람 활성화 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "protected_id")
    private Protected Protected;
}
