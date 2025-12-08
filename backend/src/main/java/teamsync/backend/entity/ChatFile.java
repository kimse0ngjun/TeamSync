package teamsync.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chat_files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private MeetingRoom meetingRoom;

    private String fileUrl;

    private String fileName;

    private long fileSize;

    private String fileType;
}
