import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.Scanner;

/**
 * Created by Catherine on 9/30/2015.
 */
public class Sudoku {

    private Integer [][]jeu=new Integer [9][9];

    public void creerJeu(Integer [][] jeu)
    {

    }

    public void lireFichier(File f)
    {
        String ligne;
        int noDeLigne = 0;
        //Lire le fichier
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
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
            System.out.println(e.getMessage());
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

    }
}
