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
    static ArrayList<Producte>llistaProducte=new ArrayList<>();

    public static void main(String[] args) throws SQLException {

        // Producte p=new Producte("poma1","Poma","Poma Bona",0.8f,1.2f);
        //producteDAO.createProducte(p);
        int opcio = 0;
        do {
            mostrarMenu();
            opcio = sc.nextInt();
            switch (opcio) {
                case 1: mostrarMaquina();break;
                case 2: comprarProducte();break;

                case 10: mostrarInventari();break;
                case 11: afegirProducte();break;
                case 12: modificarMaquina();break;
                case 13: mostrarBenefici();break;

                case -1: System.out.println("Adeu");
                default: System.out.println("Opcio incorrecte");
            }
        } while (opcio != -1);

    }

    private static void mostrarBenefici() {
    }

    private static void modificarMaquina() {
    }

    private static void mostrarInventari() {
    }

    private static void comprarProducte() {
    }

    private static void afegirProducte() throws SQLException {
        System.out.println("Introdueix el codi del producte que vols entrar?");
        String codi=sc.nextLine();
        System.out.println("Introdueix el nom del producte que vols entrar");
        String nom=sc.nextLine();
        System.out.println("Introdueix la descripcio del producte que vols entrar");
        String descripcio=sc.nextLine();

        System.out.println("Introdueix el preu de compra producte que vols entrar");
        float preuCompra=sc.nextFloat();

        System.out.println("Introdueix el preu de venta del  producte que vols entrar");
        float preuVenta=sc.nextFloat();
        Producte producte = new Producte(codi,nom,descripcio,preuCompra,preuVenta);

try {
    producteDAO.createProducte(producte);
    ArrayList<Producte> llistaProductes = producteDAO.readProductes();
    for (Producte prod:llistaProducte)
    {
        System.out.println(prod);
    }
}catch (SQLException e){
    e.printStackTrace();
    System.out.println(e.getErrorCode());
}


    }

    private static void mostrarMaquina() throws SQLException {

        ArrayList<Producte> llistaProductes = producteDAO.readProductes();
        ArrayList<Slot> slot = slotDAO.readSlots();


        for (int i = 0; i < slot.size(); i++) {

            for (int j=0;j<llistaProductes.size();j++){

                if (slot.get(i).getCodi_producte().equals(llistaProductes.get(j).getCodi_Producte())){

                    System.out.println("Posicio_____________Producte________________Quantitat");
                    System.out.println(slot.get(i).getPosicio() + "                " + llistaProductes.get(i).getNom() + "                " + slot.get(i).getQuantitat());
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


