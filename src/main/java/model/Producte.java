package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class Producte {
    private String codi_Producte;
    private String nom;
    private String descripcio;
    private float preuCompra;
    private float preuVenta;



    @Override
    public String toString() {
        String str="codi_Producte:" + codi_Producte +"\n"+"nom:" + nom +"\n"+ "descripcio:" + descripcio + "\n" + "preuCompra:" + preuCompra +"\n"+
                "preuVenta:" + preuVenta+"\n"+
                "===============================================================================";

        return   str;
    }
}
