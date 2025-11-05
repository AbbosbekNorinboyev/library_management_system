package uz.pdp.library_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.library_management_system.dto.Response;
import uz.pdp.library_management_system.entity.AuthUser;
import uz.pdp.library_management_system.repository.AuthUserRepository;
import uz.pdp.library_management_system.service.UserService;
import uz.pdp.library_management_system.service.logic.RedisCacheService;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AuthUserRepository authUserRepository;
    private final RedisCacheService redisCacheService;
    private static final String CACHE_KEY = "usersLibrary";

    @Override
    public Response getAllUsers(Pageable pageable) {
        List<AuthUser> users = authUserRepository.findAll(pageable).getContent();
        redisCacheService.saveData(CACHE_KEY, users, 10, TimeUnit.MINUTES);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .success(true)
                .message("Success")
                .data(redisCacheService.getData(CACHE_KEY))
                .build();
    }
}
