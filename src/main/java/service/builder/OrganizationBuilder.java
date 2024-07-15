package service.builder;

import model.organization.Organization;

import java.net.HttpCookie;

public class OrganizationBuilder {
    Organization organization;
    public OrganizationBuilder() {
        organization = new Organization();
    }

    public Organization build() {
        return organization;
    }

    public OrganizationBuilder setName(String name) {
        organization.setName(name);
        return this;
    }

    public OrganizationBuilder setDomain(String domain) {
        organization.setDomain(domain);
        return this;
    }
}
