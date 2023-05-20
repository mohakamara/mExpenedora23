package daos;

import model.Slot;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SlotDAO {
    public void createSlot(Slot p) throws SQLException;
    public Slot readSlot(int posicio)throws SQLException;
    public ArrayList<Slot> readSlots()throws SQLException;
    public void deleteSlot(Slot p)throws SQLException;
    public void deleteSlot(String codiProducte)throws SQLException;
    public void updateSlot(Slot p) throws SQLException;

}
