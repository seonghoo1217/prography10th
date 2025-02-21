package task.prography10th.domain.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;
import task.prography10th.domain.user.User;

import java.util.Optional;

public interface UserRepository extends Repository<User, Integer> {

    void deleteAllInBatch();

    User save(User user);

    Page<User> findAll(Pageable pageable);

    Optional<User> findById(Integer id);
}
