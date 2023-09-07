package com.example.pium.repositiory;

import com.example.pium.entity.RefreshTokenEntity;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshTokenEntity, Integer> {

    RefreshTokenEntity findByRefreshToken(String refreshToken);
}
