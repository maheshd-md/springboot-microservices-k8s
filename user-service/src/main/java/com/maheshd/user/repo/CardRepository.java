package com.maheshd.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maheshd.user.entity.CardEntity;

@Repository
public interface CardRepository extends JpaRepository<CardEntity,Long> {

}
