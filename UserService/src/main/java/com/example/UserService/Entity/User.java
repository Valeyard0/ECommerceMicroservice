package com.example.UserService.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull(message = "Username area cannot be empty!!")
    @Size(min = 2,max = 50,message = "Name must be between 2 and 50 characters.")
    private String username;

    private String password;

    @Email(message = "Please enter a valid email.")
    private String email;

    @CreationTimestamp
    private LocalDateTime creationTime;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Address> addressList = new ArrayList<>();


    public void addRole(Role role) {
        roles.add(role);
        role.getUsers().add(this); //  iki taraf senkron
    }

    public void removeRole(Role role) {
        roles.remove(role);
        role.getUsers().remove(this); //  iki taraf senkron
    }

    public void removeAllRoles() {
        for (Role role : new HashSet<>(roles)) {
            removeRole(role); // iki tarafı da senkronlar
        }
    }

    public void addAddress(Address address){
        address.setUser(this);
        addressList.add(address);
    }

    public void removeAddress(Address address){
        address.setUser(null);
        addressList.remove(address);
    }
}
