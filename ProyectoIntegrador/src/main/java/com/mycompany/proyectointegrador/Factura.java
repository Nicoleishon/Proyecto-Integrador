
package com.mycompany.proyectointegrador;
import java.util.List;


public class Factura {
    private String facturaId;
    private String fecha;
    private double monto;
    private List<String> items;
    private boolean pagada;

    public Factura(String facturaId, String fecha, double monto, List<String> items, boolean pagada) {
        this.facturaId = facturaId;
        this.fecha = fecha;
        this.monto = monto;
        this.items = items;
        this.pagada = pagada;
    }
    
    public String generar(){
        
    }
    
    public boolean pagar(String modoPago){
        
    }
}
