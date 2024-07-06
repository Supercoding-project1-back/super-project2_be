package com.example.superproject1.repository.entity;

import com.example.superproject1.repository.users.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "payment", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<PaymentItem> paymentItems = new ArrayList<>();
}
