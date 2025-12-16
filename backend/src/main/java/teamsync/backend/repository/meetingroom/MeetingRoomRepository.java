package teamsync.backend.repository.meetingroom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamsync.backend.entity.MeetingRoom;

import java.util.List;
import java.util.Optional;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Long> {
    List<MeetingRoom> findByTeamIdAndIsActiveTrue(String teamId);

    Optional<MeetingRoom> findByIdAndIsActiveTrue(Long id);
}