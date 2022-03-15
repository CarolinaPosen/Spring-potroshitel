package by.itacademy;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.Policy;

/**
 * SOURCE - Аннотация видна в SOURCE в байткоде
 * она видна не будет, например  @Override
 * CLASS - в байткоде - есть, через Reflection читать невозможно
 * RUNTIME - можно читать через Reflection
* */

@Retention(RetentionPolicy.RUNTIME)
public @interface InjectRandomInt {
    int min();
    int max();
}
