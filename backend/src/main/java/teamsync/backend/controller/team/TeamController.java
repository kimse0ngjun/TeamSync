package teamsync.backend.controller.team;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import teamsync.backend.dto.team.TeamCreateRequest;
import teamsync.backend.dto.team.TeamMemberAddRequest;
import teamsync.backend.dto.team.TeamResponse;
import teamsync.backend.entity.Team;
import teamsync.backend.entity.User;
import teamsync.backend.service.team.TeamService;

import java.security.Security;
import java.util.List;

@RestController
@RequestMapping("/api/team")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    // 팀 생성
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeamResponse createTeam(@RequestBody TeamCreateRequest req, @AuthenticationPrincipal User currentUser) {
        return teamService.createTeam(currentUser, req);
    }

    // 팀 초대
    @PostMapping("/{teamid}/member")
    public String addMembers(
            @PathVariable String teamId,
            @RequestBody TeamMemberAddRequest req,
            @AuthenticationPrincipal User currentUser) {

        teamService.addMemberByInput(teamId, req.getMemberInputs(), currentUser);
    }
}
