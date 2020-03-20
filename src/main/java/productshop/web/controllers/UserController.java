package productshop.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import productshop.domain.models.binding.user.EditUserProfileBindingModel;
import productshop.domain.models.view.user.EditUserProfileViewModel;
import productshop.domain.models.view.user.ListUserWithRolesViewModel;
import productshop.domain.models.view.user.UserProfileViewModel;
import productshop.domain.validation.UserValidator;
import productshop.services.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static productshop.config.Constants.IS_ADMIN;

@Controller
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private static final String ID_ATTRIBUTE = "id";
    private static final String ROLE_ATTRIBUTE = "role";

    private final UserService userService;
    private final UserValidator userValidator;

    @GetMapping("/profile")
    public UserProfileViewModel profile(Principal principal) {
        return userService.getByUsername(principal.getName(), UserProfileViewModel.class);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/edit")
    public EditUserProfileViewModel profileEdit(Principal principal) { // TODO: 19.3.2020 г. does principal work?
        return userService.getByUsername(principal.getName(), EditUserProfileViewModel.class);
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/edit")
    public ResponseEntity<?> profileEditPut(@Valid @RequestBody EditUserProfileBindingModel profile, Principal principal) {
        return userService.edit(principal.getName(), profile);
    }

    @PreAuthorize(IS_ADMIN)
    @GetMapping("/all")
    public List<ListUserWithRolesViewModel> all() {
        return userService.findAll();
    }

    @PreAuthorize(IS_ADMIN)
    @PostMapping("/set-{role}/{id}")
    public ResponseEntity<?> changeUserRole(@PathVariable(ID_ATTRIBUTE) String id, @PathVariable(ROLE_ATTRIBUTE) String role) {
        return userService.changeRole(id, role);
    }

    @InitBinder // TODO: 19.3.2020 г. need?
    public void dataBinding(WebDataBinder binder) {
        binder.addValidators(userValidator);
    }
}
