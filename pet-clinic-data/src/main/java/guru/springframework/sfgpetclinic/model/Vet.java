package guru.springframework.sfgpetclinic.model;

import java.util.Set;

/**
 * @author igorg
 * Date 02.05.2022
 */
public class Vet extends Person {
    private Set<Specialty> specialties;

    public Set<Specialty> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(Set<Specialty> specialties) {
        this.specialties = specialties;
    }
}
