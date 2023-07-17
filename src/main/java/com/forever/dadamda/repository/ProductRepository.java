package com.forever.dadamda.repository;

import com.forever.dadamda.entity.item.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
