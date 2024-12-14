package tenkacheva.work.app.dtos;

import tenkacheva.work.app.models.Role;

public class PersonDTO {
    private Long id;
    private String name;
    private Role role;

    public PersonDTO() {

    }

    public PersonDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
