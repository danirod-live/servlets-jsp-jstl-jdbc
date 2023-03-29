package es.makigas.ejemplos.servlethola;

import jakarta.enterprise.context.ApplicationScoped;
import java.time.Instant;

@ApplicationScoped
public class RelojService {

    public String getTime() {
        return Instant.now().toString();
    }
    
}
