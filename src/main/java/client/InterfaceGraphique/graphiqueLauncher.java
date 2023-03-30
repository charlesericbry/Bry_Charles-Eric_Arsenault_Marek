package client.InterfaceGraphique;



public class graphiqueLauncher {
    public final static int PORT = 1337;
    public static void main(String[] args) {
        try {
            Modele modele = new Modele(PORT);
            Vue.launch(args);
            modele.run();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
