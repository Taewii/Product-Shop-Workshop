package productshop.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import productshop.domain.models.binding.category.AddCategoryBindingModel;
import productshop.domain.models.binding.category.EditCategoryBindingModel;
import productshop.domain.models.view.category.DeleteCategoryViewModel;
import productshop.domain.models.view.category.EditCategoryViewModel;
import productshop.domain.models.view.category.ListCategoriesViewModel;
import productshop.services.CategoryService;

import javax.validation.Valid;
import java.util.List;

import static productshop.config.Constants.IS_MODERATOR;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PreAuthorize(IS_MODERATOR)
    @GetMapping("/all")
    public List<ListCategoriesViewModel> all() {
        return categoryService.findAll();
    }

    @PreAuthorize(IS_MODERATOR)
    @GetMapping("/edit/{id}")
    public EditCategoryViewModel edit(@PathVariable String id) {
        return categoryService.findById(id, EditCategoryViewModel.class);
    }

    @PreAuthorize(IS_MODERATOR)
    @GetMapping("/delete/{id}")
    public DeleteCategoryViewModel delete(@PathVariable String id) {
        return categoryService.findById(id, DeleteCategoryViewModel.class);
    }

//    @PreAuthorize(IS_MODERATOR)
//    @GetMapping("/add")
//    public String add(Model model) {
//        model.addAttribute(CATEGORY_ATTRIBUTE, new AddCategoryBindingModel());
//        return ADD_VIEW;
//    }

    @PreAuthorize(IS_MODERATOR)
    @PostMapping("/add")
    public ResponseEntity<?> create(@Valid @RequestBody AddCategoryBindingModel category) {
        return categoryService.add(category);
    }

    @PreAuthorize(IS_MODERATOR)
    @PatchMapping("/edit")
    public ResponseEntity<?> editPut(@Valid @RequestBody EditCategoryBindingModel category) {
        return categoryService.edit(category);
    }

    @PreAuthorize(IS_MODERATOR)
    @DeleteMapping("/delete/{category}")
    public ResponseEntity<?> deleteDelete(@PathVariable String category) {
        return categoryService.remove(category);
    }
}
