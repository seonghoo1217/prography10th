package task.prography10th.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import task.prography10th.domain.repo.UserRoomRepository;
import task.prography10th.domain.user.User;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserRoomQueryService {

    private final UserRoomRepository userRoomRepository;

    public boolean existsParticipant(User user) {
        return userRoomRepository.existsByUser(user);
    }
}
