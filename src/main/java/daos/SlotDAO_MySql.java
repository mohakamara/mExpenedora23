package daos;

import model.Producte;
import model.Slot;

import java.sql.*;
import java.util.ArrayList;

public class SlotDAO_MySql implements SlotDAO {
    private static final String DB_DRIVER="com.mysql.cj.jdbc.Driver";
    private static final String DB_ROUTE="jdbc:mysql://localhost:3306/expenedora";
    private static final String DB_USER="root";
    private static final String DB_PWD="";
    private static final String Mostrar_Slot ="SELECT *from slot";
    private static final String Instertar_Slot="INSERT INTO slot VALUES(?,?,?,?,?)";
    private static final String Update_Slot= "UPDATE slot SET Quantitat=Quantitat-1 WHERE Posicio=?";

    private Connection conn =null;



    public SlotDAO_MySql (){
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
    public void createSlot(Slot p) throws SQLException {

    }

    @Override
    public Slot readSlot() throws SQLException {
        return null;
    }

    @Override
    public ArrayList<Slot> readSlots() throws SQLException {
        ArrayList<Slot>slots=new ArrayList<Slot>();
        PreparedStatement ps=conn.prepareStatement(Mostrar_Slot);
        ResultSet rs= ps.executeQuery();
        while (rs.next()){
            Slot s=new Slot();
            s.setPosicio(rs.getInt("Posicio"));
            s.setQuantitat(rs.getInt("Quantitat"));
            s.setCodi_producte(rs.getString("Codi_Producte"));
            slots.add(s);
        }


        return slots;
    }

    @Override
    public void update(Slot p) throws SQLException {
        PreparedStatement ps=conn.prepareStatement(Update_Slot);
        ps.setInt(1,p.getQuantitat());
        ps.setInt(2,p.getPosicio());
        int rowCount=ps.executeUpdate();

    }

    @Override
    public void deleteSlot(Slot p) throws SQLException {

    }

    @Override
    public void deleteSlot(String codiProducte) throws SQLException {

    }
}
