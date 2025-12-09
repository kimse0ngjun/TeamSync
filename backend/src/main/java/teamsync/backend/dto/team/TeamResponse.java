package teamsync.backend.dto.team;

import lombok.Builder;
import lombok.Getter;
import teamsync.backend.entity.Team;
import teamsync.backend.entity.enums.EventColor;

import java.time.LocalDateTime;

@Getter
@Builder
public class TeamResponse {

    private String id;
    private String name;
    private String description;
    private EventColor color;
    private LocalDateTime createdAt;
    private int memberCount;

    public static TeamResponse from(Team team) {
        return TeamResponse.builder()
                .id(team.getId())
                .name(team.getName())
                .description(team.getDescription())
                .color(team.getColor())
                .createdAt(team.getCreatedAt())
                .memberCount(team.getTeamMembers().size())
                .build();
    }
}
