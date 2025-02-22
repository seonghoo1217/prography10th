package task.prography10th.application.room;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import task.prography10th.domain.repo.RoomRepository;
import task.prography10th.domain.room.Room;
import task.prography10th.presentation.dto.res.room.RoomPageDetailRes;
import task.prography10th.presentation.dto.res.room.RoomPageRes;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomQueryService {

    private final RoomRepository roomRepository;

    public RoomPageRes findAllRoomByPage(int size, int page) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<Room> roomPage = roomRepository.findAll(pageable);

        List<RoomPageDetailRes> roomList = roomPage.stream()
                .map(room -> new RoomPageDetailRes(room.getId(), room.getTitle(), room.getHost().getId(), room.getRoomDetails().getRoomType(), room.getRoomDetails().getRoomStatus()))
                .toList();

        return new RoomPageRes(roomPage.getTotalElements(), roomPage.getTotalPages(), roomList);
    }

    /*public RoomRes findRoomById(Integer roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(BadAPIRequestException::new);

        return new RoomRes(room);
    }*/
}
