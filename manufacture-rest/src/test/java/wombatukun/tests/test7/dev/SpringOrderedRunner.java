package wombatukun.tests.test7.dev;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.stream.Collectors;

public class SpringOrderedRunner extends SpringJUnit4ClassRunner {

	public SpringOrderedRunner(Class klass) throws InitializationError {
		super(klass);
	}

	@Override
	protected List<FrameworkMethod> computeTestMethods() {
		List<FrameworkMethod> list = super.computeTestMethods();
		return list.stream().sorted((m1, m2) ->
				m1.getMethod().getAnnotation(MethodOrder.class).order() - m2.getMethod().getAnnotation(MethodOrder.class).order())
				.collect(Collectors.toList());
	}
}

