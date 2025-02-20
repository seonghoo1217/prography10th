package task.prography10th.domain.room;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import task.prography10th.domain.UserRoom;
import task.prography10th.domain.user.User;
import task.prography10th.global.BaseEntity;

import java.util.List;

@Entity
@Table(name = "room")
@Getter
@NoArgsConstructor
public class Room extends BaseEntity {

    @Id
    private Integer id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host", referencedColumnName = "id")
    private User host;

    private RoomDetails roomDetails;

    @OneToMany(mappedBy = "room")
    private List<UserRoom> userRooms;
}
