package com.forever.dadamda.repository;

import com.forever.dadamda.entity.scrap.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
