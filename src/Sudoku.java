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
    private Carre[] listeCarre = new Carre[9];
    private Integer[] numerosTab = {1,2,3,4,5,6,7,8,9};
    private boolean resolu = false;



    //==============================================================================================================
    //------------------------------------------------------JEU-----------------------------------------------------
    //==============================================================================================================

    //Fonction de recusiviter pour resoudre le soduku
    public boolean jouer() {
        int indexNumTab =0;
        ArrayList<Integer> usedIndex = new ArrayList<Integer>();
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
                            int indexCarre = getCarre(i,j);
                            listeCarre[indexCarre].chiffre.add(numerosTab[indexNumTab]);
                            usedIndex.add(indexNumTab);
                            valide = jouer();
                            //Si on trouve pas de solutions avec la valeur mise en place, on remet la valeur a zero et on reesaye avec d"autre
                            if (valide == false){
                                jeu[i][j] = 0;
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

    //Verifier si la valeur se trouve dans un carre de jeu
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
        for (int i=0; i<9; i++){
            if (jeu[ligne][i] == num){
                libre = false;
            }
        }
        for (int i=0; i<9; i++){
            if (jeu[i][colonne] == num){
                libre = false;
            }
        }
        if (listeCarre[carre].chiffre.contains(num)){
            libre = false;
        }
        return libre;
    }

    //Ajout Marc...Remplie les listes avec des objets vides
    public void creerObjets()
    {
        for (int i=0; i<9;i++)
        {
            this.listeCarre[i]=new Carre();
        }

    }

    //Cette fonction permet de lire le fichier avant la creation du jeu
    public void lireFichier(File f)
    {
        String ligne;
        int noDeLigne = 0;
        int indexCarre=0;
        //Lire le fichier
        try  {
            BufferedReader br = new BufferedReader(new FileReader(f));
            //Pour chaque ligne on lit la ligne et remplit chaque colonne avec les caracteres, on augmente ensuite noDeLigne pour remplir la ligne suivante
            while ((ligne = br.readLine()) != null) {
                int i=0;

                for(char no:ligne.toCharArray()){
                    jeu[noDeLigne][i] = Character.getNumericValue(no);

                    //Ajout marc...ajout des chiffres dans les ligne
                    if(noDeLigne>=3 && noDeLigne<6)
                    {
                        indexCarre=3;
                    }
                    else if (noDeLigne>=6 && noDeLigne<9)
                    {
                        indexCarre=6;
                    }

                    //Ajout marc
                    if(i<3)
                    {
                        this.listeCarre[indexCarre].chiffre.add(Character.getNumericValue(no));
                    }
                    else if(i<6)
                    {
                        this.listeCarre[indexCarre+1].chiffre.add(Character.getNumericValue(no));
                    }
                    else
                    {
                        this.listeCarre[indexCarre+2].chiffre.add(Character.getNumericValue(no));
                    }
                    i++;
                }
                noDeLigne++;
            }


        }
        catch (Exception e){
            //System.out.println(e.getMessage());
        }
    }


    //Permet d'afficher le jeu a la fin du jeu
    public void voirJeu()
    {
        for(int i = 0; i < 9; i++) {
            StringBuilder ligne=new StringBuilder();
            for(int j = 0; j < 9; j++) {
                ligne.append(this.jeu[i][j]);
            }
            System.out.println(ligne);
        }
    }


    public static void main(String[] args) {
        Sudoku sudoku=new Sudoku();
        sudoku.creerObjets();
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
            System.out.println("Le sudoku est resolu en " + (time/1000.0) % 100 + "s");
            sudoku.voirJeu();
        }
        else {
            System.out.println("Le sudoku na pas de solution");
            System.out.println("Le sudoku est resolu en " + (time/1000.0) % 100 + "s");
        }
    }
}
