package productshop.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import productshop.domain.models.binding.product.AddProductBindingModel;
import productshop.domain.models.binding.product.DeleteProductBindingModel;
import productshop.domain.models.binding.product.EditProductBindingModel;
import productshop.domain.models.view.product.DeleteProductViewModel;
import productshop.domain.models.view.product.DetailsProductViewModel;
import productshop.domain.models.view.product.EditProductViewModel;
import productshop.domain.models.view.product.ListProductsViewModel;
import productshop.domain.validation.ProductValidator;
import productshop.services.CategoryService;
import productshop.services.ProductService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static productshop.config.Constants.IS_MODERATOR;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private static final String ID_ATTRIBUTE = "id";

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProductValidator productValidator;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/products/{id}")
    @ResponseBody
    public List<ListProductsViewModel> getProductsFromCategory(@PathVariable(ID_ATTRIBUTE) Long id) {
        if (id == 0) {
            return productService.findAll();
        }
        return categoryService.getProductsByCategoryId(id);
    }

    @PreAuthorize(IS_MODERATOR)
    @GetMapping("/all")
    public List<ListProductsViewModel> all() {
        return productService.findAll();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/details/{id}")
    public DetailsProductViewModel details(@PathVariable(ID_ATTRIBUTE) UUID id) {
        return productService.findByIdEager(id, DetailsProductViewModel.class);
    }

    @PreAuthorize(IS_MODERATOR)
    @GetMapping("/delete/{id}")
    public DeleteProductViewModel delete(@PathVariable(ID_ATTRIBUTE) UUID id) {
        return productService.findByIdEager(id, DeleteProductViewModel.class);
    }

    @PreAuthorize(IS_MODERATOR)
    @GetMapping("/edit/{id}")
    public EditProductViewModel edit(@PathVariable(ID_ATTRIBUTE) UUID id) {
        return productService.findByIdEager(id, EditProductViewModel.class);
    }

    @PreAuthorize(IS_MODERATOR)
    @PostMapping("/add")
    public ResponseEntity<?> addPost(@Valid @RequestBody AddProductBindingModel product) {
        return productService.add(product);
//        return "redirect:/products/details/" + productId;
    }

    @PreAuthorize(IS_MODERATOR)
    @PatchMapping("/edit")
    public ResponseEntity<?> editPost(@Valid @RequestBody EditProductBindingModel product) {
        return productService.edit(product);
    }

    @PreAuthorize(IS_MODERATOR)
    @DeleteMapping("/delete")
    public ResponseEntity<?> deletePost(@Valid @RequestBody DeleteProductBindingModel model) {
        return productService.delete(model);
    }

    @InitBinder // TODO: 19.3.2020 г. do I need this still?
    public void dataBinding(WebDataBinder binder) {
        binder.addValidators(productValidator);
    }
}
