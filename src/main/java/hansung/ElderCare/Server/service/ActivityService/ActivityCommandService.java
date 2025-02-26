package hansung.ElderCare.Server.service.ActivityService;

import hansung.ElderCare.Server.dto.ActivityDTO.ActivityRequestDTO;
import hansung.ElderCare.Server.dto.ActivityDTO.ActivityResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface ActivityCommandService {

    public ActivityResponseDTO.ActivityDTO addActivity(ActivityRequestDTO.AddActivityRequestDTO request);

}
