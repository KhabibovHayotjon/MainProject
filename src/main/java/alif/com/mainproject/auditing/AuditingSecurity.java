package alif.com.mainproject.auditing;

import alif.com.mainproject.entity.UserApp;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditingSecurity implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymous"))
        {
            return Optional.of(((UserApp) authentication.getPrincipal()).getId());
        }
        return Optional.empty();
    }
}
