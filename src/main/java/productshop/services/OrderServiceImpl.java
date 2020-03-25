package productshop.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import productshop.domain.entities.Order;
import productshop.domain.entities.Product;
import productshop.domain.entities.User;
import productshop.domain.enums.Authority;
import productshop.domain.models.ApiResponse;
import productshop.domain.models.binding.order.OrderProductBindingModel;
import productshop.domain.models.view.order.ListOrdersViewModel;
import productshop.domain.models.view.order.OrderDetailsViewModel;
import productshop.repositories.OrderRepository;
import productshop.repositories.ProductRepository;
import productshop.repositories.UserRepository;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static productshop.config.Constants.NO_PERMISSION_MESSAGE;


@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            ProductRepository productRepository,
                            UserRepository userRepository,
                            ModelMapper mapper) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<?> addToCart(OrderProductBindingModel model) {
        User user = userRepository.findByUsername(model.getCustomer()).orElseThrow();
        Product product = productRepository.findById(model.getProductId()).orElseThrow();

        Order order = mapper.map(model, Order.class);
        order.setCustomer(user);
        order.setProduct(product);
        order.setTotalPrice(model.getPrice().multiply(BigDecimal.valueOf(Math.floor(model.getQuantity()))));
        order.setId(null); // it maps the id to be the model.productId
        order.setOrderDate(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));

        Order entity = orderRepository.saveAndFlush(order);
        return ResponseEntity.ok(new ApiResponse(true, entity.getId().toString()));
    }

    @Override
    public OrderDetailsViewModel findById(UUID id) {
        return mapper.map(orderRepository.findByIdEager(id).orElseThrow(), OrderDetailsViewModel.class);
    }

    @Override
    public List<ListOrdersViewModel> findAllOrdersWithUsers() {
        return orderRepository
                .findAllEager()
                .stream()
                .map(o -> mapper.map(o, ListOrdersViewModel.class))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<ListOrdersViewModel> findAllFinalizedByUsername(String username) {
        return orderRepository
                .findAllFinalizedByUsernameEager(username)
                .stream()
                .map(o -> mapper.map(o, ListOrdersViewModel.class))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public <T> List<T> findAllNotFinalizedByUsername(String username, Class<T> targetClass) {
        return orderRepository.findAllNotFinalizedByUsernameEager(username)
                .stream()
                .map(o -> mapper.map(o, targetClass))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public ResponseEntity<?> remove(UUID id, String username) {
        Order order = orderRepository.findByIdEager(id).orElseThrow();

        // If the user that made the order doesn't match the currently logged in one,
        // check if the user is a moderator or not, if not throw exception
        if (!order.getCustomer().getUsername().equalsIgnoreCase(username)) {
            User user = userRepository.findByUsernameEager(username).orElseThrow();
            if (user.getAuthorities().stream()
                    .noneMatch(r -> r.getAuthority().equalsIgnoreCase("ROLE_" + Authority.MODERATOR.name()))) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse(false, NO_PERMISSION_MESSAGE));
            }
        }

        orderRepository.delete(order);
        return ResponseEntity.ok(new ApiResponse(true, "Order removed successfully."));
    }

    @Override
    public ResponseEntity<?> checkout(String username) {
        List<Order> orders = orderRepository.findAllNotFinalizedByUsernameEager(username);

        orders.forEach(order -> {
            order.setFinalized(true);
            order.setOrderDate(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));

            orderRepository.save(order);
        });

        return ResponseEntity.ok(new ApiResponse(true, "Checkout successful."));
    }
}
