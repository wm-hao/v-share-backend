package zhh.share.service;

import org.springframework.data.domain.Page;
import zhh.share.entity.Journal;

/**
 * @author richer
 * @date 2020/8/1 12:55 下午
 */
public interface JournalService {

    Page<Journal> findByUserId(long userId, int page, int size);

    Journal addNewJournal(Journal journal) throws Exception;

    Journal updateJournal(Journal journal) throws Exception;
}
