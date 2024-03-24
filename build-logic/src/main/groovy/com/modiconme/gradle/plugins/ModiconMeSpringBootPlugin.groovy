package com.modiconme.gradle.plugins


import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.jvm.tasks.Jar
import org.springframework.boot.gradle.plugin.SpringBootPlugin
import org.springframework.boot.gradle.tasks.bundling.BootJar

class ModiconMeSpringBootPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.plugins.apply(SpringBootPlugin)

        project.tasks.named(JavaPlugin.JAR_TASK_NAME, Jar) {
            enabled = false
        }
    }
}
