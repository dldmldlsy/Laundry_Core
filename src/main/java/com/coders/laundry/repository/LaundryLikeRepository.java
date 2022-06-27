package com.coders.laundry.repository;

import com.coders.laundry.domain.entity.LaundryLikeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LaundryLikeRepository {
    int insert(LaundryLikeEntity laundryLikeEntity);
}
