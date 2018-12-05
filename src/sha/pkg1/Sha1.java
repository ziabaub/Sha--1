/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sha.pkg1;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JFileChooser;

/**
 *
 * @author ziad
 */
public class Sha1 extends Application {


    File FSource;
    String FileLocation,FileContents,Hashtogo;
    SHA sha1cipher ;
    TextField Sha;
    @Override
    public void start(Stage primaryStage) {
        Button BtnBrowser ;

           BtnBrowser = new Button("Browse"); 
           BtnBrowser.setOnAction((ActionEvent event) -> {
               JFileChooser chooser = new JFileChooser();
               chooser.showOpenDialog(null);
               FSource = chooser.getSelectedFile();
               String s = FSource.getName();
               FileLocation = new String();
               FileLocation = FSource.getAbsolutePath();
            try {
                dohashing();
            } catch (IOException ex) {
                Logger.getLogger(Sha1.class.getName()).log(Level.SEVERE, null, ex);
            }
           });
        
        Sha = new TextField();
        Sha.setPrefWidth(280);
        VBox MainHB = new VBox(10);
        MainHB.setLayoutX(10);
         MainHB.setLayoutY(10);
        MainHB.getChildren().addAll(BtnBrowser,Sha);
        Pane root = new Pane();
        root.getChildren().add(MainHB);
        
        Scene scene = new Scene(root, 300, 100);
        
        primaryStage.setTitle("SHA-1");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
    protected  StringBuilder GetMeString2(File file) throws FileNotFoundException, IOException {

     FileInputStream fis = new FileInputStream(file);
     BufferedInputStream bis = new BufferedInputStream(fis);
     int k = 0,i;
     StringBuilder Text = new StringBuilder();
     while((i = bis.read())!=-1){
         Text.insert(k, (char)i);
         k++;         
     }
     return Text;
    }
    private  void dohashing() throws IOException {
     FileContents = GetMeString2(FSource).toString();
     sha1cipher = new SHA();
     String hash ;
       FileContents = FileContents.replace("\n", "");
      Sha.setText(sha1cipher.encodeHex(FileContents));
       // System.out.println(hash);
   }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {
        launch(args);
        
    }
    
}
