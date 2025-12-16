package teamsync.backend.service.meetingroom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamsync.backend.entity.MeetingRoom;
import teamsync.backend.entity.MeetingRoomParticipant;
import teamsync.backend.entity.Team;
import teamsync.backend.entity.User;
import teamsync.backend.repository.meetingroom.MeetingRoomParticipantRepository;
import teamsync.backend.repository.meetingroom.MeetingRoomRepository;
import teamsync.backend.repository.team.TeamRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MeetingRoomService {

    private final MeetingRoomRepository meetingRoomRepository;
    private final MeetingRoomParticipantRepository meetingParticipantRepository;
    private final TeamRepository teamRepository;

    // 회의방 생성
    @Transactional
    public MeetingRoom createRoom(User user, String teamId, String title) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("팀을 찾을 수가 없습니다."));

        MeetingRoom room = MeetingRoom.builder()
                .team(team)
                .user(user)
                .title(title)
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        meetingRoomRepository.save(room);

        MeetingRoomParticipant participant = MeetingRoomParticipant.builder()
                .meetingRoom(room)
                .user(user)
                .updatedAt(LocalDateTime.now())
                .build();
        meetingParticipantRepository.save(participant);

        return room;
    }

    // 팀 내 회의방 목록 조회
    public List<MeetingRoom> getRooms(String teamId) {
        return meetingRoomRepository.findByTeamIdAndIsActiveTrue(teamId);
    }

    // 회의방 수정 (제목 수정)
    @Transactional
    public void updateRoom(Long roomId, User user, String title) {
        MeetingRoom room = meetingRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("회의방이 없습니다."));

        if(!room.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        room.setTitle(title);
        room.setUpdatedAt(LocalDateTime.now());
    }

    // 회의방 삭제
    @Transactional
    public void deleteRoom(Long roomId, User user) {
        MeetingRoom room = meetingRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("회의방이 없습니다."));

        if(!room.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }

        room.setIsActive(false);
        room.setUpdatedAt(LocalDateTime.now());
    }
}
