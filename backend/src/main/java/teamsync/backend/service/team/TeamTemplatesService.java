package teamsync.backend.service.team;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamsync.backend.dto.team.TeamCreateRequest;
import teamsync.backend.entity.enums.EventColor;

@RequiredArgsConstructor
@Service
public class TeamTemplatesService {

    public void applyTemplates(TeamCreateRequest req) {

        if (req.getTemplates() == null || req.getTemplates().isEmpty()) {
            return;
        }

        switch (req.getTemplates()) {
            case "개발팀":
                req.setDescription("개발자을 위한 팀");
                req.setColor(EventColor.BLUE);
                break;
            case "디자인팀":
                req.setDescription("디자이너를 위한 팀");
                req.setColor(EventColor.RED);
                break;
            case "마케팅팀":
                req.setDescription("마케터를 위한 팀");
                req.setColor(EventColor.PURPLE);

            case "운영팀":
                req.setDescription("운영자를 위한 팀");
                req.setColor(EventColor.GREEN);
        }
    }
}
