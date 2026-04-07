package com.nardo.platform.business.control;

import com.nardo.platform.business.entity.*;
import com.nardo.platform.persistence.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductUpdateController {

    private final InventoryRepository inventoryRepo;

    @Autowired
    public ProductUpdateController(InventoryRepository inventoryRepo) {
        this.inventoryRepo = inventoryRepo;
    }

    public Product getProductForEdit(String productID) {
        return inventoryRepo.findById(productID).orElse(null);
    }
    
    public java.util.List<Product> getAllProductsForAdmin() {
        return inventoryRepo.findAll();
    }

    public boolean updateProduct(String id, String newName, double newPrice, String newDesc, boolean isAvailable) {
        Product product = inventoryRepo.findById(id).orElse(null);

        if (product != null) {
            if (newPrice < 0) return false;

            product.setName(newName);
            product.setPrice(newPrice);
            product.setDescription(newDesc);
            product.setAvailable(isAvailable);

            inventoryRepo.save(product);
            return true;
        }
        return false;
    }

    public boolean addNewProduct(String productID, String name, String description, double price, int quantity, int threshold) {
        if (inventoryRepo.existsById(productID) || price < 0) return false;
        
        Product newProduct = new Product(productID, name, description, price, quantity, threshold);
        inventoryRepo.save(newProduct);
        return true;
    }
}
