package com.test.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

public class AllenLogMethodVisitor extends AdviceAdapter {

    public static final String ANNOTATION_DESC = "Lcom/test/allen/AllenLog;";

    private boolean mNeedInject = false;

    private int startTimeId;

    private int methodId;

    private String className;

    private String methodName;

    private String desc;

    private boolean isStaticMethod;

    private Type[] argumentArrays;

    /**
     * Constructs a new {@link AdviceAdapter}.
     *
     * @param api           the ASM API version implemented by this visitor. Must be one of {@link
     *                      Opcodes#ASM4}, {@link Opcodes#ASM5}, {@link Opcodes#ASM6} or {@link Opcodes#ASM7}.
     * @param methodVisitor the method visitor to which this adapter delegates calls.
     * @param access        the method's access flags (see {@link Opcodes}).
     * @param name          the method's name.
     * @param descriptor    the method's descriptor (see {@link Type Type}).
     */
    protected AllenLogMethodVisitor(int api, MethodVisitor methodVisitor, int access, String name, String methodName, String descriptor) {
        super(api, methodVisitor, access, name, descriptor);
        this.className = name;
        this.methodName = methodName;
        this.desc = descriptor;
        argumentArrays = Type.getArgumentTypes(desc);
        isStaticMethod = ((access & Opcodes.ACC_STATIC) != 0);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {

        mNeedInject = ANNOTATION_DESC.equals(descriptor);
        System.out.println("visitAnnotation:   "+ descriptor + " isvisible "+ visible);
        return super.visitAnnotation(descriptor, visible);
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter();

        if (mNeedInject) {


            System.out.println("开始插装了。。。");
            methodId = newLocal(Type.INT_TYPE);
            mv.visitMethodInsn(INVOKESTATIC, "com/test/allen/presenter/MethodCache", "request", "()I", false);
            mv.visitIntInsn(ISTORE, methodId);

            for (int i = 0; i < argumentArrays.length; i++) {
                Type type = argumentArrays[i];
                int index = isStaticMethod ? i : (i + 1);
                switch (type.getSort()) {
                    case Type.BOOLEAN:
                    case Type.CHAR:
                    case Type.BYTE:
                    case Type.SHORT:
                    case Type.INT:
                        mv.visitVarInsn(ILOAD, index);
                        box(type);
                        break;
                    case Type.FLOAT:
                        mv.visitVarInsn(FLOAD, index);
                        box(type);
                        break;
                    case Type.LONG:
                        mv.visitVarInsn(LLOAD, index);
                        box(type);
                        break;
                    case Type.DOUBLE:
                        mv.visitVarInsn(DLOAD, index);
                        box(type);
                        break;
                    case Type.ARRAY:
                    case Type.OBJECT:
                        mv.visitVarInsn(ALOAD, index);
                        box(type);
                        break;
                }
                mv.visitVarInsn(ILOAD, methodId);
                visitMethodInsn(INVOKESTATIC, "com/test/allen/presenter/MethodCache", "addMethodArgument",
                        "(Ljava/lang/Object;I)V", false);
            }

            startTimeId = newLocal(Type.LONG_TYPE);
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
            mv.visitIntInsn(LSTORE, startTimeId);


        }
    }

    @Override
    protected void onMethodExit(int opcode) {
        if (mNeedInject) {
            if (opcode == RETURN) {
                visitInsn(ACONST_NULL);
            } else if (opcode == ARETURN || opcode == ATHROW) {
                dup();
            } else {
                if (opcode == LRETURN || opcode == DRETURN) {
                    dup2();
                } else {
                    dup();
                }
                box(Type.getReturnType(this.methodDesc));
            }
            mv.visitLdcInsn(className);
            mv.visitLdcInsn(methodName);
            mv.visitLdcInsn(desc);
            mv.visitVarInsn(LLOAD, startTimeId);
            mv.visitVarInsn(ILOAD, methodId);
            mv.visitMethodInsn(INVOKESTATIC, "com/test/allen/presenter/MethodCache", "updateMethodInfo",
                    "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JI)V", false);

            mv.visitVarInsn(ILOAD, methodId);
            mv.visitMethodInsn(INVOKESTATIC, "com/test/allen/presenter/MethodCache",
                    "printMethodInfo", "(I)V", false);
        }
    }
}
