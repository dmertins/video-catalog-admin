package com.dmertins.videocatalogadmin.domain.category;

import com.dmertins.videocatalogadmin.domain.validation.Error;
import com.dmertins.videocatalogadmin.domain.validation.ValidationHandler;
import com.dmertins.videocatalogadmin.domain.validation.Validator;

public class CategoryValidator extends Validator {

    public static final int NAME_MIN_LENGTH = 3;
    public static final int NAME_MAX_LENGTH = 255;

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

        final var length = name.trim().length();
        if (length < NAME_MIN_LENGTH || length > NAME_MAX_LENGTH) {
            final var errorMessageTemplate = "'name' must be between %d and %d characters long";
            final var errorMessage = errorMessageTemplate.formatted(NAME_MIN_LENGTH, NAME_MAX_LENGTH);
            this.validationHandler().append(new Error(errorMessage));
        }
    }
}
