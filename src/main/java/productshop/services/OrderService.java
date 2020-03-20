package productshop.services;

import org.springframework.http.ResponseEntity;
import productshop.domain.models.binding.order.OrderProductBindingModel;
import productshop.domain.models.view.order.ListOrdersViewModel;
import productshop.domain.models.view.order.OrderDetailsViewModel;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    ResponseEntity<?> addToCart(OrderProductBindingModel model);

    OrderDetailsViewModel findById(UUID id);

    List<ListOrdersViewModel> findAllOrdersWithUsers();

    List<ListOrdersViewModel> findAllFinalizedByUsername(String username);

    <T> List<T> findAllNotFinalizedByUsername(String username, Class<T> targetClass);

    ResponseEntity<?> remove(UUID id, String username);

    ResponseEntity<?> checkout(String username);
}
