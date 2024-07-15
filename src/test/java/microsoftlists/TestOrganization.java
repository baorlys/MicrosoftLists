package microsoftlists;

import model.constants.Permission;
import model.organization.Organization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.builder.OrganizationBuilder;
import service.OrganizationService;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestOrganization {
    Organization organization;

    @BeforeEach
    void setUp() {
        organization = new OrganizationBuilder()
                .setName("Microsoft")
                .setDomain("microsoft.com")
                .build();
    }

    @Test
    // Tests default constructor
    void testOrganization() {
        assertEquals("Microsoft",organization.getName());
        assertEquals("microsoft.com",organization.getDomain());
        assertEquals(0,organization.getUsers().size());
        assertEquals(0,organization.getLists().size());
    }

}
