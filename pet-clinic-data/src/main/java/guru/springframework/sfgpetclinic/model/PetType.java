package guru.springframework.sfgpetclinic.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author igorg
 * @data 02.05.2022
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "types")
public class PetType extends BaseEntity {

  @Builder
  public PetType(String name, Long id) {
    super(id);
    this.name = name;
  }

  @Column(name = "name")
  private String name;

  @Override
  public String toString() {
    return name;
  }

}
