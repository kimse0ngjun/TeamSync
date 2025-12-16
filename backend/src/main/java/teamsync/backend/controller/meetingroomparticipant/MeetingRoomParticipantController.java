package teamsync.backend.controller.meetingroomparticipant;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import teamsync.backend.entity.User;
import teamsync.backend.service.meetingroom.MeetingRoomParticipantService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/meeting-rooms/partcipant")
@Tag(name = "MeetingRoomParticipant", description = "회의방 참여, 회의방 나가기 API")
public class MeetingRoomParticipantController {

    public final MeetingRoomParticipantService meetingRoomParticipantService;

    // 회의방 참여
    @Operation(summary = "회의방 참여", description = "유저가 회의방에 참여합니다.", security = {@SecurityRequirement(name = "securityBearer")})
    @PostMapping("/{roomId}")
    public void join(
            @PathVariable Long roomId,
            @AuthenticationPrincipal User user
    ) {
        meetingRoomParticipantService.joinRoom(roomId, user.getId());
    }

    // 회의방 나가기
    @Operation(summary = "회의방 나가기", description = "유저가 회의방에서 나갑니다.", security = {@SecurityRequirement(name = "securityBearer")})
    @DeleteMapping("/{roomId}")
    public void leave(@PathVariable Long roomId, @AuthenticationPrincipal User user) {
        meetingRoomParticipantService.leaveRoom(roomId, user.getId());
    }
}
