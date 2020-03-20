package productshop.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import productshop.domain.entities.Category;
import productshop.domain.models.ApiResponse;
import productshop.domain.models.binding.category.AddCategoryBindingModel;
import productshop.domain.models.binding.category.EditCategoryBindingModel;
import productshop.domain.models.view.category.ListCategoriesViewModel;
import productshop.domain.models.view.product.ListProductsViewModel;
import productshop.repositories.CategoryRepository;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<?> add(AddCategoryBindingModel model) {
        if (model == null || categoryRepository.findByName(model.getName()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse(false, "Category already exists."));
        }


        Category category = mapper.map(model, Category.class);
        categoryRepository.saveAndFlush(category);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/category/add")
                .buildAndExpand().toUri();

        return ResponseEntity
                .created(location)
                .body(new ApiResponse(true, "Category created successfully"));
    }

    @Override
    public List<ListCategoriesViewModel> findAll() {
        return categoryRepository
                .findAll()
                .stream()
                .map(c -> mapper.map(c, ListCategoriesViewModel.class))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public <T> T findById(String id, Class<T> targetClass) {
        return mapper.map(categoryRepository.findById(Long.parseLong(id)).orElseThrow(), targetClass);
    }

    @Override
    public ResponseEntity<?> edit(EditCategoryBindingModel model) {
        if (model == null || categoryRepository.findByName(model.getName()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse(false, "Category name already exists."));
        }

        Category category = categoryRepository.findById(model.getId()).orElseThrow();
        category.setName(model.getName());

        categoryRepository.saveAndFlush(category);
        return ResponseEntity.ok(new ApiResponse(true, "Category name changed successfully."));
    }

    @Override
    public ResponseEntity<?> remove(String categoryName) {
        Category category = categoryRepository.findByName(categoryName).orElseThrow();
        categoryRepository.delete(category);
        return ResponseEntity.ok(new ApiResponse(true, "Category deleted successfully."));
    }

    @Override
    @Cacheable("products")
    public List<ListProductsViewModel> getProductsByCategoryId(Long categoryId) {
        Category category = categoryRepository.findByIdEager(categoryId).orElse(null);
        if (category == null) {
            return new ArrayList<>();
        }

        return category
                .getProducts()
                .stream()
                .map(p -> mapper.map(p, ListProductsViewModel.class))
                .collect(Collectors.toUnmodifiableList());
    }
}
