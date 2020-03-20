package productshop.services;

import org.apache.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import productshop.config.Constants;
import productshop.domain.entities.Role;
import productshop.domain.entities.User;
import productshop.domain.enums.Authority;
import productshop.domain.models.ApiResponse;
import productshop.domain.models.binding.user.EditUserProfileBindingModel;
import productshop.domain.models.binding.user.SignUpRequest;
import productshop.domain.models.view.user.ListUserWithRolesViewModel;
import productshop.repositories.RoleRepository;
import productshop.repositories.UserRepository;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Service("UserServiceImpl")
@Transactional
public class UserServiceImpl implements UserService {

    private static final List<String> ROLES = new ArrayList<>() {{
        add("ROOT");
        add("ADMIN");
        add("MODERATOR");
        add("USER");
    }};

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

    @Autowired
    public UserServiceImpl(RoleRepository roleRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           ModelMapper mapper) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsernameEager(username)
                .orElseThrow(() -> new UsernameNotFoundException(Constants.USERNAME_NOT_FOUND));
    }

    @Transactional
    @Override
    public UserDetails loadUserById(UUID userId) {
        return userRepository.findByIdWithRoles(userId)
                .orElseThrow(() -> new UsernameNotFoundException(Constants.USERNAME_NOT_FOUND));
    }

    @Override
    public ResponseEntity<?> register(SignUpRequest model) {
        if (userRepository.findByUsernameEager(model.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "User already exists."));
        }

        User user = mapper.map(model, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userRepository.count() == 0) {
            user.setRoles(this.getInheritedRolesFromRole("ROOT"));
        } else {
            user.setRoles(this.getInheritedRolesFromRole("USER"));
        }

        userRepository.saveAndFlush(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/user/register")
                .buildAndExpand().toUri();

        return ResponseEntity
                .created(location)
                .body(new ApiResponse(true, "User created successfully"));
    }

    @Override
    public <T> T getByUsername(String username, Class<T> targetClass) {
        return mapper.map(userRepository.findByUsername(username).orElseThrow(), targetClass);
    }

    @Override
    public ResponseEntity<?> edit(String username, EditUserProfileBindingModel model) {
        User user = userRepository.findByUsername(username).orElseThrow();
        if (!passwordEncoder.matches(model.getOldPassword(), user.getPassword())) {
            return null;
        }

        user.setPassword(passwordEncoder.encode(model.getNewPassword()));
        user.setEmail(model.getEmail());

        userRepository.saveAndFlush(user);
        return ResponseEntity.ok(new ApiResponse(true, "Profile edited successfully."));
    }

    @Override
    public List<ListUserWithRolesViewModel> findAll() {
        return userRepository.findAll().stream()
                .map(u -> {
                    ListUserWithRolesViewModel model = mapper.map(u, ListUserWithRolesViewModel.class);
                    model.setRoles(u.getRoles()
                            .parallelStream()
                            .map(r -> r.authorityAsEnum().name())
                            .collect(Collectors.toList()));
                    model.setMainRole(this.getMainRole(u.getRoles()));
                    return model;
                }).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public ResponseEntity<?> changeRole(String userId, String newRole) {
        if (newRole.equalsIgnoreCase("root")) {
            return ResponseEntity.status(HttpStatus.SC_FORBIDDEN)
                    .body(new ApiResponse(false, "Cannot change role to root."));
        }

        User user = userRepository.findById(UUID.fromString(userId)).orElseThrow();
        user.setRoles(this.getInheritedRolesFromRole(newRole));
        userRepository.saveAndFlush(user);
        return ResponseEntity.ok(new ApiResponse(true, "Role changed successfully"));
    }

    private Authority getMainRole(Set<Role> roles) {
        List<Authority> authorities = Arrays.asList(Authority.values());

        int bestRoleIndex = Integer.MAX_VALUE;
        for (Role role : roles) {
            int roleIndex = authorities.indexOf(role.authorityAsEnum());
            if (roleIndex != -1 && roleIndex < bestRoleIndex) {
                bestRoleIndex = roleIndex;
            }
        }

        return authorities.get(bestRoleIndex);
    }

    private Set<Role> getInheritedRolesFromRole(String role) {
        Set<Role> roles = new HashSet<>();
        for (int i = ROLES.indexOf(role.toUpperCase()); i < ROLES.size(); i++) {
            roles.add(roleRepository.findById(i + 1L).orElseThrow());
        }

        return roles;
    }
}
