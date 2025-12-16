package teamsync.backend.service.meetingroom;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamsync.backend.entity.MeetingRoom;
import teamsync.backend.entity.MeetingRoomParticipant;
import teamsync.backend.entity.User;
import teamsync.backend.repository.meetingroom.MeetingRoomParticipantRepository;
import teamsync.backend.repository.meetingroom.MeetingRoomRepository;
import teamsync.backend.repository.user.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MeetingRoomParticipantService {

    private final MeetingRoomRepository meetingRoomRepository;
    private final MeetingRoomParticipantRepository meetingParticipantRepository;
    private final UserRepository userRepository;

    // 회의방 참여
    @Transactional
    public void joinRoom(Long roomId, String userId) {
        if (meetingParticipantRepository.existsByMeetingRoomIdAndUserId(roomId, userId)){
            return;
        }

        MeetingRoom room = meetingRoomRepository.findByIdAndIsActiveTrue(roomId)
                .orElseThrow(() -> new RuntimeException("회의방이 없습니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다."));

        MeetingRoomParticipant participant = MeetingRoomParticipant.builder()
                .meetingRoom(room)
                .user(user)
                .updatedAt(LocalDateTime.now())
                .build();

        meetingParticipantRepository.save(participant);
    }

    // 회의방 나가기
    @Transactional
    public void leaveRoom(Long roomId, String userId) {
        meetingParticipantRepository.deleteByMeetingRoomIdAndUserId(roomId, userId);
    }
}
