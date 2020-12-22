package com.test.buildsrc

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project;

class TestPlugin implements Plugin<Project>{
    @Override
    void apply(Project project) {
        def extension = project.extensions.create("hello", TestExtension)

        project.afterEvaluate {

            println("hi ${extension.name}")
        }

        def transform = new TestTransform()
        def baseExtension = project.extensions.getByType(BaseExtension)
        baseExtension.registerTransform(transform)
    }
}