package nl.fourscouts.blog.secureddomain.domain.security;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.InterceptorChain;
import org.axonframework.messaging.MessageHandlerInterceptor;
import org.axonframework.messaging.unitofwork.UnitOfWork;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static java.util.function.Predicate.isEqual;

@Slf4j
@Component
public class RoleInterceptor implements MessageHandlerInterceptor<CommandMessage<?>> {
	public Object handle(UnitOfWork<? extends CommandMessage<?>> unitOfWork, InterceptorChain interceptorChain) throws Exception {
		CommandMessage<?> message = unitOfWork.getMessage();

		if (message.getPayloadType().isAnnotationPresent(RequiresManager.class)) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if ((authentication != null) && authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).noneMatch(isEqual("ROLE_MANAGER"))) {
				logger.warn("discarded command {} because {} is not a manager", message.getCommandName(), authentication.getName());

				throw new RoleRequired();
			}
		}

		return interceptorChain.proceed();
	}
}
