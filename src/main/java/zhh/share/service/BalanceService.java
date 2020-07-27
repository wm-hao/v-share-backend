package zhh.share.service;

import org.springframework.data.domain.Page;
import zhh.share.entity.Balance;

import java.util.List;

/**
 * @author richer
 * @date 2020/7/26 8:17 下午
 */
public interface BalanceService {
    List<Balance> findByUserId(long userId);

    Balance save(Balance balance);

    Page<Balance> findByUserIdPagination(long userId, int page, int size, boolean asc);

    List<Balance> saveAll(List<Balance> balances);
}
