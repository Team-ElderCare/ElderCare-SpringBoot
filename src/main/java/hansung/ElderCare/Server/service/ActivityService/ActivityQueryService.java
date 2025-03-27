package hansung.ElderCare.Server.service.ActivityService;


import hansung.ElderCare.Server.dto.ActivityDTO.ActivityResponseDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface ActivityQueryService {

    public ActivityResponseDTO.getActivityResponseDTO getDailyActivity(Long userId, LocalDate date);
}
