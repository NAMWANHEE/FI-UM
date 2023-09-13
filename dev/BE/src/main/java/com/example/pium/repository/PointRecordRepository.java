package com.example.pium.repository;

import com.example.pium.dto.projection.ChildPointInterface;
import com.example.pium.entity.PointRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointRecordRepository extends JpaRepository<PointRecordEntity, Integer> {

    // 유저 번호를 이용하여 포인트 내역 최신순으로 보여줍니다.
    @Query(value = "select a.point_type as useType, b.point_change as pointChange, b.changed_time as changedTime from point_type as a, (select point_type_no , point_change, user_no, changed_time from point_record order by changed_time desc) as b where a.point_type_no = b.point_type_no and b.user_no = :userNo",nativeQuery = true)
    List<ChildPointInterface> findByUserNo(Integer userNo);
}
