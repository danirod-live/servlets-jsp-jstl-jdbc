package es.makigas.ejemplos.servlethola;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class NameStorage {

    private List<String> names;
    
    public NameStorage() {
        this.names = new ArrayList<>();
    }

    public void addName(String name) {
        this.names.add(name);
    }
    
    public List<String> getNames() {
        return Collections.unmodifiableList(names);
    }
}
