package teamsync.backend.service.organization;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamsync.backend.dto.organization.OrganizationCreateRequest;
import teamsync.backend.dto.organization.OrganizationResponse;
import teamsync.backend.entity.Organization;
import teamsync.backend.entity.OrganizationMember;
import teamsync.backend.entity.Team;
import teamsync.backend.entity.User;
import teamsync.backend.entity.enums.OrganizationRole;
import teamsync.backend.entity.enums.OrganizationStatus;
import teamsync.backend.repository.organization.OrganizationMemberRepository;
import teamsync.backend.repository.organization.OrganizationRepository;
import teamsync.backend.repository.team.TeamRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMemberRepository organizationMemberRepository;
    private final TeamRepository teamRepository;

    // 조직 생성
    @Transactional
    public OrganizationResponse createOrganization(User user, OrganizationCreateRequest req) {

        Organization organization = Organization.builder()
                .user(user)
                .title(req.getTitle())
                .description(req.getDescription())
                .status(OrganizationStatus.ACTIVATE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Organization savedOrganization = organizationRepository.save(organization);

        Team OrganizationTeam = Team.builder()
                .name(organization.getTitle())
                .description(organization.getDescription())
                .owner(user)
                .organization(savedOrganization)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Team savedTeam = teamRepository.save(OrganizationTeam);

        organizationMemberRepository.save(
                OrganizationMember.builder()
                        .organization(savedOrganization)
                        .user(user)
                        .team(savedTeam)
                        .role(OrganizationRole.OWNER)
                        .build()
        );

        return OrganizationResponse.from(savedOrganization);
    }

    // 내가 속한 조직 목록 조회
    public List<OrganizationResponse> getMyOrganizations(User user) {
        return organizationMemberRepository.findByUser(user).stream()
                .map(OrganizationMember::getOrganization)
                .filter(org -> org != null)
                .distinct()
                .map(OrganizationResponse::from)
                .collect(Collectors.toList());
    }
}
