package com.modiconme.gradle.plugins

import io.spring.gradle.dependencymanagement.DependencyManagementPlugin
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project

class ModiconMeSpringBootDependencyManagementPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        if (!project.plugins.hasPlugin(ModiconMeSpringBootPlugin)) {
            throw new GradleException("Не найден spring boot плагин")
        }
        project.plugins.apply(DependencyManagementPlugin)
    }
}
