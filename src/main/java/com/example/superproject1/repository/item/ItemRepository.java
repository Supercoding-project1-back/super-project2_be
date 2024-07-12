package com.example.superproject1.repository.item;

import com.example.superproject1.repository.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findAllByCountGreaterThan(int count, Pageable pageable);
    Page<Item> findAllByUser(User user, Pageable pageable);
    Optional<Item> findByName(String name);
    Boolean existsByName(String name);
}