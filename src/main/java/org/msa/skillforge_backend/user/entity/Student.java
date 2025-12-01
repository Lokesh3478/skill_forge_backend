package org.msa.skillforge_backend.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
    @Id
    private String id;
    @OneToOne
    @MapsId
    @JoinColumn(name="id")
    private User user;

    //should link course enrollments
}
