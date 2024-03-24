package com.modiconme.gradle.plugins

import org.gradle.api.provider.Property

interface ModiconMeJavaExtension {

    Property<Integer> getJavaVersion()

}