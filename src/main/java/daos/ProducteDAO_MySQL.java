package daos;

import model.Producte;

import java.sql.*;
import java.util.ArrayList;

public class ProducteDAO_MySQL implements  ProducteDAO{
    private static final String DB_DRIVER="com.mysql.cj.jdbc.Driver";
    private static final String DB_ROUTE="jdbc:mysql://localhost:3306/expenedora";
    private static final String DB_USER="root";
    private static final String DB_PWD="";
    private static final String Mostrar_Productes ="SELECT *from producte";
    private static final String Instertar_Producte="INSERT INTO producte VALUES(?,?,?,?,?)";

    private Connection conn =null;

    public ProducteDAO_MySQL(){
        try {
            Class.forName(DB_DRIVER);
            conn= DriverManager.getConnection(DB_ROUTE,DB_USER,DB_PWD);
            System.out.println("Connexio establerta satisfactoriament");

        }catch (Exception e){
            System.out.println("S'ha produit un error en intentar connectar amb la base de dades.Revisa els parametres");
            System.out.println(e);
        }
    }
    @Override
    public void createProducte(Producte p) throws SQLException {

        PreparedStatement ps=conn.prepareStatement(Instertar_Producte);
        ps.setString(1,p.getCodi_Producte());
        ps.setString(2,p.getNom());
        ps.setString(3,p.getDescripcio());
        ps.setFloat(4,p.getPreuCompra());
        ps.setFloat(5,p.getPreuVenta());

        int rowCount=ps.executeUpdate();

    }

    @Override
    public Producte readProducte(String codiProducte) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<Producte> readProductes() throws SQLException {

        ArrayList<Producte>llistaProductes=new ArrayList<Producte>();
        PreparedStatement ps=conn.prepareStatement(Mostrar_Productes);
        ResultSet rs= ps.executeQuery();
        while (rs.next()){
            Producte p=new Producte();
            p.setCodi_Producte(rs.getString("codi_producte"));
            p.setNom(rs.getString("nom"));
            p.setDescripcio(rs.getString("descripcio"));
            p.setPreuCompra(rs.getFloat("preu_copmra"));
            p.setPreuVenta(rs.getFloat("preu_venta"));
            llistaProductes.add(p);
        }

        return llistaProductes;
    }

    @Override
    public void update(Producte p) throws SQLException {


    }

    @Override
    public void deleteProducte(Producte p) throws SQLException {

    }

    @Override
    public void deleteProducte(String codiProducte) throws SQLException {

    }
}
