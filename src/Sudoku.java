import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.List;
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
    public boolean jouer(int ligne, int colonne) {
        for (int num = 1; num <= 9; num++) {
            if (estLibre(num, ligne, colonne)) {
                jeu[ligne][colonne] = num;
                if (jouer(ligne+1,colonne+1)) {
                    return true;
                }
                jeu[ligne][colonne] = null;
            }
        }
        return false;
    }

    public boolean jouerV2() {
        int indexNumTab =0;
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
                        if (estLibre2(i, j, getCarre(i, j), numerosTab[indexNumTab])) {
                            jeu[i][j] = numerosTab[indexNumTab];
                            System.out.println("Ligne " + i + " Colonne " + j + " Num " + numerosTab[indexNumTab]);
                            valide = jouerV2();
                            //Si on trouve pas de solutions avec la valeur mise en place, on remet la valeur a zero et on reesaye avec d"autre
                            if (valide == false){
                                jeu[i][j] = 0;
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            indexNumTab ++;
                        }
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
    public boolean estLibre2(int ligne, int colonne, int carre,int num) {
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
    //Verifie si la case es libre
    public boolean estLibre(int ligne, int colonne, int carre) {
        boolean ligneLibre = true;
        boolean colonneLibre = true;
        boolean carreLibre = true;

        switch (colonne) {
            case 1:
                if (listeColonne[1].chiffre.contains(colonne)) {
                    colonneLibre = false;
                }
                break;
            case 2:
                if (listeColonne[2].chiffre.contains(colonne)) {
                    colonneLibre = false;
                }

                break;
            case 3:
                if (listeColonne[3].chiffre.contains(colonne)) {
                    colonneLibre = false;
                }
                break;
            case 4:
                if (listeColonne[4].chiffre.contains(colonne)) {
                    colonneLibre = false;
                }
                break;
            case 5:
                if (listeColonne[5].chiffre.contains(colonne)) {
                    colonneLibre = false;
                }
                break;
            case 6:
                if (listeColonne[6].chiffre.contains(colonne)) {
                    colonneLibre = false;
                }
                break;
            case 7:
                if (listeColonne[7].chiffre.contains(colonne)) {
                    colonneLibre = false;
                }
                break;
            case 8:
                if (listeColonne[8].chiffre.contains(colonne)) {
                    colonneLibre = false;
                }
                break;
            case 9:
                if (listeColonne[9].chiffre.contains(colonne)) {
                    colonneLibre = false;
                }
                break;
        }
        switch (ligne) {
            case 1:
                if (listeLigne[1].chiffre.contains(ligne)) {
                    ligneLibre = false;
                }
                break;
            case 2:
                if (listeLigne[2].chiffre.contains(ligne)) {
                    ligneLibre = false;
                }

                break;
            case 3:
                if (listeLigne[3].chiffre.contains(ligne)) {
                    ligneLibre = false;
                }
                break;
            case 4:
                if (listeLigne[4].chiffre.contains(ligne)) {
                    ligneLibre = false;
                }
                break;
            case 5:
                if (listeLigne[5].chiffre.contains(ligne)) {
                    ligneLibre = false;
                }
                break;
            case 6:
                if (listeLigne[6].chiffre.contains(ligne)) {
                    ligneLibre = false;
                }
                break;
            case 7:
                if (listeLigne[7].chiffre.contains(ligne)) {
                    ligneLibre = false;
                }
                break;
            case 8:
                if (listeLigne[8].chiffre.contains(ligne)) {
                    ligneLibre = false;
                }
                break;
            case 9:
                if (listeLigne[9].chiffre.contains(ligne)) {
                    ligneLibre = false;
                }
                break;
        }
        switch (carre) {
            case 1:
                if (listeCarre[1].chiffre.contains(carre)) {
                    carreLibre = false;
                }
                break;
            case 2:
                if (listeCarre[2].chiffre.contains(carre)) {
                    carreLibre = false;
                }

                break;
            case 3:
                if (listeCarre[3].chiffre.contains(carre)) {
                    carreLibre = false;
                }
                break;
            case 4:
                if (listeCarre[4].chiffre.contains(carre)) {
                    carreLibre = false;
                }
                break;
            case 5:
                if (listeCarre[5].chiffre.contains(carre)) {
                    carreLibre = false;
                }
                break;
            case 6:
                if (listeCarre[6].chiffre.contains(carre)) {
                    carreLibre = false;
                }
                break;
            case 7:
                if (listeCarre[7].chiffre.contains(carre)) {
                    carreLibre = false;
                }
                break;
            case 8:
                if (listeCarre[8].chiffre.contains(carre)) {
                    carreLibre = false;
                }
                break;
            case 9:
                if (listeCarre[9].chiffre.contains(carre)) {
                    carreLibre = false;
                }
                break;
        }


        if (ligneLibre && colonneLibre && carreLibre)
        {
            return true;
        }
        else
        {
            return false;
        }

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
        boolean resolution = sudoku.jouerV2();
        if(resolution == true){
            System.out.println("Le sudoku est resolu");
        }
        else {
            System.out.println("Le sudoku na pas de solution");
        }
    }
}
