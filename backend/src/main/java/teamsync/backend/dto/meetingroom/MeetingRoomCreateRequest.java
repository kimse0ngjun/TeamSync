package teamsync.backend.dto.meetingroom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MeetingRoomCreateRequest {
    private String teamId;
    private String title;
}
