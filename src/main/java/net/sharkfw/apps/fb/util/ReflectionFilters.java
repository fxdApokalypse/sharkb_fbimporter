package net.sharkfw.apps.fb.util;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionFilters {
    private static final String GETTER_PREFIX = "get";
    public static final ReflectionUtils.FieldFilter IS_NOT_PUBLIC_STATIC_FINAL = Not(ReflectionUtils::isPublicStaticFinal);

    public static ReflectionUtils.FieldFilter Not(ReflectionUtils.FieldFilter filter) {
        return (field) -> {
            return !filter.matches(field);
        };
    }

    public static ReflectionUtils.FieldFilter And(ReflectionUtils.FieldFilter... filters) {
        return (field) -> {
            for ( int i = 0; i < filters.length; i++) {
                boolean matches = filters[i].matches(field);
                if (!matches) return matches;
            }
            return true;
        };
    }

    public static ReflectionUtils.FieldFilter Or(ReflectionUtils.FieldFilter... filters) {
        return (field) -> {
            for ( int i = 0; i < filters.length; i++) {
                boolean matches = filters[i].matches(field);
                if (matches) return matches;
            }
            return false;
        };
    }

    public static ReflectionUtils.MethodFilter Not(ReflectionUtils.MethodFilter filter) {
        return (field) -> {
            return !filter.matches(field);
        };
    }

    public static ReflectionUtils.MethodFilter And(ReflectionUtils.MethodFilter... filters) {
        return (field) -> {
            for ( int i = 0; i < filters.length; i++) {
                boolean matches = filters[i].matches(field);
                if (!matches) return matches;
            }
            return true;
        };
    }

    public static ReflectionUtils.MethodFilter Or(ReflectionUtils.MethodFilter... filters) {
        return (field) -> {
            for ( int i = 0; i < filters.length; i++) {
                boolean matches = filters[i].matches(field);
                if (matches) return matches;
            }
            return false;
        };
    }

    public static ReflectionUtils.FieldFilter fieldWithName(String name) {
        return (Field field) -> {
            return name.equals(field.getName());
        };
    }

    public static ReflectionUtils.FieldFilter fieldStartsWith(String name) {
        return (Field field) -> {
            return field.getName().startsWith(name);
        };
    }

    public static ReflectionUtils.FieldFilter fieldEndsWith(String name) {
        return (Field field) -> {
            return field.getName().endsWith(name);
        };
    }


    public static ReflectionUtils.MethodFilter methodWithName(String name) {
        return (Method method) -> {
            return name.equals(method.getName());
        };
    }

    public static ReflectionUtils.MethodFilter methodStartsWith(String name) {
        return (Method method) -> {
            return method.getName().startsWith(name);
        };
    }

    public static ReflectionUtils.MethodFilter methodEndsWith(String name) {
        return (Method method) -> {
            return method.getName().endsWith(name);
        };
    }

    public static ReflectionUtils.MethodFilter methodParamNum(int num) {
        return (Method method) -> {
            return method.getParameterCount() == num;
        };
    }

    public static ReflectionUtils.MethodFilter methodReturnType(Class type) {
        return (Method method) -> {
            return method.getReturnType().equals(type);
        };
    }

    public static ReflectionUtils.MethodFilter isGetterMethod(Class type) {
        return And(
            methodStartsWith("get"),
            methodParamNum(0),
            Not(methodReturnType(Void.class)),
            methodReturnType(type)
        );
    }

}
