package com.dmertins.videocatalogadmin.domain.category;

import com.dmertins.videocatalogadmin.domain.validation.Error;
import com.dmertins.videocatalogadmin.domain.validation.ValidationHandler;
import com.dmertins.videocatalogadmin.domain.validation.Validator;

public class CategoryValidator extends Validator {
    private final Category category;

    public CategoryValidator(final Category category, final ValidationHandler handler) {
        super(handler);
        this.category = category;
    }

    @Override
    public void validate() {
        checkNameConstraints();
    }

    private void checkNameConstraints() {
        final var name = this.category.getName();

        if (name == null) {
            this.validationHandler().append(new Error("'name' cannot be null"));
            return;
        }

        if (name.isBlank()) {
            this.validationHandler().append(new Error("'name' cannot be empty"));
            return;
        }
    }
}
