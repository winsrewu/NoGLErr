package org.jawbts.noglerr.tweak.var.javascript;

import org.jawbts.noglerr.config.Configs;
import org.jawbts.noglerr.event.ClientTickHandler;
import org.jawbts.noglerr.tweak.var.javascript.proxy.World;
import org.jawbts.noglerr.util.PlayerMessageSender;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Utils {
    public PlayerMessageSender getPms() {
        return PlayerMessageSender.getInstance();
    }

    public void sendMes(String s) {
        PlayerMessageSender.getInstance().add(s);
    }

    public World getWorld() {
        return new World(ClientTickHandler.mc.world);
    }

    public boolean sendMesToPublic(String s) {
        if (ClientTickHandler.mc.player == null) return false;
        ClientTickHandler.mc.player.sendChatMessage(s);
        return true;
    }

    public Object getField(Object obj, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        Field field;
        Class<?> clazz = obj.getClass();
        while (true) {
            try {
                field = clazz.getDeclaredField(fieldName);
                break;
            } catch (NoSuchFieldException e) {
                if (clazz.getSuperclass() == null) {
                    throw e;
                }
                clazz = clazz.getSuperclass();
            }
        }
        field.setAccessible(true);
        return field.get(obj);
    }

    public void setField(Object obj, String fieldName, Object value)
            throws NoSuchFieldException, IllegalAccessException {
        Field field;
        Class<?> clazz = obj.getClass();
        while (true) {
            try {
                field = clazz.getDeclaredField(fieldName);
                break;
            } catch (NoSuchFieldException e) {
                if (clazz.getSuperclass() == null) {
                    throw e;
                }
                clazz = clazz.getSuperclass();
            }
        }
        field.setAccessible(true);
        field.set(obj, value);
    }

    public Object invokeMethod(Object obj, String methodName, String targetSignature, Object... args)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Class<?> clazz = obj.getClass();
        while (true) {
            try {
                for (Method method : clazz.getDeclaredMethods()) {
                    // 生成方法签名
                    String signature = getMethodSignature(method);
                    if (Configs.Toggles.DEBUG.getBooleanValue()) {
                        PlayerMessageSender.getInstance().add("available method: " + signature);
                    }

                    if (signature.equals(targetSignature)) {
                        method.setAccessible(true);
                        if (Configs.Toggles.DEBUG.getBooleanValue()) {
                            PlayerMessageSender.getInstance().add("Utils.invokeMethod: " + signature + " " + Arrays.toString(getClasses(args)));
                        }
                        return method.invoke(obj, args);
                    }
                }
                throw new NoSuchMethodException(methodName + " with signature " + targetSignature);
            } catch (NoSuchMethodException e) {
                if (clazz.getSuperclass() == null) {
                    throw e;
                }
                clazz = clazz.getSuperclass();
            }
        }
    }

    private Class<?>[] getClasses(Object... args) {
        Class<?>[] classes = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            classes[i] = args[i].getClass();
        }
        if (Configs.Toggles.DEBUG.getBooleanValue()) {
            PlayerMessageSender.getInstance().add("Utils.getClasses: " + Arrays.toString(classes));
        }
        return classes;
    }

    public String getMethodSignature(Method method) {
        StringBuilder signature = new StringBuilder();

        // 方法名
        signature.append(method.getName());

        // 参数类型
        signature.append("(");
        for (Class<?> paramType : method.getParameterTypes()) {
            signature.append(getInternalName(paramType));
        }
        signature.append(")");

        // 返回类型
        signature.append(getInternalName(method.getReturnType()));

        return signature.toString();
    }

    private String getInternalName(Class<?> clazz) {
        if (clazz.isPrimitive()) {
            return getPrimitiveDescriptor(clazz);
        } else {
            return "L" + clazz.getName().replace('.', '/') + ";";
        }
    }

    private String getPrimitiveDescriptor(Class<?> clazz) {
        if (clazz == int.class) return "I";
        if (clazz == byte.class) return "B";
        if (clazz == char.class) return "C";
        if (clazz == double.class) return "D";
        if (clazz == float.class) return "F";
        if (clazz == long.class) return "J";
        if (clazz == short.class) return "S";
        if (clazz == boolean.class) return "Z";
        if (clazz == void.class) return "V";
        throw new IllegalArgumentException("Unknown primitive type: " + clazz.getName());
    }
}
