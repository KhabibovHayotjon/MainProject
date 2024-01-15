package alif.com.mainproject.aop;

import alif.com.mainproject.entity.UserApp;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@Component
@Aspect
public class CheckPermissionImpl {
    @Before(value = "@annotation(checkPermission)")
    public void checkPermissionUser(CheckPermission checkPermission) throws AccessDeniedException {
        UserApp userApp = (UserApp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for (GrantedAuthority authority : userApp.getAuthorities()) {
            if (authority.getAuthority().equals(checkPermission.permission())){
                return;
            }
        }
        throw new AccessDeniedException("access denied");
    }
}
