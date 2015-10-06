import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Catherine on 9/30/2015.
 */
public class Sudoku {

    protected Integer[][] jeu = new Integer[9][9];
    private Ligne[] listeLigne = new Ligne[9];
    private Colonne[] listeColonne = new Colonne[9];
    private Carre[] listeCarre = new Carre[9];
    private Integer[] numerosTab = {1,2,3,4,5,6,7,8,9};
    private boolean resolu = false;

    //Creer le jeu en assignant les valeurs pour les lignes et colonnes
    public void creerJeu(Integer[][] jeu) {
        int noDeLigne = 0;
        int noDeColonne = 0;
        //Construction des lignes, je pense
        for (noDeLigne = 0; noDeLigne < 9; noDeLigne++) {
            Ligne ligne = new Ligne();
            for (noDeColonne = 0; noDeColonne < 9; noDeColonne++) {
                ligne.chiffre.add(jeu[noDeLigne][noDeColonne]);
            }
            this.listeLigne[noDeLigne] = ligne;
        }

        //Construction des colonnes, je pense
        for (noDeColonne = 0; noDeColonne < 9; noDeColonne++) {
            Colonne colonne = new Colonne();
            for (noDeLigne = 0; noDeLigne < 9; noDeLigne++) {
                colonne.chiffre.add(jeu[noDeLigne][noDeColonne]);
            }
            this.listeColonne[noDeColonne] = colonne;
        }

        //Construction carre, elle pense (haha tres tres  drole marc andre majeau
        construireCarre(0, 0, 0);
        construireCarre(0, 3, 1);
        construireCarre(0, 6, 2);
        construireCarre(3, 0, 3);
        construireCarre(3, 3, 4);
        construireCarre(3, 6, 5);
        construireCarre(6, 0, 6);
        construireCarre(6, 3, 7);
        construireCarre(6, 6, 8);
    }

    //Remplir les sections avec les numeros contenu dans la section
    public void construireCarre(int ligne, int colonne, int index) {

        Carre carre = new Carre();
        for (int i = ligne; i < ligne + 3; i++) {
            for (int j = colonne; j < colonne + 3; j++) {
                carre.chiffre.add(jeu[i][j]);
            }
        }
        this.listeCarre[index] = carre;
    }
    //==============================================================================================================
    //------------------------------------------------------JEU-----------------------------------------------------
    //==============================================================================================================

    public boolean jouer() {
        int indexNumTab =0;
        ArrayList<Integer> usedIndex = new ArrayList<>();
        boolean valide = true;
        //Si le jeu est resolu ou on as tous essaye les numeros dla table
        if(resolu == true || indexNumTab >= 9){
            return true;
        }
        //Parcour des lignes
        for(int i=0; i<9; i++){
            //Parcour des colonnes
            for (int j=0; j<9; j++){
                if (jeu[i][j] == 0){
                    while (resolu == false && indexNumTab < 9) {
                        if (estLibre(i, j, getCarre(i, j), numerosTab[indexNumTab]) && !usedIndex.contains(indexNumTab)) {
                            jeu[i][j] = numerosTab[indexNumTab];
                            listeLigne[i].chiffre.add(numerosTab[indexNumTab]);
                            listeColonne[j].chiffre.add(numerosTab[indexNumTab]);
                            int indexCarre = getCarre(i,j);
                            listeCarre[indexCarre].chiffre.add(numerosTab[indexNumTab]);
                            usedIndex.add(indexNumTab);
                            valide = jouer();
                            //Si on trouve pas de solutions avec la valeur mise en place, on remet la valeur a zero et on reesaye avec d"autre
                            if (valide == false){
                                jeu[i][j] = 0;
                                listeLigne[i].chiffre.remove(numerosTab[indexNumTab]);
                                listeColonne[j].chiffre.remove(numerosTab[indexNumTab]);
                                listeCarre[indexCarre].chiffre.remove(numerosTab[indexNumTab]);
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            indexNumTab ++;
                        }
                    }
                    if (indexNumTab >= 9) {
                        return false;
                    }
                }
            }
        }
        if (indexNumTab >= 9) {
            return false;
        }
        else {
            return true;
        }
    }

    public int getCarre(int ligne, int colonne){
        int indexCarre = 10;
        if (ligne < 3 && colonne < 3){
            indexCarre = 0;
        }
        else if (ligne < 3 && colonne < 6){
            indexCarre = 1;
        }
        else if (ligne < 3 && colonne < 9){
            indexCarre = 2;
        }
        else if (ligne < 6 && colonne < 3){
            indexCarre = 3;
        }
        else if (ligne < 6 && colonne < 6){
            indexCarre = 4;
        }
        else if (ligne < 6 && colonne < 9){
            indexCarre = 5;
        }
        else if (ligne < 9 && colonne < 3){
            indexCarre = 6;
        }
        else if (ligne < 9 && colonne < 6){
            indexCarre = 7;
        }
        else if (ligne < 9 && colonne < 9){
            indexCarre = 8;
        }
        return indexCarre;
    }

    //Verifie si la case es libre
    public boolean estLibre(int ligne, int colonne, int carre,int num) {
        boolean libre = true;
        if (listeColonne[colonne].chiffre.contains(num)){
           libre = false;
        }
        if (listeLigne[ligne].chiffre.contains(num)){
            libre = false;
        }
        if (listeCarre[carre].chiffre.contains(num)){
            libre = false;
        }
        return libre;
    }

    //Cette fonction permet de lire le fichier avant la creation du jeu
    public void lireFichier(File f)
    {
        String ligne;
        int noDeLigne = 0;
        //Lire le fichier
        try  {
            BufferedReader br = new BufferedReader(new FileReader(f));
            //Pour chaque ligne on lit la ligne et remplit chaque colonne avec les caracteres, on augmente ensuite noDeLigne pour remplir la ligne suivante
            while ((ligne = br.readLine()) != null) {
                int i=0;
                for(char no:ligne.toCharArray()){
                    jeu[noDeLigne][i] = Character.getNumericValue(no);
                    i++;
                }
                noDeLigne++;
            }
            this.creerJeu(jeu);
        }
        catch (Exception e){
            //System.out.println(e.getMessage());
        }
    }


    public void voirJeu()
    {

        for(int i = 0; i < 9; i++) {
            System.out.println("ligne"+i);
            for(int j = 0; j < 9; j++) {
                System.out.println(this.jeu[i][j]);
            }
        }
    }



    public static void main(String[] args) {
        Sudoku sudoku=new Sudoku();
        //Entrer le fichier dans la console
        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez entrer le path contenant le fichier de jeu :");
        String path = scanner.nextLine();
        File file = new File(path);
        //On appelle la methode lireFichier qui sert a remplir le array de jeu
        sudoku.lireFichier(file);
        //On appelle la methode pour resoudre le sudoku
        long startTime = System.currentTimeMillis();
        boolean resolution = sudoku.jouer();
        long stopTime = System.currentTimeMillis();
        long time = stopTime-startTime;
        if(resolution == true){
            System.out.println("Le sudoku est resolu en " + time + " ms");
        }
        else {
            System.out.println("Le sudoku na pas de solution");
        }
    }
}
