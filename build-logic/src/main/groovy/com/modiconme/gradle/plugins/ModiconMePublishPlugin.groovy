package com.modiconme.gradle.plugins


import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.jvm.tasks.Jar
import org.jfrog.gradle.plugin.artifactory.ArtifactoryPlugin
import org.jfrog.gradle.plugin.artifactory.dsl.ArtifactoryPluginConvention
import org.springframework.boot.gradle.plugin.SpringBootPlugin

import static org.gradle.api.plugins.JavaPlugin.JAR_TASK_NAME
import static org.springframework.boot.gradle.plugin.SpringBootPlugin.BOOT_JAR_TASK_NAME

class ModiconMePublishPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.plugins.apply(MavenPublishPlugin)
        project.plugins.apply(ArtifactoryPlugin)

        def mavenJavaPublication = project.extensions.getByType(PublishingExtension)
                .publications.create('maven', MavenPublication) {
            if (project.plugins.hasPlugin(SpringBootPlugin)) {
                artifacts += project.tasks.named(BOOT_JAR_TASK_NAME, Jar)
                artifactId = project.tasks.named(BOOT_JAR_TASK_NAME, Jar).get().archiveBaseName.get()
            } else {
                artifacts += project.tasks.named(JAR_TASK_NAME, Jar)
                artifactId = project.tasks.named(JAR_TASK_NAME, Jar).get().archiveBaseName.get()
            }
        }

        project.extensions.getByType(ArtifactoryPluginConvention).publish {
            contextUrl = '0'
            repository {
                repoKey = '1'
                username = '2'
                password = '3'
            }
            defaults {
                mavenJavaPublication
            }
            publishBuildInfo = false
        }
    }
}
