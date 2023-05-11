import daos.ProducteDAO;
import daos.ProducteDAO_MySQL;
import model.Producte;

import java.sql.SQLException;
import java.util.ArrayList;

public class Application {
    public static void main(String[] args) throws SQLException {

        ProducteDAO producteDAO=new ProducteDAO_MySQL();
        ArrayList<Producte>llistaProductes=producteDAO.readProductes();
        llistaProductes.forEach(f-> System.out.println(f));

        Producte p=new Producte("poma1","Poma","Poma Bona",0.8f,1.2f);
        producteDAO.createProducte(p);


    }

}
