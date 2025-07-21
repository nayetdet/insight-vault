package io.github.nayetdet.insightvault.repository;

import io.github.nayetdet.insightvault.model.DatasetRecord;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatasetRecordRepository extends ElasticsearchRepository<DatasetRecord, String> {
}
