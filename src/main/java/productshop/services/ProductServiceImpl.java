package productshop.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import productshop.domain.entities.Category;
import productshop.domain.entities.Product;
import productshop.domain.models.ApiResponse;
import productshop.domain.models.binding.product.AddProductBindingModel;
import productshop.domain.models.binding.product.DeleteProductBindingModel;
import productshop.domain.models.binding.product.EditProductBindingModel;
import productshop.domain.models.view.product.ListProductsViewModel;
import productshop.repositories.CategoryRepository;
import productshop.repositories.ProductRepository;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final DropboxService dropboxService;
    private final ModelMapper mapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              DropboxService dropboxService,
                              ModelMapper mapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.dropboxService = dropboxService;
        this.mapper = mapper;
    }

    @Override
    @CacheEvict(cacheNames = "products", allEntries = true)
    public ResponseEntity<?> add(AddProductBindingModel model) {
        String fileId;
        try {
            fileId = dropboxService.uploadImageAndCreateSharableLink(model.getImage());
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            return null;
        }

        Product product = mapper.map(model, Product.class);
        product.setImageUrl(fileId);
        product.getCategories().clear(); // has one null value for some reason
        product.setCategories(this.getCategoriesFromValues(model.getCategories()));

        Product entity = productRepository.saveAndFlush(product);
        return ResponseEntity.ok(new ApiResponse(true, entity.getId().toString()));
        // TODO: 19.3.2020 г. should not return it like this lmao
    }

    @Override
    public <T> T findByIdEager(UUID id, Class<T> targetClass) {
        return mapper.map(productRepository.findByIdEager(id).orElseThrow(), targetClass);
    }

    @Override
    public <T> T findById(UUID id, Class<T> targetClass) {
        return mapper.map(productRepository.findById(id).orElseThrow(), targetClass);
    }

    @Override
    @Cacheable("products")
    public List<ListProductsViewModel> findAll() {
        return productRepository.findAll()
                .stream()
                .map(p -> mapper.map(p, ListProductsViewModel.class))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    @CacheEvict(cacheNames = "products", allEntries = true)
    public ResponseEntity<?> edit(EditProductBindingModel model) {
        Product product = productRepository.findByIdEager(model.getId()).orElseThrow();
        product.setName(model.getName());
        product.setDescription(model.getDescription());
        product.setPrice(model.getPrice());
        product.setCategories(this.getCategoriesFromValues(model.getCategories()));

        productRepository.saveAndFlush(product);
        return ResponseEntity.ok(new ApiResponse(true, "Successfully edited product"));
    }

    @Override
    @CacheEvict(cacheNames = "products", allEntries = true)
    public ResponseEntity<?> delete(DeleteProductBindingModel model) {
        Product product = productRepository.findById(model.getId()).orElseThrow();
        dropboxService.deleteFileFromSharableUrl(product.getImageUrl());
        productRepository.delete(product);
        return ResponseEntity.ok(new ApiResponse(true, "Successfully deleted product"));
    }

    private Set<Category> getCategoriesFromValues(Collection<Long> values) {
        return values
                .stream()
                .map(v -> categoryRepository.findById(v).orElseThrow())
                .collect(Collectors.toSet());
    }
}
