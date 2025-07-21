package io.github.nayetdet.insightvault.repository;

import io.github.nayetdet.insightvault.model.Dataset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DatasetRepository extends JpaRepository<Dataset, Long>, JpaSpecificationExecutor<Dataset> {
}
