package com.tms.model.annotations;

import com.tms.util.SixSevenValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = SixSevenValidator.class) //Класс который будет иметь обработчик этой аннотации
public @interface SixSeven {
    String message() default "Invalid value, should be 67!";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
