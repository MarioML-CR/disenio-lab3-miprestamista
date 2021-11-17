package cr.fi.migarrotera;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.inject.Named;

@Named
public class ProcesarSolicitud implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String nombre = (String) delegateExecution.getVariable("nombre");
        String apellido1 = (String) delegateExecution.getVariable("apellido1");
        String apellido2 = (String) delegateExecution.getVariable("apellido2");
        String tipoTrabajo = (String) delegateExecution.getVariable("tipoTrabajo");
        String ingreso1 = (String) delegateExecution.getVariable("ingreso");
        String montoSolicitado1 = (String) delegateExecution.getVariable("montoSolicitado");
        String estados = (String) delegateExecution.getVariable("estados");

        float ingreso = Float.parseFloat(ingreso1);
        float montoSolicitado = Float.parseFloat(montoSolicitado1);

        String mensaje;
        if (isIndependiente(tipoTrabajo)) {
            if (presentaEstados(estados)) {
                if (verificarMonto(ingreso, montoSolicitado)) {
                    mensaje = "Aprobado";
                } else {
                    mensaje = "Monto excede su límite";
                }
            } else {
                mensaje = "Debe presentar estados";
            }
        } else {
            if (verificarMonto(ingreso, montoSolicitado)) {
                mensaje = "Aprobado";
            } else {
                mensaje = "Monto excede su límite";
            }
        }
        System.out.println(nombre + " " + apellido1 + " " + apellido2 + ": " + mensaje);
    }
    public boolean isIndependiente(String tTrabajo){
        if(tTrabajo.equals("Independiente")){
            return true;
        }
            return false;
    }
    public boolean presentaEstados(String estados){
        if (estados.equals("presenta")) {
            return true;
        }
            return false;
    }
    public boolean verificarMonto(float ingreso, float montoSolicitado){
        if ((montoSolicitado / ingreso) < 0.8) {
            return true;
        }
            return false;
    }
}
