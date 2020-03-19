package productshop.services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import productshop.domain.entities.User;
import productshop.domain.models.binding.user.EditUserProfileBindingModel;
import productshop.domain.models.binding.user.SignUpRequest;
import productshop.domain.models.view.user.ListUserWithRolesViewModel;

import java.util.List;
import java.util.UUID;

public interface UserService extends UserDetailsService {

    ResponseEntity<?> register(SignUpRequest user);

    <T> T getByUsername(String username, Class<T> targetClass);

    User edit(String username, EditUserProfileBindingModel profile);

    List<ListUserWithRolesViewModel> findAll();

    void changeRole(String userId, String newRole);

    UserDetails loadUserById(UUID userId);
}
