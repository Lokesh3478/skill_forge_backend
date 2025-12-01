package org.msa.skillforge_backend.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "admins")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin {

    @Id
    private String id;
    @OneToOne
    @MapsId
    @JoinColumn(name="id")
    private User user;

    //should add department of administration and work details

}
