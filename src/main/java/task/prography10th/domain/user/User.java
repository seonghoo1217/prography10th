package task.prography10th.domain.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import task.prography10th.domain.room.Room;
import task.prography10th.global.BaseEntity;

import java.util.ArrayList;
import java.util.List;

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
    private UserStatus userStatus;

    @OneToMany(mappedBy = "host")
    private List<Room> rooms = new ArrayList<>();
}
