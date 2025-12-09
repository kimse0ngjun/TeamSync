package teamsync.backend.repository.team;

import org.springframework.data.jpa.repository.JpaRepository;
import teamsync.backend.entity.Team;
import teamsync.backend.entity.TeamMember;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, String> {

    List<TeamMember> findByTeam(Team team);

}
