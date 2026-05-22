package fr.master.representationconnaissances.defauts;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RaisonneurDefautsTest {

    @Test
    void droneEstOperationnelQuandIlEstSeulementDroneDeMaintenance() {
        RaisonneurDefauts raisonneur = new RaisonneurDefauts();
        Set<String> extension = raisonneur.calculerExtension(DemonstrationLogiqueDefauts.scenarioDroneDisponible());

        assertTrue(extension.contains("DroneMaintenance(droneAlpha)"));
        assertTrue(extension.contains("Operationnel(droneAlpha)"));
    }

    @Test
    void droneNestPasOperationnelQuandIlEstEndommage() {
        RaisonneurDefauts raisonneur = new RaisonneurDefauts();
        Set<String> extension = raisonneur.calculerExtension(DemonstrationLogiqueDefauts.scenarioDroneEndommage());

        assertTrue(extension.contains("DroneEndommage(droneAlpha)"));
        assertTrue(extension.contains("DroneMaintenance(droneAlpha)"));
        assertTrue(extension.contains("NonOperationnel(droneAlpha)"));
        assertFalse(extension.contains("Operationnel(droneAlpha)"));
    }
}
