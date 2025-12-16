package teamsync.backend.controller.organization;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import teamsync.backend.dto.organization.OrganizationCreateRequest;
import teamsync.backend.dto.organization.OrganizationResponse;
import teamsync.backend.dto.organization.OrganizationUpdateRequest;
import teamsync.backend.entity.Organization;
import teamsync.backend.entity.OrganizationMember;
import teamsync.backend.entity.User;
import teamsync.backend.service.organization.OrganizationService;

import java.util.List;

@RestController
@RequestMapping("/api/organization")
@RequiredArgsConstructor
@Tag(name = "Organization", description = "조직 생성, 조직 목록 조회 API")
public class OrganizationController {

    private final OrganizationService organizationService;

    // 조직 생성
    @Operation(summary = "조직 생성", description = "조직을 생성합니다.", security = {@SecurityRequirement(name = "securityBearer")})
    @PostMapping("/generate")
    @ResponseStatus(HttpStatus.CREATED)
    public OrganizationResponse createOrganization(@AuthenticationPrincipal User user, @RequestBody OrganizationCreateRequest req) {
        return organizationService.createOrganization(user, req);
    }

    // 내가 속한 조직 목록 조회
    @Operation(summary = "조직 목록 조회", description = "조직 목록 조회합니다.", security = {@SecurityRequirement(name = "securityBearer")})
    @GetMapping("/me")
    public List<OrganizationResponse> getMyOrganizations(@AuthenticationPrincipal User user) {
        return organizationService.getMyOrganizations(user);
    }

    // 조직 수정
    @Operation(summary = "조직 수정", description = "조직을 수정합니다.", security = {@SecurityRequirement(name = "securityBearer")})
    @PutMapping("{organizationId}/update")
    public OrganizationResponse updateOrganization(@AuthenticationPrincipal User user,
                                                   @PathVariable String organizationId,
                                                   @RequestBody OrganizationUpdateRequest req) {
        return organizationService.updateOrganization(organizationId, user, req);
    }

    // 조직 삭제
    @Operation(summary = "조직 삭제", description = "조직을 삭제합니다.", security = {@SecurityRequirement(name = "securityBearer")})
    @DeleteMapping("/{organizationId}")
    public void removeOrganization(@PathVariable String organizationId, @AuthenticationPrincipal User user) {
        organizationService.removeOrganization(organizationId, user);
    }
}
