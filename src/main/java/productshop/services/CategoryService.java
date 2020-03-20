package productshop.services;

import org.springframework.http.ResponseEntity;
import productshop.domain.models.binding.category.AddCategoryBindingModel;
import productshop.domain.models.binding.category.EditCategoryBindingModel;
import productshop.domain.models.view.category.ListCategoriesViewModel;
import productshop.domain.models.view.product.ListProductsViewModel;

import java.util.List;

public interface CategoryService {

    ResponseEntity<?> add(AddCategoryBindingModel model);

    List<ListCategoriesViewModel> findAll();

    <T> T findById(String id, Class<T> targetClass);

    ResponseEntity<?> edit(EditCategoryBindingModel model);

    ResponseEntity<?> remove(String categoryName);

    List<ListProductsViewModel> getProductsByCategoryId(Long categoryId);
}
