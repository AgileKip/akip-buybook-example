package org.agilekip.tutorials.buybook;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("org.agilekip.tutorials.buybook");

        noClasses()
            .that()
            .resideInAnyPackage("org.agilekip.tutorials.buybook.service..")
            .or()
            .resideInAnyPackage("org.agilekip.tutorials.buybook.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..org.agilekip.tutorials.buybook.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
