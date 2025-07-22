package io.github.nayetdet.insightvault.repository.query.jpa.specification;

import io.github.nayetdet.insightvault.model.Dataset;
import org.springframework.data.jpa.domain.Specification;

public class DatasetSpecificationFactory {

    private DatasetSpecificationFactory() {
    }

    public static Specification<Dataset> recordIdEquals(String recordId) {
        return (root, query, cb) ->
                cb.equal(root.get("recordId"), recordId);
    }

    public static Specification<Dataset> nameContains(String name) {
        return (root, query, cb) ->
                cb.like(cb.upper(root.get("name")), "%" + name.toUpperCase() + "%");
    }

    public static Specification<Dataset> contentTypeEquals(String contentType) {
        return (root, query, cb) ->
                cb.equal(root.get("contentType"), contentType);
    }

    public static Specification<Dataset> sizeInBytesMin(Long sizeInBytesMin) {
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("sizeInBytes"), sizeInBytesMin);
    }

    public static Specification<Dataset> sizeInBytesMax(Long sizeInBytesMax) {
        return (root, query, cb) ->
                cb.lessThanOrEqualTo(root.get("sizeInBytes"), sizeInBytesMax);
    }

    public static Specification<Dataset> usernameEquals(String username) {
        return (root, query, cb) ->
                cb.equal(root.get("user").get("username"), username);
    }

}
