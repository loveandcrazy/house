package org.linlinjava.litemall.db.dao;


import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.Contract;


import java.time.LocalDateTime;

public interface LocalContractMapper {
    int updateWithOptimisticLocker(@Param("lastUpdateTime") LocalDateTime lastUpdateTime, @Param("order") Contract contract);
}
