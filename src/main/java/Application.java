import daos.ProducteDAO;
import daos.ProducteDAO_MySQL;
import daos.SlotDAO;
import daos.SlotDAO_MySql;
import model.Producte;
import model.Slot;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.SortedMap;

public class Application {
    static ProducteDAO producteDAO = new ProducteDAO_MySQL();
    static SlotDAO slotDAO = new SlotDAO_MySql();
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Producte> llistaProducte = new ArrayList<>();
    static ArrayList<Slot> slots = new ArrayList<>();

    public static void main(String[] args) throws SQLException {

        // Producte p=new Producte("poma1","Poma","Poma Bona",0.8f,1.2f);
        //producteDAO.createProducte(p);
        int opcio = 0;
        do {
            mostrarMenu();
            opcio = sc.nextInt();
            switch (opcio) {
                case 1:
                    mostrarMaquina();
                    break;
                case 2:
                    comprarProducte();
                    break;

                case 10:
                    mostrarInventari();
                    break;
                case 11:
                    afegirProducte();
                    break;
                case 12:
                    modificarMaquina();
                    break;
                case 13:
                    mostrarBenefici();
                    break;

                case -1:
                    System.out.println("Adeu");
                default:
                    System.out.println("Opcio incorrecte");
            }
        } while (opcio != -1);

    }

    private static void mostrarBenefici() {
        float benefici = 0;
        try {
            ArrayList<Slot> slots = (ArrayList<Slot>) slotDAO.readSlots();
            for (Slot slot : slots) {
                Producte producte = producteDAO.readProducte(slot.getCodi_producte());
                benefici += (producte.getPreuVenta() - producte.getPreuCompra()) * slot.getQuantitat();
            }
            System.out.println("El benefici es de " + benefici);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void modificarMaquina() throws SQLException {
        int opcio = sc.nextInt();
        switch (opcio) {
            case 1:
                modificarPosicio();
                break;
            case 2:
                modificarStock();
                break;
            case 3:
                afegirRanures();
                break;
            default:
                System.out.println("Opcio incorrecte");
        }

    }
    private static void afegirRanures() throws SQLException {
        System.out.println("Introdueix la posicio on vols afegir ranures");
        int posicio = sc.nextInt();
        System.out.println("Introdueix el numero de ranures que vols afegir");
        int ranures = sc.nextInt();
        Slot slot = slotDAO.readSlot(posicio);
        slot.setQuantitat(slot.getQuantitat() + ranures);
    }

    private static void modificarStock() {

    }

    private static void modificarPosicio() {
    }

    private static void mostrarInventari() {
        try {
            ArrayList<Producte> productes = (ArrayList<Producte>) producteDAO.readProductes();
            for (Producte p : productes) {
                System.out.println(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void comprarProducte() throws SQLException {
        System.out.println("Introdueix la posicio del producte que vols comprar?");
        int posicio = sc.nextInt();
        Slot slot = slotDAO.readSlot(posicio);
        if (slot.getQuantitat() > 0) {
            slot.setQuantitat(slot.getQuantitat() - 1);
            slotDAO.update(slot);
            System.out.println("Has comprat un producte");
        } else {
            System.out.println("No hi ha productes");
        }
    }

    private static void afegirProducte() throws SQLException {
        System.out.println("Introdueix el codi del producte que vols entrar?");
        String codi = sc.next();
        System.out.println("Introdueix el nom del producte que vols entrar");
        String nom = sc.next();
        System.out.println("Introdueix la descripcio del producte que vols entrar");
        String descripcio = sc.next();

        System.out.println("Introdueix el preu de compra producte que vols entrar");
        float preuCompra = sc.nextFloat();

        System.out.println("Introdueix el preu de venta del  producte que vols entrar");
        float preuVenta = sc.nextFloat();
        Producte producte = new Producte(codi, nom, descripcio, preuCompra, preuVenta);
        llistaProducte.add(producte);

        System.out.println("Introdueix posicio del producte que vols entrar");
        int posicio = sc.nextInt();
        System.out.println("Introdueix quantitat del producte que vols entrar");
        int quantitat = sc.nextInt();
        Slot slot = new Slot(posicio, quantitat, codi);
        slots.add(slot);

        try {
            producteDAO.createProducte(producte);
            ArrayList<Producte> llistaProductes = producteDAO.readProductes();
            for (Producte prod : llistaProducte) {
                System.out.println(prod);
            }
            slotDAO.createSlot(slot);
            ArrayList<Slot> slots = slotDAO.readSlots();
            for (Slot slot1 : slots) {
                System.out.println(slot1);
            }

            }catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getErrorCode());
        }
    }

    private static void mostrarMaquina() throws SQLException {

        ArrayList<Producte> llistaProductes = producteDAO.readProductes();
        ArrayList<Slot> slot = slotDAO.readSlots();


        for (int i = 0; i < slot.size(); i++) {
            System.out.print("Posicio " +slot.get(i).getPosicio()+"_____________" + "Quantitat "+slot.get(i).getQuantitat());

            for (int j=0;j<llistaProductes.size();j++){


                if (slot.get(i).getCodi_producte().equals(llistaProductes.get(j).getCodi_Producte())){

                    System.out.print("_____________Producte");
                    System.out.println( "                " + llistaProductes.get(j).getNom());
                }
            }

        }
    }

    private static void mostrarMenu() {
        System.out.println("\nMenú de la màquina expenedora");
        System.out.println("=============================");
        System.out.println("Selecciona la operació a realitzar introduïnt el número corresponent: \n");


        //Opcions per client / usuari
        System.out.println("[1] Mostrar Posició / Nom producte / Stock de la màquina");
        System.out.println("[2] Comprar un producte");

        //Opcions per administrador / manteniment
        System.out.println();
        System.out.println("[10] Mostrar llistat productes disponibles (BD)");
        System.out.println("[11] Afegir productes disponibles");
        System.out.println("[12] Assignar productes / stock a la màquina");
        System.out.println("[13] Mostrar benefici");

        System.out.println();
        System.out.println("[-1] Sortir de l'aplicació");

    }
}


