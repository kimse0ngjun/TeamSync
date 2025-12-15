package teamsync.backend.dto.organization;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationCreateRequest {

    private String title;
    private String description;
}
