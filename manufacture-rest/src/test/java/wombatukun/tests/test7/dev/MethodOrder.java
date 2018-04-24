package wombatukun.tests.test7.dev;

@java.lang.annotation.Target(value = { java.lang.annotation.ElementType.METHOD })
@java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface MethodOrder {
	int order() default 1;
}
