package nl.fourscouts.blog.validateddomain.domain;

import nl.fourscouts.blog.validateddomain.projections.BoxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Constraint(validatedBy = RoomAvailable.Validator.class)
public @interface RoomAvailable {
	String message() default "{nl.fourscouts.blog.constraints.RoomAvailable.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	@Component
	class Validator implements ConstraintValidator<RoomAvailable, AddItems> {
		private BoxRepository boxRepository;

		@Autowired
		public Validator(BoxRepository boxRepository) {
			this.boxRepository = boxRepository;
		}

		public void initialize(RoomAvailable constraintAnnotation) {}

		public boolean isValid(AddItems command, ConstraintValidatorContext context) {
			return boxRepository.findById(command.getBoxId()).map(box -> box.getAvailableRoom() >= command.getCount()).orElse(false);
		}
	}
}
