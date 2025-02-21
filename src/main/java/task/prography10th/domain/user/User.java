package task.prography10th.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import task.prography10th.domain.UserRoom;
import task.prography10th.domain.room.Room;
import task.prography10th.global.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer fakerId;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @OneToMany(mappedBy = "host", cascade = CascadeType.PERSIST)
    private List<Room> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<UserRoom> userRooms;

    public User(Integer fakerId, String name, String email, UserStatus userStatus) {
        this.fakerId = fakerId;
        this.name = name;
        this.email = email;
        this.userStatus = userStatus;
    }

    public boolean isActive() {
        return this.userStatus == UserStatus.ACTIVE;
    }
}
