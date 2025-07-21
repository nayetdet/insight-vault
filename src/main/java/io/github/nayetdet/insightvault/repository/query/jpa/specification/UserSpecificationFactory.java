package io.github.nayetdet.insightvault.repository.query.jpa.specification;

import io.github.nayetdet.insightvault.model.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecificationFactory {

    private UserSpecificationFactory() {
    }

    public static Specification<User> usernameContains(String username) {
        return (root, query, cb) ->
                cb.like(cb.upper(root.get("username")), "%" + username.toUpperCase() + "%");
    }

    public static Specification<User> nameContains(String name) {
        return (root, query, cb) ->
                cb.like(cb.upper(root.get("name")), "%" + name.toUpperCase() + "%");
    }

}
