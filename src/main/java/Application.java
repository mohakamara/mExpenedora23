import daos.ProducteDAO;
import daos.ProducteDAO_MySQL;
import daos.SlotDAO;
import daos.SlotDAO_MySql;
import model.Producte;
import model.Slot;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Application {
    static ProducteDAO producteDAO = new ProducteDAO_MySQL();
    static SlotDAO slotDAO = new SlotDAO_MySql();
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Producte> llistaProducte = new ArrayList<>();
    static ArrayList<Slot> slots = new ArrayList<>();


    public static void main(String[] args) throws SQLException {
        /**
         * Aqui tenim els switchs per a poder fer les diferents opcions del menu
         */
        Scanner lector = new Scanner(System.in);
        int opcio = 0;
        do {
            mostrarMenu();
            opcio = lector.nextInt();
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

    /**
     * Aqui tenim el metode de mostrarBen
     */
    private static void mostrarBenefici() {

        try {
            ArrayList<Slot> slots = slotDAO.readSlots();
            ArrayList<Producte> llistaProductes = producteDAO.readProductes();

            double benefici = 0;
            for (Slot slot : slots) {
                for (Producte producte : llistaProductes) {
                    if (slot.getCodi_producte().equals(producte.getCodi_Producte())) {
                        benefici += (producte.getPreuVenta() - producte.getPreuCompra()) * slot.getQuantitat();
                    }
                }
                System.out.println("El benefici es de: " + benefici + "€");
            }

        }catch (Exception e){
            System.out.println("No hi ha cap benefici");
        }

    }

    /**
     * Aqui tenim el metodode de modificar la maquina on modifiquem el stock dels productes*
     *
     * @throws SQLException
     */
    private static void modificarMaquina() throws SQLException {
        System.out.print("Introdueix el nom del producte que vols modificar l'stock: ");
        String nomSel = sc.nextLine();

        int stock;
        for (Producte producte : llistaProducte) {
            if (producte.getNom().equals(nomSel)) {
                for (Slot slot : slots) {
                    if (slot.getCodi_producte().equals(producte.getCodi_Producte())) {
                        stock = slot.getQuantitat();

                        System.out.println();
                        System.out.print("Introdueix el nou stock: ");
                        int stockNou = sc.nextInt();

                        slot.setQuantitat(stockNou);

                        slotDAO.updateSlot(slot);

                    }
                }
            }
        }
    }

    /**
     * Aqui tenim el metode de MostrarInventari on mostrem tots els productes que tenim a la maquina
     */
    private static void mostrarInventari() {
        try {
            ArrayList<Producte> productes = producteDAO.readProductes();
            for (Producte producte : productes) {
                System.out.println(producte);
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /**
     * Aqui tenim el metode de comprar producte on comprem el producte que volem
     *
     * @throws SQLException
     */
    private static void comprarProducte() throws SQLException {
        mostrarMaquina();
        try {
            ArrayList<Slot> slots = slotDAO.readSlots();
            ArrayList<Producte> productes = producteDAO.readProductes();

            System.out.println("Introdueix la posicio que vols comprar");
            int posicioSel = sc.nextInt();

            for (Slot slot : slots) {
                if (slot.getPosicio() == posicioSel) {
                    if (slot.getQuantitat() < 1) {
                        System.out.println("NO hi ha  stock!!");
                    } else {
                        slot.setQuantitat(slot.getQuantitat() - 1);

                        slotDAO.updateSlot(slot);
                        System.out.println("Producte comprat!!");
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /**
     * Aqui tenim el metodde de afegir producte on afegim un producte a la maquina i l'slot
     *
     * @throws SQLException
     */
    private static void afegirProducte() throws SQLException {
        System.out.println("Introdueix el codi del producte: ");
        String codi = sc.nextLine();
        System.out.println("Introdueix el nom del producte: ");
        String nom = sc.nextLine();
        System.out.println("Introdueix la descripcio del producte: ");
        String descripcio = sc.nextLine();
        System.out.println("Introdueix el preu del producte: ");
        float preu = sc.nextFloat();
        System.out.println("Introdueix el preu de venta del producte: ");
        float preuVenta = sc.nextFloat();
        Producte p = new Producte(codi, nom, descripcio, preu, preuVenta);
        llistaProducte.add(p);

        System.out.println("Introdueix la posicio del producte: ");
        int posicio = sc.nextInt();
        System.out.println("Introdueix la quantitat del producte: ");
        int quantitat = sc.nextInt();
        Slot s = new Slot(posicio, quantitat, codi);
        slots.add(s);


        try {
            producteDAO.createProducte(p);
            ArrayList<Producte> llistaProductes = producteDAO.readProductes();
            for (Producte prod : llistaProducte) {
                System.out.println(prod);
            }
            slotDAO.createSlot(s);
            ArrayList<Slot> slots = slotDAO.readSlots();
            for (Slot slot : slots) {
                System.out.println(slot);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getErrorCode());
        }
    }

    /**
     * Aqui tenim el mostrar maquina on mostrem la maquina amb els seus productes i els slots
     *
     * @throws SQLException
     */
    private static void mostrarMaquina() throws SQLException {

        ArrayList<Producte> llistaProductes = producteDAO.readProductes();
        ArrayList<Slot> slot = slotDAO.readSlots();


        for (int i = 0; i < slot.size(); i++) {
            System.out.print("Posicio " + slot.get(i).getPosicio() + "_____________" + "Quantitat " + slot.get(i).getQuantitat());

            for (int j = 0; j < llistaProductes.size(); j++) {


                if (slot.get(i).getCodi_producte().equals(llistaProductes.get(j).getCodi_Producte())) {

                    System.out.print("_____________Producte");
                    System.out.println("                " + llistaProductes.get(j).getNom());

                }
            }
        }
    }

    /**
     * Aqui tenim el metode de mostrar menu on mostrem el menu de la maquina
     */
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


