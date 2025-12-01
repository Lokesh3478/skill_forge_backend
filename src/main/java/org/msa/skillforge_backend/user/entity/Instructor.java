package org.msa.skillforge_backend.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "instructors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Instructor {

    @Id
    private String id;
    @OneToOne
    @MapsId
    @JoinColumn(name="id")
    private User user;

    //should add dept details and course uploaded details
}
