package com.test.asm;

import com.android.ddmlib.Log;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;


public class AllenLogVisitor extends ClassVisitor {

    public static final String TAG = "AllenLogVisitor";

    private String mClassName;

    public AllenLogVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM7, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        mClassName = name;

        System.out.println("visit: "+ version + " "+ access+ "  "+ name +" "+ signature + "  "+ superName + "  "+ interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
        methodVisitor = new AllenLogMethodVisitor(Opcodes.ASM7, methodVisitor, access, mClassName, name, desc);
        return methodVisitor;
    }
}
