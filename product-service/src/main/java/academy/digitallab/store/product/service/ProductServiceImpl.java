package academy.digitallab.store.product.service;

import academy.digitallab.store.product.entity.Category;
import academy.digitallab.store.product.entity.Product;
import academy.digitallab.store.product.repository.CategoryRepository;
import academy.digitallab.store.product.repository.ProductRepository;
import academy.digitallab.store.product.util.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl  implements ProductService{


    private final ProductRepository productRepository;


    private final CategoryRepository categoryRepository;


    @Override
    public List<Product> listAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        Product productDB = productRepository.findByNumberProduct( product.getNumberProduct () );
        if (productDB != null ){
            return productDB;
        }
        product.setStatus (Constant.STATE_CREATED );
        product.setCreatedBy ( "admin" );

        return productRepository.save ( product );


    }

    @Override
    public Product updateProduct(Product product) {
        Product productDB = getProduct(product.getId());
        if (null == productDB){
            return null;
        }
        productDB.setName(product.getName());
        productDB.setDescription(product.getDescription());
        productDB.setCategory(product.getCategory());
        productDB.setPrice(product.getPrice());
        productDB.setUpdatedBy ( "admin" );
        return productRepository.save(productDB);
    }

    @Override
    public Product deleteProduct(Long id) {
        Product productDB = getProduct(id);
        if (null == productDB){
            return null;
        }
        productDB.setStatus(Constant.STATE_DELETED);
        return productRepository.save(productDB);
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Transactional
    @Override
    public Product updateStock(Long id, Double quantity) {
        Product productDB = getProduct(id);
        if (null == productDB){
            return null;
        }
        Double stock =  productDB.getStock() + quantity;
        productDB.setStock(stock);
        return productRepository.save(productDB);
    }
    //---CATEGORY--------------------------------------------
    @Override
    public Category createCategory(Category category) {

        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {

        Category categoryDB = getCategory(category.getId());
        if (categoryDB == null){
            return null;
        }
        categoryDB.setName(category.getName());
        return categoryRepository.save(categoryDB);
    }

    @Override
    public Category getCategory(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category deleteCategory(Long id) {
        Category category = getCategory ( id );
        if (category ==null){
            return null;
        }
        categoryRepository.deleteById(id);
        return category;
    }

    @Override
    public List <Category> findCategoryAll() {

        return categoryRepository.findAll();
    }
}
