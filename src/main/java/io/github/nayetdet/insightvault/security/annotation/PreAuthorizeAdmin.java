package io.github.nayetdet.insightvault.security.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasRole('ROLE_' + T(io.github.nayetdet.insightvault.model.enums.UserRole).ADMIN.name())")
public @interface PreAuthorizeAdmin {
}
