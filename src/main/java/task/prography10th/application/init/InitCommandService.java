package task.prography10th.application.init;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import task.prography10th.application.dto.FakerResponse;
import task.prography10th.application.dto.FakerUser;
import task.prography10th.domain.repo.RoomRepository;
import task.prography10th.domain.repo.UserRepository;
import task.prography10th.domain.repo.UserRoomRepository;
import task.prography10th.domain.user.User;
import task.prography10th.domain.user.UserStatus;
import task.prography10th.presentation.dto.req.InitReq;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InitCommandService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final UserRoomRepository userRoomRepository;

    public void initializeDatabase(InitReq initReq) {
        userRepository.deleteAllInBatch();
        roomRepository.deleteAllInBatch();
        userRoomRepository.deleteAllInBatch();

        RestTemplate restTemplate = new RestTemplate();
        String fakerApiUrl = "https://fakerapi.it/api/v1/users?_seed=" + initReq.seed() +
                "&_quantity=" + initReq.quantity() + "&_locale=ko_KR";

        ResponseEntity<FakerResponse> responseEntity = restTemplate.getForEntity(fakerApiUrl, FakerResponse.class);
        FakerResponse fakerResponse = responseEntity.getBody();
//        System.out.println(fakerResponse);

        List<FakerUser> sortedFakerUsers = fakerResponse.data().stream()
                .sorted(Comparator.comparing(FakerUser::id))
                .toList();
        for (FakerUser fakerUser : sortedFakerUsers) {
            UserStatus userStatus = determineStatus(fakerUser.id());
            User user = new User(
                    fakerUser.id(),
                    fakerUser.username(),
                    fakerUser.email(),
                    userStatus
            );
            userRepository.save(user);
        }
    }


    private UserStatus determineStatus(int id) {
        if (id <= 30) {
            return UserStatus.ACTIVE;
        } else if (id <= 60) {
            return UserStatus.WAIT;
        } else {
            return UserStatus.NON_ACTIVE;
        }
    }
}
