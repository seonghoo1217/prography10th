package task.prography10th.application.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import task.prography10th.domain.repo.UserRepository;
import task.prography10th.domain.user.User;
import task.prography10th.presentation.dto.req.PageReq;
import task.prography10th.presentation.dto.res.UserPageDetailRes;
import task.prography10th.presentation.dto.res.UserPageRes;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryService {

    private final UserRepository userRepository;

    public UserPageRes findAllUserByPage(PageReq pageReq) {
        Pageable pageable = PageRequest.of(pageReq.page(), pageReq.size(), Sort.by("id"));
        Page<User> userPage = userRepository.findAll(pageable);

        List<UserPageDetailRes> userList = userPage.stream()
                .map(user -> new UserPageDetailRes(user.getId(), user.getFakerId(), user.getName(), user.getEmail(), user.getUserStatus(), user.getCreateAt(), user.getUpdateAt()))
                .toList();

        return new UserPageRes(userPage.getTotalElements(), userPage.getTotalPages(), userList);
    }
}
