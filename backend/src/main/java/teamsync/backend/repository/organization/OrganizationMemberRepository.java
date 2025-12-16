package teamsync.backend.repository.organization;

import org.springframework.data.repository.CrudRepository;
import teamsync.backend.entity.Organization;
import teamsync.backend.entity.Team;
import teamsync.backend.entity.OrganizationMember;
import teamsync.backend.entity.User;

import java.util.List;
import java.util.Optional;

public interface OrganizationMemberRepository extends CrudRepository<OrganizationMember, String> {

    List<OrganizationMember> findByUser(User user);

    List<OrganizationMember> findByTeam(Team team);

    void deleteAllByTeam(Team team);

    Optional<OrganizationMember> findByTeamAndUser(Team team, User user);

    Optional<OrganizationMember> findByOrganizationAndUser(Organization organization, User user);
}
