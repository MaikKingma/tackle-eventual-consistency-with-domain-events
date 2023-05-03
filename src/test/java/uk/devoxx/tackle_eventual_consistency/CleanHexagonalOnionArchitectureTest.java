package uk.devoxx.tackle_eventual_consistency;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import shaded_package.com.lmax.disruptor.EventProcessor;
import uk.devoxx.tacke_eventual_consistency.data.book.BookJPA;
import uk.devoxx.tacke_eventual_consistency.domain.DomainEvent;
import uk.devoxx.tacke_eventual_consistency.domain.book.Book;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

/**
 * @author Maik Kingma
 */

@AnalyzeClasses(packages = "uk.devoxx.tackle_eventual_consistency", importOptions = {ImportOption.DoNotIncludeTests.class})
public class CleanHexagonalOnionArchitectureTest {

    @ArchTest
    static final ArchRule layer_dependencies_are_respected =
            layeredArchitecture().consideringAllDependencies()

            .layer("command").definedBy("uk.devoxx.tackle_eventual_consistency.command..")
            .layer("query").definedBy("uk.devoxx.tackle_eventual_consistency.query..")
            .layer("data").definedBy("uk.devoxx.tackle_eventual_consistency.data..")
            .layer("acl").definedBy("uk.devoxx.tackle_eventual_consistency.acl..")
            .layer("domain interaction").definedBy("uk.devoxx.tackle_eventual_consistency.domaininteraction..")
            .layer("domain").definedBy("uk.devoxx.tackle_eventual_consistency.domain..")

            .whereLayer("command").mayNotBeAccessedByAnyLayer()
            .whereLayer("query").mayNotBeAccessedByAnyLayer()
            .whereLayer("data").mayNotBeAccessedByAnyLayer()
            .whereLayer("acl").mayNotBeAccessedByAnyLayer()
            .whereLayer("domain interaction").mayOnlyBeAccessedByLayers("command", "query", "data", "acl")
            .whereLayer("domain").mayOnlyBeAccessedByLayers("domain interaction")
            // we will ignore the Domain Event dependencies from the process layer to the domain layer
            // We are eventually trying to solve complexity, not add to it. Adding another layer to solve
            // this would be overkill and overcomplicate things
            .ignoreDependency(BookJPA.class, DomainEvent.class);
}
