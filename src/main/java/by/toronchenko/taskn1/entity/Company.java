package by.toronchenko.taskn1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "name")
@ToString(exclude = "users")
@Entity(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long company_id;
    @Column(unique = true)
    @NotNull
    @NotBlank
    @Size(max = 16)
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
    @Builder.Default
    private Set<User> users = new HashSet<>();

    public void addUser(User user){
        users.add(user);
        user.setCompany(this);
    }

}
