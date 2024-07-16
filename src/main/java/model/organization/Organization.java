package model.organization;

import lombok.Getter;
import lombok.Setter;
import model.microsoftlist.MicrosoftList;
import model.user.User;

import java.util.HashSet;

@Getter
@Setter
public class Organization {
    private String name;
    private String domain;
    private HashSet<User> users;
    private HashSet<MicrosoftList> lists;

    public Organization() {
        users = new HashSet<>();
        lists = new HashSet<>();
    }
}
