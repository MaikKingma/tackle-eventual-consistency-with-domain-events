package uk.devoxx.tacke_eventual_consistency;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

/**
 * @author Maik Kingma
 */

@AnalyzeClasses(packages = "uk.devoxx.tacke_eventual_consistency", importOptions = {ImportOption.DoNotIncludeTests.class})
public class CleanHexagonalOnionArchitectureTest {

    @ArchTest
    static final ArchRule layer_dependencies_are_respected =
            layeredArchitecture().consideringAllDependencies()

            .layer("command").definedBy("uk.devoxx.tacke_eventual_consistency.command..")
            .layer("query").definedBy("uk.devoxx.tacke_eventual_consistency.query..")
            .layer("data").definedBy("uk.devoxx.tacke_eventual_consistency.data..")
            .layer("acl").definedBy("uk.devoxx.tacke_eventual_consistency.acl..")
            .layer("domain interaction").definedBy("uk.devoxx.tacke_eventual_consistency.domaininteraction..")
            .layer("domain").definedBy("uk.devoxx.tacke_eventual_consistency.domain..")

            .whereLayer("command").mayNotBeAccessedByAnyLayer()
            .whereLayer("query").mayNotBeAccessedByAnyLayer()
            .whereLayer("data").mayNotBeAccessedByAnyLayer()
            .whereLayer("acl").mayNotBeAccessedByAnyLayer()
            .whereLayer("domain interaction").mayOnlyBeAccessedByLayers("command", "query", "data", "acl")
            .whereLayer("domain").mayOnlyBeAccessedByLayers("domain interaction");
}
