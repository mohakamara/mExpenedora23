package daos;

import model.Producte;
import model.Slot;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SlotDAO {
    public void createSlot(Slot p) throws SQLException;
    public Slot readSlot()throws SQLException;
    public ArrayList<Slot> readSlots()throws SQLException;
    public void update(Slot p)throws SQLException;
    public void deleteSlot(Slot p)throws SQLException;
    public void deleteSlot(String codiProducte)throws SQLException;

}
