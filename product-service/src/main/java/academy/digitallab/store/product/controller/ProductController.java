package academy.digitallab.store.product.controller;

import academy.digitallab.store.product.entity.Category;
import academy.digitallab.store.product.entity.Product;
import academy.digitallab.store.product.service.ProductService;
import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Slf4j
@RestController
@RequestMapping (value = "/products")
public class ProductController {

    @Autowired
    private ProductService productService ;

    // -------------------Retrieve All Products--------------------------------------------
    @GetMapping
    public ResponseEntity<List<Product>> listProduct(@RequestParam(name = "categoryId", required = false) Long categoryId){
        List<Product> products = new ArrayList<>();
        if (null ==  categoryId){
             products = productService.listAllProduct();
            if (products.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }else{
            products = productService.findByCategory(Category.builder().id(categoryId).build());
            if (products.isEmpty()){
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(products);
    }

    // -------------------Retrieve Single Product------------------------------------------
    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id) {
        log.info("Fetching Product with id {}", id);
        Product product =  productService.getProduct(id);
        if (null==product){
            log.error("Product with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    // -------------------Create a Product-------------------------------------------
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product, BindingResult result){
        log.info("Creating Product: {}",product);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Product productCreate =  productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productCreate);
    }

    //------------------Update Product--------------------------------
   @PutMapping(value = "/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product){
       log.info("Updating Product with id {}", id);
        product.setId(id);
        Product productDB =  productService.updateProduct(product);
        if (productDB == null){
            log.error("Unable to update. Product with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDB);
    }

    // ------------------- Delete a Product-----------------------------------------
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id){
        Product productDelete = productService.deleteProduct(id);
        if (productDelete == null){
            log.error("Unable to delete. Product with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDelete);
    }

    // -------------------Update Stock to Product-------------------------------------------
    @PutMapping(value = "/{id}/stock")
    public ResponseEntity<Product> updateStockProduct(@PathVariable  Long id ,@RequestParam(name = "quantity", required = true) Double quantity){
        log.info("update stock Product : {} with quantity {} ", id, quantity);
        Product product = productService.updateStock(id, quantity);
        if (product == null){
            log.error("Unable to update stock Product. Product with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String>  error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
