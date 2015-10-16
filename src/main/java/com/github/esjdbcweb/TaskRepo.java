package com.github.esjdbcweb;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by abo on 10/15/15.
 */
public interface TaskRepo extends JpaRepository<Task, Long> {

    Page<Task> findByState(Integer state, Pageable pageable);
}
