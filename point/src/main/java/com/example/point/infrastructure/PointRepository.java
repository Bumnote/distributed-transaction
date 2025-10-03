package com.example.point.infrastructure;

import com.example.point.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Integer> {

  Point findByUserId(Long userId);
}
