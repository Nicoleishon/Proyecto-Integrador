package com.mycompany.proyectointegrador.modelo;

import com.mycompany.proyectointegrador.modelo.MedicamentoPrescrito;
import java.util.ArrayList; // Importamos ArrayList
import java.util.List;
import java.util.Date;

public class Receta {

    private int idReceta;
    private Date fechaEmision;
    private Medico medico;
    private Paciente paciente;
    private List<MedicamentoPrescrito> medicamento; // Tu lista (está bien nombrada)
    private String observaciones;

  
    public Receta(int idReceta, Date fechaEmision, Medico medico, Paciente paciente, String observaciones) {
        this.idReceta = idReceta;
        this.fechaEmision = fechaEmision;
        this.medico = medico;
        this.paciente = paciente;
        this.observaciones = observaciones;
        this.medicamento = new ArrayList<>();
    }
    
    public int getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public List<MedicamentoPrescrito> getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(List<MedicamentoPrescrito> medicamento) {
        this.medicamento = medicamento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void agregarMedicamento(int idMedicamento, String nombre, String dosis, String frecuencia, Date fechaInicio, Date fechaFin) {
        MedicamentoPrescrito nuevoItem = new MedicamentoPrescrito(idMedicamento, nombre, dosis, frecuencia, fechaInicio, fechaFin);
        this.medicamento.add(nuevoItem);
    }
    
    public void eliminarMedicamento(MedicamentoPrescrito medicamentoPrescrito) {
        this.medicamento.remove(medicamentoPrescrito);
    }

    
}