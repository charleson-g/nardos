package com.nardo.platform.business.control;

import com.nardo.platform.business.entity.PickupSlot;
import com.nardo.platform.business.entity.Product;
import com.nardo.platform.persistence.InventoryRepository;
import com.nardo.platform.persistence.PickupSlotRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class DataSeederControl {

    @Bean
    public CommandLineRunner dataLoader(InventoryRepository repo, PickupSlotRepository slotRepo) {
        return args -> {
            if (repo.count() == 0) {
                // ID, Name, Description, Price, Quantity, Threshold
                repo.save(new Product("P001", "Regular Hot Dog", "Classic bun and sausage", 2.50, 50, 10));
                repo.save(new Product("P002", "Corn Dog", "Made with corn bread", 3.00, 40, 10));
                repo.save(new Product("P003", "Pit Bull", "Made with grotto bread", 4.00, 30, 5));
                repo.save(new Product("P004", "Sugar Dog", "Made with sugar bun", 3.00, 30, 5));
                repo.save(new Product("P005", "Raisin Dog", "Made with raisin bread", 3.50, 25, 5));
                repo.save(new Product("P006", "Beef and Chicken Burger", "Double patty combo", 6.00, 20, 5));
                repo.save(new Product("P007", "Chicken Nugget Sandwich", "Crispy nuggets on bread", 4.50, 25, 5));
                repo.save(new Product("P008", "Mega Sausage", "Jumbo sausage special", 5.00, 30, 5));
                repo.save(new Product("P009", "Nuggets & Fries", "Chicken nuggets with a side of fries", 5.50, 40, 10));
                repo.save(new Product("P010", "Cheezy Fries", "Fries smothered in cheese", 3.50, 40, 10));
                repo.save(new Product("P011", "Turkey Sandwich", "Sliced turkey on fresh bread", 5.00, 20, 5));
                repo.save(new Product("P012", "Deli", "Assorted deli meats sandwich", 5.50, 20, 5));
                repo.save(new Product("P013", "Regular Fries", "Crispy golden fries", 2.00, 50, 15));
                repo.save(new Product("P014", "Tastee Beef Patty", "Pre-made spicy beef patty", 2.00, 60, 10));
                repo.save(new Product("P015", "Tastee Chicken Patty", "Pre-made savory chicken patty", 2.00, 40, 10));
                repo.save(new Product("P016", "Tasty Cheese Patty", "Pre-made cheesy patty", 2.50, 30, 10));
                repo.save(new Product("P017", "Coco Bread", "Buttery soft bread", 1.50, 50, 10));
                repo.save(new Product("P018", "Bigga Soda", "Refreshing Jamaican soda", 1.50, 100, 20));
                
                System.out.println("? Full menu items loaded successfully!");
            }

            // Load Pickup Slots
            if (slotRepo.count() == 0) {
                slotRepo.save(new PickupSlot("SLOT-1200", LocalTime.of(12, 0), 5));
                slotRepo.save(new PickupSlot("SLOT-1215", LocalTime.of(12, 15), 5));
                slotRepo.save(new PickupSlot("SLOT-1230", LocalTime.of(12, 30), 5));
                slotRepo.save(new PickupSlot("SLOT-1245", LocalTime.of(12, 45), 5));
                System.out.println("? Pickup slots loaded successfully!");
            }
        };
    }
}
