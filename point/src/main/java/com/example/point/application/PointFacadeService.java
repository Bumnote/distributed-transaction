package com.example.point.application;

import com.example.point.application.dto.PointReserveCommand;
import com.example.point.application.dto.PointReserveConfirmCommand;
import com.example.point.controller.dto.PointReserveCancelCommand;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Service
public class PointFacadeService {

  private final PointService pointService;

  public PointFacadeService(PointService pointService) {
    this.pointService = pointService;
  }

  public void tryReserve(PointReserveCommand command) {

    int tryCount = 0;

    while (tryCount < 3) {
      try {
        pointService.tryReserve(command);
        return;
      } catch (ObjectOptimisticLockingFailureException e) {
        tryCount++;
      }
    }

    throw new RuntimeException("예약에 실패했습니다.");
  }

  public void confirmReserve(PointReserveConfirmCommand command) {
    int tryCount = 0;

    while (tryCount < 3) {
      try {
        pointService.confirmReserve(command);
        return;
      } catch (ObjectOptimisticLockingFailureException e) {
        tryCount++;
      }
    }

    throw new RuntimeException("예약에 실패했습니다.");

  }

  public void cancelReserve(PointReserveCancelCommand command) {
    int tryCount = 0;

    while (tryCount < 3) {
      try {
        pointService.cancelReserve(command);
        return;
      } catch (ObjectOptimisticLockingFailureException e) {
        tryCount++;
      }
    }

    throw new RuntimeException("예약에 실패했습니다.");
  }


}