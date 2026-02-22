package com.inventory.controller.ui;

import com.inventory.dto.request.ProductRequestDto;
import com.inventory.dto.request.ProductSearchRequestDto;
import com.inventory.dto.response.PagedResponseDto;
import com.inventory.dto.response.ProductResponseDto;
import com.inventory.service.ProductService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductViewController {

    private final ProductService service;
	
	 @GetMapping("/")
    public String root() {
        return "redirect:/products";
    }
    public ProductViewController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/ui/products")
    public String listPage(
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
            @RequestParam(value = "msg", required = false) String msg,
            @RequestParam(required = false) String err,
            Model model
    ) {
        fillListModel(model, query, page, size, sortBy, sortDir);
        model.addAttribute("msg", msg);
        model.addAttribute("err", err);
        if (!model.containsAttribute("productForm")) {
            model.addAttribute("productForm", new ProductRequestDto());
        }
        return "products";
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        model.addAttribute("product", new ProductRequestDto());
        return "add-product";
    }

    @PostMapping("/add")
    public String createFromUI(@Valid @ModelAttribute("product") ProductRequestDto dto,
                               BindingResult bindingResult,
                               Model model) {

        if (bindingResult.hasErrors()) {
            return "add-product";
        }

        service.create(dto);
        return "redirect:/ui/products?msg=Created+successfully";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable Long id, Model model) {
        ProductResponseDto dto = service.getById(id);
        // reuse same form object type
        ProductRequestDto form = new ProductRequestDto();
        form.setSku(dto.getSku());
        form.setName(dto.getName());
        form.setDescription(dto.getDescription());
        form.setPrice(dto.getPrice());
        form.setQuantity(dto.getQuantity());

        model.addAttribute("id", id);
        model.addAttribute("product", form);
        return "product-edit";
    }

    @PostMapping("/{id}/edit")
    public String updateFromUI(@PathVariable Long id,
                               @Valid @ModelAttribute("product") ProductRequestDto dto,
                               BindingResult bindingResult,
                               Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("id", id);
            return "product-edit";
        }

        service.update(id, dto);
        return "redirect:/ui/products?msg=Updated+successfully";
    }

    @GetMapping("/{id}/stock")
    public String stockPage(@PathVariable Long id, Model model) {
        ProductResponseDto dto = service.getById(id);
        model.addAttribute("product", dto);
        model.addAttribute("qty", 1);
        return "update-stock";
    }

    @PostMapping("/{id}/stock/in")
    public String stockInFromUI(@PathVariable Long id,
                                @RequestParam Integer qty) {
        service.adjustStock(id, qty);
        return "redirect:/ui/products?msg=Stock+added+successfully";
    }

    @PostMapping("/{id}/stock/out")
    public String stockOutFromUI(@PathVariable Long id,
                                 @RequestParam Integer qty) {
        service.adjustStock(id, -qty);
        return "redirect:/ui/products?msg=Stock+removed+successfully";
    }

    @PostMapping("/{id}/delete")
    public String deleteFromUI(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/ui/products?msg=Deleted+successfully";
    }
    
    private void fillListModel(Model model, String query, Integer page, Integer size, String sortBy, String sortDir) {
        ProductSearchRequestDto req = new ProductSearchRequestDto();
        req.setQuery(query == null || query.isBlank() ? null : query.trim());
        req.setPage(page);
        req.setSize(size);
        req.setSortBy(sortBy);
        req.setSortDir(sortDir);

        PagedResponseDto<ProductResponseDto> result = service.search(req);

        model.addAttribute("result", result);
        model.addAttribute("query", query == null ? "" : query);
        model.addAttribute("page", page);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("size", size);
    }
    
}