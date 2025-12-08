package teamsync.backend.entity;


import com.fasterxml.jackson.databind.JsonNode;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;
import teamsync.backend.entity.enums.AIMessageStatus;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "ai_schedule_proposals") // AI 일정 제안
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiScheduleProposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id",  nullable = false)
    private MeetingRoom meetingRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_message_id")
    private ChatMessage chatMessage;

    private String title;

    private LocalDateTime date;

    private LocalTime time;

    private String location;

    @Column(columnDefinition = "jsonb")
    @Type(JsonType.class)
    private JsonNode tasks; // 할 일 목록 배열

    @Builder.Default
    private AIMessageStatus status = AIMessageStatus.PENDING;
}
