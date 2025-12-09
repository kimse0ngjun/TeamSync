package teamsync.backend.service.team;

import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamsync.backend.dto.team.TeamCreateRequest;
import teamsync.backend.dto.team.TeamResponse;
import teamsync.backend.entity.Team;
import teamsync.backend.entity.TeamMember;
import teamsync.backend.entity.User;
import teamsync.backend.entity.enums.UserRole;
import teamsync.backend.repository.team.TeamMemberRepository;
import teamsync.backend.repository.team.TeamRepository;
import teamsync.backend.repository.user.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final UserRepository userRepository;
    private final TeamTemplatesService teamTemplatesService;

    // 팀 생성
    public TeamResponse createTeam(User owner, TeamCreateRequest req) {

        teamTemplatesService.applyTemplates(req);

        Team team = Team.builder()
                .owner(owner)
                .name(req.getName())
                .description(req.getDescription())
                .color(req.getColor())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Team savedTeam = teamRepository.save(team);

        // 팀 Owner를 TeamMember로 등록
        TeamMember ownerMember = TeamMember.builder()
                .team(savedTeam)
                .user(owner)
                .role(UserRole.OWNER)
                .createdAt(LocalDateTime.now())
                .build();

        teamMemberRepository.save(ownerMember);

        // 추가 멤버 등록
        if (req.getMemberEmails() != null) {
            for (String email : req.getMemberEmails()) {
                addMemberByInput(savedTeam, email);
            }
        }

        return TeamResponse.from(savedTeam);
    }

    // 내가 속한 팀 목록
    public List<TeamResponse> getMyTeams(User user) {
        return teamMemberRepository.findByUser(user)
                .stream()
                .map(tm -> TeamResponse.from(tm.getTeam()))
                .collect(Collectors.toList());
    }

    // 멤버 초대/추가
    @Transactional
    public void addMembers(String teamId, List<String> inputs, User inviter) {

        Team team = teamRepository.findById(teamId).
                orElseThrow(() -> new RuntimeException("팀 ID " + teamId + " 를 찾을 수 없습니다."));

        if (!hasManagePermission(team, inviter)) {
            throw new RuntimeException("멤버를 초대할 권한이 없습니다. (OWNER 또는 ADMIN 역할이여야함.");
        }

        if (inputs != null) {
            for (String input : inputs) {
                addMemberByInput(team, input);
            }
        }
    }

    // 멤버 제거
    @Transactional
    public void removeMember(String teamId, String userIdToRemove, User requester) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("팀을 찾을 수가 없습니다."));

        if (!hasManagePermission(team, requester)) {
            throw new RuntimeException("멤버를 제거할 권한이 없습니다. (OWNER. ADMIN의 역할 권한입니다.");
        }

        User userToRemove = userRepository.findById(userIdToRemove)
                .orElseThrow(() -> new RuntimeException("제거할 사용자 ID를 찾을 수가 없습니다."));

        TeamMember teamMember = teamMemberRepository.findByTeamAndUser(team, userToRemove)
                .orElseThrow(() -> new RuntimeException("해당 팀의 멤버가 아닙니다."));

        if (teamMember.getRole() == UserRole.OWNER) {
            throw new RuntimeException("팀 OWNER는 제거할 수 없습니다.");
        }

        teamMemberRepository.delete(teamMember);
    }

    // 멤버 역할 변경
    @Transactional
    public void changeMemberRole(String teamId, String userIdToChange, UserRole newRole, User requester) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("팀을 찾을 수가 없습니다."));

        if (!hasManagePermission(team, requester)) {
            throw new RuntimeException("멤버의 역할을 변경할 권한이 없습니다. (OWNER, ADMIN의 역할 권한입니다.)");
        }

        User userToChange = userRepository.findById(userIdToChange)
                .orElseThrow(() -> new RuntimeException("역할 변경 대상 사용자 ID를 찾을 수 없습니다."));

        TeamMember teamMember = teamMemberRepository.findByTeamAndUser(team, userToChange)
                .orElseThrow(() -> new RuntimeException("해딩 팀의 멤버가 아닙니다."));

        if(teamMember.getRole() == UserRole.OWNER &&newRole != UserRole.OWNER)

        {
            throw new RuntimeException("Owner의 역할은 소유권 이전 API를 통해서만 변경해야 합니다.");
        }

        teamMember.setRole(newRole);
        teamMemberRepository.save(teamMember);
    }

    // 이메일로 멤버 추가
    private void addMemberByInput(Team team, String input) {

        if (input == null || input.isEmpty()) return;

        User user = findUserByNameOrEmail(input)
                .orElseThrow(() -> new RuntimeException("입력된 값 " + input + " 사용자를 찾을 수 없습니다."));

        // 이미 가입된 멤버 확인
        boolean exists = teamMemberRepository.findByTeamAndUser(team, user).isPresent();
        if (exists) return;

        TeamMember member = TeamMember.builder()
                .team(team)
                .user(user)
                .role(UserRole.MEMBER)
                .createdAt(LocalDateTime.now())
                .build();

        teamMemberRepository.save(member);
    }

    // 이메일 또는 이름으로 User 찾기
    private Optional<User> findUserByNameOrEmail(String input) {

        if(input.contains("@")) {
            Optional<User> userEmail = userRepository.findByEmail(input);
            if (userEmail.isPresent()) return userEmail;
        }

        return userRepository.findByName(input);
    }

    // 멤버 관리 권한 확인 (OWNER 또는 ADMIN)
    private boolean hasManagePermission(Team team, User user) {
        if (team.getOwner().equals(user)) {
            return true;
        }
        return teamMemberRepository.findByTeamAndUser(team, user)
                .map(TeamMember::getRole)
                .filter(role -> role == UserRole.ADMIN || role == UserRole.OWNER)
                .isPresent();
    }

    // OWNER 전용 - 팀 삭제
    @Transactional
    public void deleteTeam(String teamId, User requester) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("팀을 삭제할 권한이 없습니다. (Owner만 가능)"));

        teamRepository.delete(team);
    }

    // OWNER 전용 - 팀 소유권 이전
    @Transactional
    public void transferOwnership(String teamId, String newOwnerEmail, User currentOwner) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("팀을 찾을 수 없습니다."));
        if (!team.getOwner().equals(currentOwner)) {
            throw new RuntimeException("소유권을 이전할 권한이 없습니다. (현재 Owner 권한을 가진 사람만 가능)");
        }

        User newOwner = userRepository.findByEmail(newOwnerEmail)
                .orElseThrow(() -> new RuntimeException("새 Owner 사용자를 찾을 수 없습니다."));

        TeamMember newOwnerMember = teamMemberRepository.findByTeamAndUser(team, newOwner)
                .orElseThrow(() -> new RuntimeException("새 Owner는 팀의 기존 멤버이여야 합니다."));

        TeamMember currentOwnerMember = teamMemberRepository.findByTeamAndUser(team, currentOwner)
                .orElseThrow(() -> new RuntimeException("현재 Owner의 멤버 기록에 없습니다."));

        currentOwnerMember.setRole(UserRole.ADMIN);
        newOwnerMember.setRole(UserRole.OWNER);

        team.setOwner(newOwner);
        team.setUpdatedAt(LocalDateTime.now());
        teamRepository.save(team);
    }
}
