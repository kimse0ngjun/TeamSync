package teamsync.backend.dto.team;

import lombok.Getter;
import lombok.Setter;
import teamsync.backend.entity.enums.EventColor;

import java.util.List;

@Getter
@Setter
public class TeamCreateRequest {

    private String name;
    private String description;
    private EventColor color;
    private List<String> memberEmails; // 이메일로 추가한 팀 멤버들
    private String templates; // 선택한 탬플릿
}
