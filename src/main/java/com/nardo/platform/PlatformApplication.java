/*package com.nardo.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformApplication.class, args);
    }



}*/

package com.nardo.platform;

import com.nardo.platform.business.entity.Product;
import com.nardo.platform.persistence.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformApplication.class, args);
    }


    @Bean
    public CommandLineRunner dataLoader(InventoryRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                // Adjust constructor order to match your Product.java:
                // ID, Name, Description, Price, Quantity, Threshold
                repo.save(new Product("P001", "Beef Patty", "Flaky crust", 2.50, 50, 10));
                repo.save(new Product("P002", "Coco Bread", "Buttery soft", 1.50, 30, 5));
                repo.save(new Product("P003", "Grape Soda", "Refreshing", 2.00, 40, 5));
                System.out.println("✅ Menu items loaded successfully!");
            }
        };
    }


}
