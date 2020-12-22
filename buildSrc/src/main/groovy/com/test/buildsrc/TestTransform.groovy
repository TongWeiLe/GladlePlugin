package com.test.buildsrc

import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import com.test.asm.AllenLogVisitor
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter


public class TestTransform  extends Transform{


    @Override
    String getName() {
        return "Allen"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {

        def inputs = transformInvocation.inputs
        def outputProvider = transformInvocation.outputProvider

        inputs.each {

            it.jarInputs.each {
                File dest = outputProvider.getContentLocation(it.name, it.contentTypes, it.scopes, Format.JAR)

//                println "Jar: ${it.file}, --------  Dest: ${dest}"
                FileUtils.copyFile(it.file, dest)
            }

            it.directoryInputs.each {
                if (it.file.isDirectory()) {
                    it.file.eachFileRecurse { File file ->
                        def name = file.name
                        println "file name *** ${name}"

                        if (name.endsWith(".class") && !(name == ("R.class"))
                                && !name.startsWith("R\$") && !(name == ("BuildConfig.class"))) {
                            ClassReader reader = new ClassReader(file.bytes)
                            ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS)
                            ClassVisitor visitor = new AllenLogVisitor(writer)

                            reader.accept(visitor, ClassReader.EXPAND_FRAMES)

                            byte[] code = writer.toByteArray()
                            def classPath = file.parentFile.absolutePath + File.separator + name
                            println "classPath is *** ${classPath}"
                            FileOutputStream fos = new FileOutputStream(classPath)
                            fos.write(code)
                            fos.close()
                        }
                    }
                }


                File dest = outputProvider.getContentLocation(it.name, it.contentTypes, it.scopes, Format.DIRECTORY)
//                println "Jar: ${it.file}, ~~~~~~~~  Dest: ${dest}"
                FileUtils.copyDirectory(it.file, dest)
            }

        }







    }
}
