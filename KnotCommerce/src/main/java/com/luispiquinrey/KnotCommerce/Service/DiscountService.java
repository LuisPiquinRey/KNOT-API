package com.luispiquinrey.KnotCommerce.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luispiquinrey.KnotCommerce.Entities.Product.PerishableProduct;
import com.luispiquinrey.KnotCommerce.Repository.RepositoryProduct;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class DiscountService {

    @Autowired
    private final RepositoryProduct repositoryProduct;

    public DiscountService(RepositoryProduct repositoryProduct) {
        this.repositoryProduct = repositoryProduct;
    }

    @Scheduled(cron = "0 0 12 * * *")
    @Async
    @Transactional
    public void applyDiscountsToPerishables() {
        List<PerishableProduct> perishables = repositoryProduct.findAllPerishable();
        for (PerishableProduct product : perishables) {
            int days = (int) ChronoUnit.DAYS.between(LocalDate.now(), product.getExpirationDate());
            switch (days) {
                case -1 -> product.setPrice(product.getPrice() * 0.5);
                case 0 -> product.setPrice(product.getPrice() * 0.7);
                case 1 -> product.setPrice(product.getPrice() * 0.9);
                case -2 -> repositoryProduct.delete(product);
                default -> {
                }
            }
            repositoryProduct.save(product);
        }
    }
}
