package kw.zeropick.product.controller;

import kw.zeropick.product.service.ProductSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductSearchController {
    private final ProductSearchService productSearchServiceService;

    @PostMapping("/update-all")
    public ResponseEntity<String> updateAllProducts() {
        productSearchServiceService.updateAllProducts();
        return ResponseEntity.ok("Product information updated successfully.");
    }
}
