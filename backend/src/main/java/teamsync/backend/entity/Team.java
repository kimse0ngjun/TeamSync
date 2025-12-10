package teamsync.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import teamsync.backend.entity.enums.EventColor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @Id
    @UuidGenerator
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true) // mappedBy 사용 -> 양방향 매핑을 하기 위함
    private List<OrganizationMember> teamMembers = new ArrayList<>();

    private String description;

    @Enumerated(EnumType.STRING)
    private EventColor color;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
