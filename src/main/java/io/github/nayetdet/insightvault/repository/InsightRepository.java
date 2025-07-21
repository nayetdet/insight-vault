package io.github.nayetdet.insightvault.repository;

import io.github.nayetdet.insightvault.model.Insight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface InsightRepository extends JpaRepository<Insight, Long>, JpaSpecificationExecutor<Insight> {
}
