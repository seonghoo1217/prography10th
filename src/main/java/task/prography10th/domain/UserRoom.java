package task.prography10th.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import task.prography10th.domain.room.Room;
import task.prography10th.domain.user.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "userroom")
public class UserRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private UserTeam userTeam;


    public UserRoom(Room room, User user, UserTeam userTeam) {
        this.room = room;
        this.user = user;
        this.userTeam = userTeam;
    }

    public void changeTeam(UserTeam changeTeam) {
        this.userTeam = changeTeam;
    }
}
