package teamsync.backend.dto.organization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationUpdateRequest {

    private String id;
    private String title;
    private String description;
}
