package com.modiconme.gradle.plugins

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.gradle.testing.jacoco.tasks.JacocoReport

class ModiconMeTestUnitPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        baseTestConfig(project)
        jacocoConfig(project)
    }

    private static baseTestConfig(Project project) {
        project.tasks.withType(Test).configureEach {
            useJUnitPlatform()
            testLogging.showStackTraces = true
            testLogging.exceptionFormat = TestExceptionFormat.FULL
            testLogging.events TestLogEvent.FAILED, TestLogEvent.SKIPPED, TestLogEvent.STANDARD_ERROR
            systemProperties.put('file.encoding', 'UTF-8')

            systemProperty('junit.jupiter.execution.parallel.enabled', false)
            systemProperty('junit.jupiter.execution.parallel.mode.default', 'concurrent')
            systemProperty('junit.jupiter.execution.parallel.mode.classes.default', 'concurrent')
            systemProperty('junit.jupiter.execution.parallel.config.strategy', 'fixed')
            systemProperty('junit.jupiter.execution.parallel.config.fixed.parallelism', 4)
        }
    }

    private static jacocoConfig(Project project) {
        project.plugins.apply(JacocoPlugin)

        def jacocoTestReport = project.tasks.withType(JacocoReport)
        jacocoTestReport.configureEach {
            reports.xml.required = true
            executionData.from += project.file('build/jacoco/test.exec')
        }

        project.tasks.named('check', DefaultTask) {
            finalizedBy jacocoTestReport
        }
    }
}
