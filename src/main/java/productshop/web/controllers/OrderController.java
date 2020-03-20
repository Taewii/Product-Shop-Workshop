package productshop.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import productshop.config.Constants;
import productshop.domain.models.binding.order.OrderProductBindingModel;
import productshop.domain.models.view.order.ListOrdersViewModel;
import productshop.domain.models.view.order.OrderDetailsViewModel;
import productshop.domain.models.view.order.OrderProductViewModel;
import productshop.services.OrderService;
import productshop.services.ProductService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private static final String ID_ATTRIBUTE = "id";

    private final OrderService orderService;
    private final ProductService productService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/product/{id}")
    public OrderProductViewModel orderDetails(@PathVariable(ID_ATTRIBUTE) UUID id, Principal principal) {
        OrderProductViewModel product = productService.findById(id, OrderProductViewModel.class);
        product.setCustomer(principal.getName());
        return product;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/details/{id}")
    public OrderDetailsViewModel details(@PathVariable(ID_ATTRIBUTE) UUID id) {
        return orderService.findById(id);
    }

    @PreAuthorize(Constants.IS_ADMIN)
    @GetMapping("/all")
    public List<ListOrdersViewModel> all() {
        return orderService.findAllOrdersWithUsers();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mine")
    public List<ListOrdersViewModel> mine(Principal principal) {
        return orderService.findAllFinalizedByUsername(principal.getName());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/order")
    public ResponseEntity<?> addToCart(@Valid @RequestBody OrderProductBindingModel order) {
        return orderService.addToCart(order);
    }
}
