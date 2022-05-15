package guru.springframework.sfgpetclinic.model;

/**
 * @author igorg
 * Date 15.05.2022
 */
public class Specialty extends BaseEntity {
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
