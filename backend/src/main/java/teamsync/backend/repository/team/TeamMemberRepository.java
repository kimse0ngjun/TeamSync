package teamsync.backend.repository.team;

import org.springframework.data.repository.CrudRepository;
import teamsync.backend.entity.Team;
import teamsync.backend.entity.TeamMember;
import teamsync.backend.entity.User;

import java.util.List;
import java.util.Optional;

public interface TeamMemberRepository extends CrudRepository<TeamMember, String> {

    List<TeamMember> findByUser(User user);

    List<TeamMember> findByTeam(Team team);

    Optional<TeamMember> findbyIdAndUserId(String teamId, String userId);

    void deleteAllByTeam(Team team);

    Optional<TeamMember> findByTeamAndUser(Team team, User user);
}
