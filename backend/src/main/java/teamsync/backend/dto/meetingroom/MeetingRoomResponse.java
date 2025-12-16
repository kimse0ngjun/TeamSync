package teamsync.backend.dto.meetingroom;

import lombok.Builder;
import lombok.Getter;
import teamsync.backend.entity.MeetingRoom;

import java.time.LocalDateTime;

@Getter
@Builder
public class MeetingRoomResponse {
    private Long id;
    private String title;
    private boolean isActive;
    private String lastMessage;
    private LocalDateTime lastMessageTime;

    public static MeetingRoomResponse from(MeetingRoom room) {
        return MeetingRoomResponse.builder()
                .id(room.getId())
                .title(room.getTitle())
                .isActive(room.getIsActive())
                .lastMessage(room.getLastMessage())
                .lastMessageTime(room.getLastMessageTime())
                .build();
    }
}
