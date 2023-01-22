package com.dmertins.videocatalogadmin.domain.category;

import com.dmertins.videocatalogadmin.domain.exceptions.DomainException;
import com.dmertins.videocatalogadmin.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

    @Test
    public void shouldCreateCategoryForValidParams() {
        final var expectedName = "Movies";
        final var expectedDescription = "The movies category";
        final var expectedIsActive = true;
        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));
        assertNotNull(actualCategory);
        assertNotNull(actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertNotNull(actualCategory.getCreatedAt());
        assertNotNull(actualCategory.getUpdatedAt());
        assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void shouldCreateCategoryForEmptyDescription() {
        final var expectedName = "Movies";
        final var expectedDescription = "   ";
        final var expectedIsActive = true;
        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));
        assertNotNull(actualCategory);
        assertNotNull(actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertNotNull(actualCategory.getCreatedAt());
        assertNotNull(actualCategory.getUpdatedAt());
        assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void shouldCreateCategoryForInactiveState() {
        final var expectedName = "Movies";
        final var expectedDescription = "The movies category";
        final var expectedIsActive = false;
        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));
        assertNotNull(actualCategory);
        assertNotNull(actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertNotNull(actualCategory.getCreatedAt());
        assertNotNull(actualCategory.getUpdatedAt());
        assertNotNull(actualCategory.getDeletedAt());
    }

    @Test
    public void shouldRaiseNullNameError() {
        final String expectedName = null;
        final var expectedDescription = "The movies category";
        final var expectedIsActive = true;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' cannot be null";

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualException =
                assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void shouldRaiseEmptyNameError() {
        final var expectedName = "   ";
        final var expectedDescription = "The movies category";
        final var expectedIsActive = true;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' cannot be empty";

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualException =
                assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void shouldRaiseInvalidNameLengthErrorForLessThan3() {
        final var expectedName = "Mo ";
        final var expectedDescription = "The movies category";
        final var expectedIsActive = true;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters long";

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualException =
                assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void shouldRaiseInvalidNameLengthErrorForMoreThan255() {
        final var expectedName = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor i.";
        final var expectedDescription = "The movies category";
        final var expectedIsActive = true;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters long";

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualException =
                assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void shouldDeactivateAnActiveCategory() {
        final var expectedName = "Movies";
        final var expectedDescription = "The movies category";
        final var activeCategory = Category.newCategory(expectedName, expectedDescription, true);

        assertDoesNotThrow(() -> activeCategory.validate(new ThrowsValidationHandler()));
        assertTrue(activeCategory.isActive());
        assertNull(activeCategory.getDeletedAt());

        final var expectedId = activeCategory.getId();
        final var expectedCreatedAt = activeCategory.getCreatedAt();
        final var lastUpdatedAt = activeCategory.getUpdatedAt();

        final var deactivatedCategory = activeCategory.deactivate();

        assertDoesNotThrow(() -> deactivatedCategory.validate(new ThrowsValidationHandler()));
        assertEquals(expectedId, deactivatedCategory.getId());
        assertEquals(expectedName, deactivatedCategory.getName());
        assertEquals(expectedDescription, deactivatedCategory.getDescription());
        assertFalse(deactivatedCategory.isActive());
        assertEquals(expectedCreatedAt, deactivatedCategory.getCreatedAt());
        assertTrue(deactivatedCategory.getUpdatedAt().isAfter(lastUpdatedAt));
        assertNotNull(deactivatedCategory.getDeletedAt());
        assertTrue(deactivatedCategory.getDeletedAt().isAfter(lastUpdatedAt));
    }

    @Test
    public void shouldNotChangeDeletedAtInstantForAnInactiveCategory() {
        final var expectedName = "Movies";
        final var expectedDescription = "The movies category";
        final var inactiveCategory = Category.newCategory(expectedName, expectedDescription, false);

        assertDoesNotThrow(() -> inactiveCategory.validate(new ThrowsValidationHandler()));
        assertFalse(inactiveCategory.isActive());
        assertNotNull(inactiveCategory.getDeletedAt());

        final var expectedId = inactiveCategory.getId();
        final var expectedCreatedAt = inactiveCategory.getCreatedAt();
        final var lastUpdatedAt = inactiveCategory.getUpdatedAt();
        final var lastDeletedAt = inactiveCategory.getDeletedAt();

        final var deactivatedCategory = inactiveCategory.deactivate();

        assertDoesNotThrow(() -> deactivatedCategory.validate(new ThrowsValidationHandler()));
        assertEquals(expectedId, deactivatedCategory.getId());
        assertEquals(expectedName, deactivatedCategory.getName());
        assertEquals(expectedDescription, deactivatedCategory.getDescription());
        assertFalse(deactivatedCategory.isActive());
        assertEquals(expectedCreatedAt, deactivatedCategory.getCreatedAt());
        assertTrue(deactivatedCategory.getUpdatedAt().isAfter(lastUpdatedAt));
        assertNotNull(deactivatedCategory.getDeletedAt());
        assertEquals(lastDeletedAt, deactivatedCategory.getDeletedAt());
    }

    @Test
    public void shouldActivateCategory() {
        final var expectedName = "Movies";
        final var expectedDescription = "The movies category";
        final var inactiveCategory = Category.newCategory(expectedName, expectedDescription, false);

        assertDoesNotThrow(() -> inactiveCategory.validate(new ThrowsValidationHandler()));
        assertFalse(inactiveCategory.isActive());
        assertNotNull(inactiveCategory.getDeletedAt());

        final var expectedId = inactiveCategory.getId();
        final var expectedCreatedAt = inactiveCategory.getCreatedAt();
        final var lastUpdatedAt = inactiveCategory.getUpdatedAt();

        final var activatedCategory = inactiveCategory.activate();

        assertDoesNotThrow(() -> activatedCategory.validate(new ThrowsValidationHandler()));
        assertEquals(expectedId, activatedCategory.getId());
        assertEquals(expectedName, activatedCategory.getName());
        assertEquals(expectedDescription, activatedCategory.getDescription());
        assertTrue(activatedCategory.isActive());
        assertEquals(expectedCreatedAt, activatedCategory.getCreatedAt());
        assertTrue(activatedCategory.getUpdatedAt().isAfter(lastUpdatedAt));
        assertNull(activatedCategory.getDeletedAt());
    }

    @Test
    public void shouldUpdateCategory() {
        final var activeCategory = Category.newCategory("Movies", "The movies category", true);

        assertDoesNotThrow(() -> activeCategory.validate(new ThrowsValidationHandler()));

        final var expectedId = activeCategory.getId();
        final var expectedName = "Series";
        final var expectedDescription = "The series category";
        final var expectedCreatedAt = activeCategory.getCreatedAt();
        final var lastUpdatedAt = activeCategory.getUpdatedAt();

        final var updatedCategory = activeCategory.update(expectedName, expectedDescription, true);

        assertDoesNotThrow(() -> updatedCategory.validate(new ThrowsValidationHandler()));
        assertEquals(expectedId, updatedCategory.getId());
        assertEquals(expectedName, updatedCategory.getName());
        assertEquals(expectedDescription, updatedCategory.getDescription());
        assertTrue(updatedCategory.isActive());
        assertEquals(expectedCreatedAt, updatedCategory.getCreatedAt());
        assertTrue(updatedCategory.getUpdatedAt().isAfter(lastUpdatedAt));
        assertNull(updatedCategory.getDeletedAt());
    }

    @Test
    public void shouldUpdateAndDeactivateAnActiveCategory() {
        final var activeCategory = Category.newCategory("Movies", "The movies category", true);

        assertDoesNotThrow(() -> activeCategory.validate(new ThrowsValidationHandler()));
        assertTrue(activeCategory.isActive());
        assertNull(activeCategory.getDeletedAt());

        final var expectedId = activeCategory.getId();
        final var expectedName = "Series";
        final var expectedDescription = "The series category";
        final var expectedCreatedAt = activeCategory.getCreatedAt();
        final var lastUpdatedAt = activeCategory.getUpdatedAt();

        final var updatedCategory = activeCategory.update(expectedName, expectedDescription, false);

        assertDoesNotThrow(() -> updatedCategory.validate(new ThrowsValidationHandler()));
        assertEquals(expectedId, updatedCategory.getId());
        assertEquals(expectedName, updatedCategory.getName());
        assertEquals(expectedDescription, updatedCategory.getDescription());
        assertFalse(updatedCategory.isActive());
        assertEquals(expectedCreatedAt, updatedCategory.getCreatedAt());
        assertTrue(updatedCategory.getUpdatedAt().isAfter(lastUpdatedAt));
        assertNotNull(updatedCategory.getDeletedAt());
        assertTrue(updatedCategory.getDeletedAt().isAfter(expectedCreatedAt));
    }

    @Test
    public void shouldUpdateAndActivateAnInactiveCategory() {
        final var inactiveCategory = Category.newCategory("Movies", "The movies category", false);

        assertDoesNotThrow(() -> inactiveCategory.validate(new ThrowsValidationHandler()));
        assertFalse(inactiveCategory.isActive());
        assertNotNull(inactiveCategory.getDeletedAt());

        final var expectedId = inactiveCategory.getId();
        final var expectedName = "Series";
        final var expectedDescription = "The series category";
        final var expectedCreatedAt = inactiveCategory.getCreatedAt();
        final var lastUpdatedAt = inactiveCategory.getUpdatedAt();

        final var updatedCategory = inactiveCategory.update(expectedName, expectedDescription, true);

        assertDoesNotThrow(() -> updatedCategory.validate(new ThrowsValidationHandler()));
        assertEquals(expectedId, updatedCategory.getId());
        assertEquals(expectedName, updatedCategory.getName());
        assertEquals(expectedDescription, updatedCategory.getDescription());
        assertTrue(updatedCategory.isActive());
        assertEquals(expectedCreatedAt, updatedCategory.getCreatedAt());
        assertTrue(updatedCategory.getUpdatedAt().isAfter(lastUpdatedAt));
        assertNull(updatedCategory.getDeletedAt());
    }

    @Test
    public void shouldUpdateCategoryForInvalidParams() {
        final var validCategory = Category.newCategory("Movies", "The movies category", true);

        assertDoesNotThrow(() -> validCategory.validate(new ThrowsValidationHandler()));

        final var expectedId = validCategory.getId();
        final String invalidName = null;
        final var expectedDescription = "The series category";
        final var expectedCreatedAt = validCategory.getCreatedAt();
        final var lastUpdatedAt = validCategory.getUpdatedAt();

        final var invalidCategory = validCategory.update(invalidName, expectedDescription, true);

        assertThrows(DomainException.class, () -> invalidCategory.validate(new ThrowsValidationHandler()));
        assertEquals(expectedId, invalidCategory.getId());
        assertEquals(invalidName, invalidCategory.getName());
        assertEquals(expectedDescription, invalidCategory.getDescription());
        assertTrue(invalidCategory.isActive());
        assertEquals(expectedCreatedAt, invalidCategory.getCreatedAt());
        assertTrue(invalidCategory.getUpdatedAt().isAfter(lastUpdatedAt));
        assertNull(invalidCategory.getDeletedAt());
    }
}
