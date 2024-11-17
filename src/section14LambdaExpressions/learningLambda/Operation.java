package section14LambdaExpressions.learningLambda;

@FunctionalInterface
public interface Operation<T> {

    T operate(T value1, T value2);
}
