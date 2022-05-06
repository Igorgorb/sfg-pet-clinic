package guru.springframework.sfgpetclinic.model;

/**
 * @author igorg
 * Date 02.05.2022
 */
public class PetType extends BaseEntity {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
