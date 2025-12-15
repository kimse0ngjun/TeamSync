package teamsync.backend.repository.organization;

import org.springframework.data.jpa.repository.JpaRepository;

import teamsync.backend.entity.Organization;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, String> {
    List<Organization> findByTitle(String title);
}
