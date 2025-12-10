package teamsync.backend.dto.team;

import lombok.Builder;
import lombok.Getter;
import teamsync.backend.entity.OrganizationMember;
import teamsync.backend.entity.Team;
import teamsync.backend.entity.enums.EventColor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        List<OrganizationMember> members = Optional.ofNullable(team.getTeamMembers()).orElse(List.of());
        return TeamResponse.builder()
                .id(team.getId())
                .name(team.getName())
                .description(team.getDescription())
                .color(team.getColor())
                .createdAt(team.getCreatedAt())
                .memberCount(members.size())
                .build();
    }
}
