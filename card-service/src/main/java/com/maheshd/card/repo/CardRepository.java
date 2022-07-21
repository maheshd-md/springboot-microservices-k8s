package com.maheshd.card.repo;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maheshd.card.entity.CardEntity;

@Repository
public interface CardRepository extends JpaRepository<CardEntity,Long> {

	CardEntity findByAccountId(Long accountId);


}
