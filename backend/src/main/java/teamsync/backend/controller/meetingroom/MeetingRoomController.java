package teamsync.backend.controller.meetingroom;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import teamsync.backend.dto.meetingroom.MeetingRoomCreateRequest;
import teamsync.backend.dto.meetingroom.MeetingRoomResponse;
import teamsync.backend.entity.MeetingRoom;
import teamsync.backend.entity.User;
import teamsync.backend.service.meetingroom.MeetingRoomService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meeting-rooms")
@Tag(name = "MeetingRoom", description = "회의방 생성, 팀 내 회의방 목록 조회, 회의방 수정 및 삭제 API")
public class MeetingRoomController {

    private final MeetingRoomService meetingRoomService;

    // 회의방 생성
    @Operation(summary = "회의방 생성", description = "회의방을 생성합니다.", security = {@SecurityRequirement(name = "securityBearer")})
    @PostMapping
    public ResponseEntity<Long> create(
            @AuthenticationPrincipal User user,
            @RequestBody MeetingRoomCreateRequest req
            ) {
        MeetingRoom room = meetingRoomService.createRoom(
                user, req.getTeamId(), req.getTitle()
        );
        return ResponseEntity.ok(room.getId());
    }

    // 팀 내 회의방 목록 조회
    @Operation(summary = "팀 내 회의방 목록 조회", description = "팀 내 회의방 목록을 조회합니다.", security = {@SecurityRequirement(name = "securityBearer")})
    @GetMapping("/team/{teamId}")
    public List<MeetingRoomResponse> list(@PathVariable String teamId) {
        return meetingRoomService.getRooms(teamId)
                .stream()
                .map(MeetingRoomResponse::from)
                .toList();
    }

    // 회의방 수정(제목 수정)
    @Operation(summary = "회의방 제목 수정", description = "회의방의 제목을 수정합니다.", security = {@SecurityRequirement(name = "securityBearer")})
    @PutMapping("/{roomId}")
    public void update(
            @PathVariable Long roomId,
            @AuthenticationPrincipal User user,
            @RequestBody String title
    ) {
        meetingRoomService.updateRoom(roomId, user, title);
    }

    // 회의방 삭제
    @Operation(summary = "회의방 삭제", description = "회의방을 삭제합니다.", security = {@SecurityRequirement(name = "securityBearer")})
    @DeleteMapping("/{roomId}")
    public void delete(
            @PathVariable Long roomId,
            @AuthenticationPrincipal User user
    ) {
        meetingRoomService.deleteRoom(roomId, user);
    }
}
