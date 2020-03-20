package productshop.services;

import org.springframework.http.ResponseEntity;
import productshop.domain.models.binding.product.AddProductBindingModel;
import productshop.domain.models.binding.product.DeleteProductBindingModel;
import productshop.domain.models.binding.product.EditProductBindingModel;
import productshop.domain.models.view.product.ListProductsViewModel;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ResponseEntity<?> add(AddProductBindingModel model);

    <T> T findByIdEager(UUID id, Class<T> targetClass);

    <T> T findById(UUID id, Class<T> targetClass);

    List<ListProductsViewModel> findAll();

    ResponseEntity<?> edit(EditProductBindingModel model);

    ResponseEntity<?> delete(DeleteProductBindingModel model);
}
