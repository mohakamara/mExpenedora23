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

     Scanner lector=new Scanner(System.in);
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

    private static void mostrarBenefici() {
    }

    private static void modificarMaquina() throws SQLException {
        System.out.print("Introdueix el nom del producte que vols modificar l'stock: ");
        String nomSel = sc.nextLine();

        int stock;
        for (Producte producte : llistaProducte){
            if (producte.getNom().equals(nomSel)){
                for (Slot slot : slots){
                    if (slot.getCodi_producte().equals(producte.getCodi_Producte())){
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

    private static void mostrarInventari() {
        for (Producte prod:llistaProducte)
        {
            System.out.println(prod);
        }

    }

    private static void comprarProducte() throws SQLException {
        mostrarMaquina();
        System.out.println("Introdueix el nom del producte que vols comprar");
        String producteComprar= sc.next();

       for (Producte producte:llistaProducte){
           if (producteComprar.equals(producte.getNom())){
               for (Slot slot:slots){
                   if (slot.getCodi_producte().equals(producte.getCodi_Producte())){
                       slot.setQuantitat(slot.getQuantitat()-1);
                       slotDAO.updateSlot(slot);
                       System.out.println("Producte comprat!!!");


                   }
               }
           }
       }

    }

    private static void afegirProducte() throws SQLException {
        Producte p=new Producte();

        Scanner lector=new Scanner(System.in);
        System.out.println("Introdueix el codi del producte que vols entrar?");
        p.setCodi_Producte(lector.next());
        System.out.println("Introdueix el nom del producte que vols entrar");
        p.setNom(lector.next());
        System.out.println("Introdueix la descripcio del producte que vols entrar");
        p.setDescripcio(lector.next());
        System.out.println("Introdueix el preu de compra producte que vols entrar");
        p.setPreuCompra(lector.nextFloat());
        System.out.println("Introdueix el preu de venta del  producte que vols entrar");
        p.setPreuVenta(lector.nextFloat());
        ;


        try {
            producteDAO.createProducte(p);
            ArrayList<Producte> llistaProductes = producteDAO.readProductes();
            for (Producte prod : llistaProducte) {
                System.out.println(prod);
            }
        } catch (SQLException e) {
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


