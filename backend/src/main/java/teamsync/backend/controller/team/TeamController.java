package teamsync.backend.controller.team;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import teamsync.backend.dto.team.TeamCreateRequest;
import teamsync.backend.dto.team.TeamResponse;
import teamsync.backend.entity.User;
import teamsync.backend.entity.enums.OrganizationRole;
import teamsync.backend.service.team.TeamService;

import java.util.List;

@RestController
@RequestMapping("/api/team")
@RequiredArgsConstructor
@Tag(name = "Team", description = "팀 생성, 조회, 초대, 멤버 삭제, 멤버 역할 변경, OWNER 전용 - 팀 삭제, 소유권 이전 API")
public class TeamController {

    private final TeamService teamService;

    // 팀 생성
    @Operation(summary = "팀 생성", description = "팀 이름, 설명, Color, 이메일로 추가한 멤버, 탬플릿을 제공합니다.", security = {@SecurityRequirement(name = "securityBearer")})
    @PostMapping("/{organizationId}")
    @ResponseStatus(HttpStatus.CREATED)
    public TeamResponse createTeam(@RequestBody TeamCreateRequest req,
                                   @PathVariable String organizationId,
                                   @AuthenticationPrincipal User owner) {
        return teamService.createTeam(owner, organizationId, req);
    }

    // 팀 조회
    @Operation(summary = "팀 조회", description = "팀에 소속된 멤버들을 조회합니다.", security = {@SecurityRequirement(name = "securityBearer")})
    @GetMapping("/me")
    public List<TeamResponse> getMyTeams(
           @AuthenticationPrincipal User user) {
        return teamService.getMyTeams(user);
    }

    // 팀 초대
    @Operation(summary = "팀 초대", description = "조직 팀원을 이름 또는 이메일로 초대합니다.", security = {@SecurityRequirement(name = "securityBearer")})
    @PostMapping("/{teamId}/members")
    public void addMembers(
            @PathVariable String teamId,
            @RequestBody List<String> inputs,
            @AuthenticationPrincipal User inviter) {

        teamService.addMembers(teamId, inputs, inviter);
    }

    // 멤버 삭제
    @Operation(summary = "멤버 삭제", description = "조직 팀원을 삭제합니다.", security = {@SecurityRequirement(name = "securityBearer")})
    @DeleteMapping("/{teamId}/members/{userid}")
    public void removeMembers(
            @PathVariable String teamId,
            @PathVariable String userId,
            @AuthenticationPrincipal User requester){
        teamService.removeMembers(teamId, userId, requester);
    }

    // 멤버 역할 변경
    @Operation(summary = "멤버 역할 변경", description = "Owner, Admin의 권한으로 Member 역할을 변경합니다.", security = {@SecurityRequirement(name = "securityBearer")})
    @PatchMapping("/{teamId}/members/{userId}/role")
    public void changeMemberRole(
            @PathVariable String teamId,
            @PathVariable String userId,
            @RequestParam OrganizationRole Role,
            @AuthenticationPrincipal User requester
            ) {
        teamService.changeMemberRole(teamId, userId, Role, requester);
    }

    // OWNER 전용 - 팀 삭제
    @Operation(summary = "Owner 전용 - 팀 삭제", description = "Owner 역할 담당자가 팀을 삭제합니다.", security = {@SecurityRequirement(name = "securityBearer")})
    @DeleteMapping("/{teamId}")
    public void deleteTeam(
            @PathVariable String teamId,
            @AuthenticationPrincipal User requester) {
        teamService.deleteTeam(teamId, requester);
    }

    // OWNER 전용 - 팀 소유권 넘기기
    @Operation(summary = "Owner 전용 - 팀 소유권 이전", description = "Owner 역할 담당자가 팀 소유권 넘길 수 있습니다.", security = {@SecurityRequirement(name = "securityBearer")})
    @PatchMapping("/{teamId}/transfer")
    public void transferOwnership(
            @PathVariable String teamId,
            @RequestParam String newOwnerEmail,
            @AuthenticationPrincipal User currentOwner) {
        teamService.transferOwnership(teamId, newOwnerEmail, currentOwner);
    }
}
