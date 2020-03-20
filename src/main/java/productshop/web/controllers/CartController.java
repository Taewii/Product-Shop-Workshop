package productshop.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import productshop.domain.models.view.order.CartDetailsOrderViewModel;
import productshop.domain.models.view.order.CartViewOrderModel;
import productshop.services.OrderService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final OrderService orderService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/orders")
    public List<CartViewOrderModel> cartItems(Principal principal) {
        return orderService.findAllNotFinalizedByUsername(principal.getName(), CartViewOrderModel.class);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/details")
    public List<CartDetailsOrderViewModel> details(Principal principal) {
        return orderService.findAllNotFinalizedByUsername(principal.getName(), CartDetailsOrderViewModel.class);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/remove/{id}")
    public ResponseEntity<?> remove(@PathVariable UUID id, Principal principal) {
        return orderService.remove(id, principal.getName());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/checkout")
    public ResponseEntity<?> checkout(Principal principal) {
        return orderService.checkout(principal.getName());
    }
}
