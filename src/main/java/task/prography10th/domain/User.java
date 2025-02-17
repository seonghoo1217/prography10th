package task.prography10th.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import task.prography10th.global.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    private Integer id;

    private Integer fakerId;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private Status status;
}
