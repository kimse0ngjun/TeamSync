package teamsync.backend.dto.organization;


import lombok.*;
import teamsync.backend.entity.Organization;
import teamsync.backend.entity.User;
import teamsync.backend.entity.enums.OrganizationStatus;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationResponse {

    private String id;
    private String title;
    private String description;
    private OrganizationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static OrganizationResponse from(Organization organization) {

        return OrganizationResponse.builder()
                .id(organization.getId())
                .title(organization.getTitle())
                .description(organization.getDescription())
                .status(organization.getStatus())
                .createdAt(organization.getCreatedAt())
                .updatedAt(organization.getUpdatedAt())
                .build();
    }
}
