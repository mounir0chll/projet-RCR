package fr.master.representationconnaissances.defauts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class TheorieDefauts {

    private final Set<String> faitsStricts = new LinkedHashSet<>();
    private final List<RegleStricte> reglesStrictes = new ArrayList<>();
    private final List<RegleDefaut> reglesDefaut = new ArrayList<>();

    public record RegleStricte(String condition, String conclusion) {
    }

    public void ajouterFaitStrict(String fait) {
        faitsStricts.add(fait);
    }

    public void ajouterRegleStricte(String condition, String conclusion) {
        reglesStrictes.add(new RegleStricte(condition, conclusion));
    }

    public void ajouterRegleDefaut(RegleDefaut regleDefaut) {
        reglesDefaut.add(regleDefaut);
    }

    public Set<String> faitsStricts() {
        return Collections.unmodifiableSet(faitsStricts);
    }

    public List<RegleStricte> reglesStrictes() {
        return Collections.unmodifiableList(reglesStrictes);
    }

    public List<RegleDefaut> reglesDefaut() {
        return Collections.unmodifiableList(reglesDefaut);
    }
}
