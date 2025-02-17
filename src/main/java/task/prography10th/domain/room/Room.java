package task.prography10th.domain.room;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import task.prography10th.domain.user.User;
import task.prography10th.global.BaseEntity;

@Entity
@Table(name = "room")
@Getter
@NoArgsConstructor
public class Room extends BaseEntity {

    @Id
    private Integer id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "host_id")
    private User host;

    private RoomDetails roomDetails;

}
