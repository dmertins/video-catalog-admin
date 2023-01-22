package com.dmertins.videocatalogadmin.domain.category;

import com.dmertins.videocatalogadmin.domain.pagination.Pagination;

import java.util.Optional;

public interface CategoryGateway {

    Category create(Category category);

    Optional<Category> findById(CategoryID id);

    Pagination<Category> findAll(CategorySearchQuery query);

    Category update(Category category);

    void deleteById(CategoryID id);
}
