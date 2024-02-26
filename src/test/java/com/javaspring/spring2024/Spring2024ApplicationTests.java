package com.javaspring.spring2024;

import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@SpringBootTest
class Spring2024ApplicationTests {
    private final JavaClasses classes = new ClassFileImporter().importPackages("com.javaspring.spring2024");

    @Test
    @DisplayName("Соблюдены требования архитектуры")
    void ArchitectureRule() {
        layeredArchitecture().consideringAllDependencies()

                .layer("Domain").definedBy("com.javaspring.spring2024.domain..")
                .layer("Application").definedBy("com.javaspring.spring2024.app..")
                .layer("Controllers").definedBy("com.javaspring.spring2024.controller..")

                .whereLayer("Application").mayOnlyBeAccessedByLayers("Application", "Controllers")
                .whereLayer("Controllers").mayOnlyBeAccessedByLayers("Controllers")

                .check(classes);
    }

}
