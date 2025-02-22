package task.prography10th.domain.room;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import task.prography10th.domain.UserRoom;
import task.prography10th.domain.user.User;
import task.prography10th.global.entity.BaseEntity;

import java.util.List;

@Entity
@Table(name = "room")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host", referencedColumnName = "id")
    private User host;

    private RoomDetails roomDetails;

    @OneToMany(mappedBy = "room")
    private List<UserRoom> userRooms;

    public Room(String title, User host, RoomStatus roomStatus, RoomType roomType) {
        this.title = title;
        this.host = host;
        this.roomDetails = new RoomDetails(roomType, roomStatus);
    }

    public boolean isActive() {
        return this.roomDetails.getRoomStatus() == RoomStatus.WAIT;
    }

    public boolean isParticipate() {
        return this.userRooms.size() < this.roomDetails.getRoomType().getCapacity();
    }

    public void gameIsFinish() {
        this.roomDetails = new RoomDetails(this.roomDetails.getRoomType(), RoomStatus.FINISH);
    }
}
