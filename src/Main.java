import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static Connection conn = null;
    private static Scanner sc = new Scanner(System.in);
    private static Statement state;

    public static void getHeroes() {
        try{
            state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String query = "SELECT * FROM Hero";

            ResultSet res = state.executeQuery(query);
            while (res.next()){
                System.out.println(res.getInt( "id") + " : " + res.getString( "Nom"));
            }

            res.close();
            state.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void getHero(){
        getHeroes();
        int choice = sc.nextInt();
        sc.nextLine();

        try{
            state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String query = "SELECT * FROM Hero WHERE id = ?";

            PreparedStatement prepare = conn.prepareStatement(query);
            prepare.setInt(1, choice);

            ResultSet res = prepare.executeQuery();
            ResultSetMetaData resultSetMetaData = res.getMetaData();

            res.first();

            for(int i = 2; i <= resultSetMetaData.getColumnCount(); i++){
                System.out.println(resultSetMetaData.getColumnName(i) + " : " + res.getObject(i));
            }

            prepare.close();
            res.close();
            state.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void createHero(){

        String string;
        int integer;

        try{
            state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String query = "INSERT INTO hero (Type, Nom, Image, NiveauVie, NiveauForce, Offense, Bouclier) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement prepare = conn.prepareStatement(query);

            System.out.println("1 : Guerrier - 2 : Magicien");
            integer = sc.nextInt();
            sc.nextLine();

            if(integer == 1){
                prepare.setString(1, "Warrior");
            } else {
                prepare.setString(1, "Wizard");
            }

            System.out.println("Nom ?");
            string = sc.nextLine();
            prepare.setString(2, string);

            System.out.println("Image ?");
            string = sc.nextLine();
            prepare.setString(3, string);

            System.out.println("Niveau de vie ?");
            integer = sc.nextInt();
            sc.nextLine();
            prepare.setInt(4, integer);

            System.out.println("Niveau de force ?");
            integer = sc.nextInt();
            sc.nextLine();
            prepare.setInt(5, integer);

            System.out.println("Nom de l'arme ?");
            string = sc.nextLine();
            prepare.setString(6, string);

            System.out.println("Nom du bouclier ?");
            string = sc.nextLine();
            prepare.setString(7, string);

            prepare.executeUpdate();

            prepare.close();
            state.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void deleteHero(){
        getHeroes();
        int choice = sc.nextInt();
        sc.nextLine();

        try{
            state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String query = "DELETE FROM Hero WHERE id = ?";

            PreparedStatement prepare = conn.prepareStatement(query);
            prepare.setInt(1, choice);

            prepare.executeUpdate();

            prepare.close();
            state.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void updateHero(){
        getHeroes();
        int choice = sc.nextInt();
        sc.nextLine();

        try{
            state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String query = "UPDATE Hero SET Nom = ? WHERE id = ?";

            PreparedStatement prepare = conn.prepareStatement(query);
            prepare.setInt(2, choice);

            System.out.println("Nouveau nom ?");
            String string = sc.nextLine();
            prepare.setString(1, string);

            prepare.executeUpdate();

            prepare.close();
            state.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void menu(){
        System.out.println("\n1 : Afficher les héros \n2 : Détail hero \n3 : Créer un héro \n4 : Modifier un héro \n5 : Supprimer un héro");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice){
            case 1:
                getHeroes();
                break;
            case 2:
                getHero();
                break;
            case 3:
                createHero();
                break;
            case 4:
                updateHero();
                break;
            case 5:
                deleteHero();
                break;
        }

        menu();
    }

    public static void main(String[] args) {

        try {
            Class.forName( "com.mysql.cj.jdbc.Driver" );

            String url = "jdbc:mysql://localhost:3306/warrior";
            String user = "root";
            String passwd = "";

            conn = DriverManager.getConnection(url, user, passwd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        menu();

    }
}
