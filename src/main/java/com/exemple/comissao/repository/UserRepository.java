package com.exemple.comissao.repository;

import com.exemple.comissao.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.comissao.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
