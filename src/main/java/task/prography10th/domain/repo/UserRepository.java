package task.prography10th.domain.repo;

import org.springframework.data.repository.Repository;
import task.prography10th.domain.user.User;

public interface UserRepository extends Repository<User, Integer> {

    void deleteAllInBatch();

    User save(User user);
}
