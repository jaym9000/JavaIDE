import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.scene.text.Font;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import java.io.*;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;


public class JavaIDE_UI_L7 extends Application
{
   ChoiceBox<String> filesChoice;
   String[] listOfFiles = {};
   TextArea codeArea, compileRunArea;
   Project prj = new Project("none");
   Stage mainStage;
   JavaFile displayedFile = null;
   boolean empty = true;
   MenuItem createPrjMenu;
   MenuItem addFileMenu;
   MenuItem newFileMenu;
   MenuItem saveFileMenu;
   MenuItem removeFileMenu;
   MenuItem compileMenu;
   MenuItem runMenu;
     
   public void start(Stage primaryStage)
   {
      mainStage = primaryStage;

      MenuBar menuBar = new MenuBar();
      
      Menu projectTopMenu = new Menu("Project");      
      createPrjMenu = new MenuItem("Create project");
      //createPrjMenu.setOnAction(this::processCreatePrjMenu);
		createPrjMenu.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
               processCreatePrjMenu(e);
               addFileMenu.setDisable(false);
               newFileMenu.setDisable(false);
               runMenu.setDisable(false);
          saveFileMenu.setDisable(false);
           removeFileMenu.setDisable(false); 
            compileMenu.setDisable(false);
            }
        });
      addFileMenu = new MenuItem("Add file");
      addFileMenu.setDisable(true);
      //addFileMenu.setOnAction(this::processAddFileMenu);
		addFileMenu.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
               processAddFileMenu(e);
                
            }
        });
        
      saveFileMenu = new MenuItem("Save file");
      saveFileMenu.setDisable(true);
      //saveFileMenu.setOnAction(this::processSaveFileMenu);
		saveFileMenu.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
               processSaveFileMenu(e);
               
            }
        });
      removeFileMenu = new MenuItem("Remove file");
      removeFileMenu.setDisable(true);
      //removeFileMenu.setOnAction(this::processRemoveFileMenu);
		removeFileMenu.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
               processRemoveFileMenu(e);
               
            }
        });
        
        newFileMenu = new MenuItem("Add new file");
      newFileMenu.setDisable(true);
      //addFileMenu.setOnAction(this::processAddFileMenu);
		newFileMenu.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
               processNewFileMenu(e);
                
            }
        });
        
      projectTopMenu.getItems().addAll(createPrjMenu, 
                     addFileMenu, saveFileMenu, removeFileMenu, newFileMenu);
      
      Menu buildTopMenu = new Menu("Build");     
      
      compileMenu = new MenuItem("Compile all");
      compileMenu.setDisable(true); 
      //compileMenu.setOnAction(this::processCompileMenu);
		compileMenu.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
               processCompileMenu(e);
              
            }
        });
      runMenu = new MenuItem("Run");
      runMenu.setDisable(true);
      //runMenu.setOnAction(this::processRunMenu);
		runMenu.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
               processRunMenu(e);
               
            }
        });
      buildTopMenu.getItems().addAll(compileMenu, runMenu);
      
      
      menuBar.getMenus().addAll(projectTopMenu, buildTopMenu);
      
        addFileMenu.setDisable(true);
        newFileMenu.setDisable(true);
        removeFileMenu.setDisable(true);
        compileMenu.setDisable(true);
        runMenu.setDisable(true);
      
      filesChoice = new ChoiceBox<String>();
      filesChoice.getItems().addAll(listOfFiles);
      filesChoice.getSelectionModel().select(0);
      //filesChoice.setOnAction(this::processFileSelection);
		filesChoice.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
               processFileSelection(e);
            }
        });
      
      codeArea = new TextArea();
      codeArea.setPrefRowCount(20);
      codeArea.setWrapText(true);

      compileRunArea = new TextArea();
      compileRunArea.setPrefRowCount(10);
      compileRunArea.setWrapText(true);

      VBox pane = new VBox(menuBar, filesChoice, 
                        codeArea, compileRunArea);
      pane.setSpacing(10);
      Scene theScene = new Scene(pane, 1000, 600);
      primaryStage.setTitle("Simple Java IDE");
      primaryStage.setScene(theScene);
      primaryStage.show();
   }
   
   public void processCreatePrjMenu(ActionEvent event)
   {
      TextInputDialog dialog = new TextInputDialog();
      dialog.setTitle("Project creation");
      dialog.setHeaderText("Enter the name of the project:");
      Optional<String> result = dialog.showAndWait();
      if (result.isPresent())
      {
         String name = dialog.getEditor().getText();
         prj = new Project(name);
      }
    

   }
 
   public void processAddFileMenu(ActionEvent event)
   {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Add File");
      fileChooser.setInitialDirectory(new File(System.getProperty("user.dir"))); 
      File selectedFile = fileChooser.showOpenDialog(mainStage);
      
      if (selectedFile != null) 
      {
         String name = selectedFile.getName();
        String path = selectedFile.getAbsolutePath();
       
         // remove the .java extension

         JavaFile file = new JavaFile(path);
         name = file.getName();
         prj.addFile(file);
         String[] temp = new String[listOfFiles.length + 1];
         for (int i=0; i<listOfFiles.length; i++)
            temp[i] = listOfFiles[i];
         temp[listOfFiles.length] = name;
         listOfFiles = temp;
         //filesChoice.getItems().setAll(listOfFiles);
         filesChoice.getItems().add(name);
               
      }
   }
   
   public void processNewFileMenu(ActionEvent event)
   {
      try
      {
         TextInputDialog dialog = new TextInputDialog();
         dialog.setTitle("New file creation");
         dialog.setHeaderText("Enter the name of the new file:");
         Optional<String> result = dialog.showAndWait();
         if (result.isPresent())
         {
            String name = dialog.getEditor().getText();
            File newFile = new File("C:\\Users\\Nathan\\Desktop\\Java 2\\Labs\\group\\lab 10"+ name + ".java");
           // newFile.createNewFile();
           JavaFile file = new JavaFile(name);
           file.save();
            
            if (newFile.createNewFile())
            {
               
               compileRunArea.setText("Creation complete.");
               
               prj.addFile(file);
               filesChoice.getItems().add(name);
            }
            else
               compileRunArea.setText("File already exists.");
         }
      }   
         catch (IOException e)
         {
            Alert alert = new Alert(AlertType.INFORMATION,
                           "Problem loading", ButtonType.OK);
                           alert.showAndWait();
         }
      
   }

   public void processSaveFileMenu(ActionEvent event)
   {
      displayedFile.setContent(codeArea.getText());
      try
      {
         displayedFile.save();
      }
      catch (IOException e)
      {
         Alert alert = new Alert(AlertType.INFORMATION, 
                     "Problem saving" , ButtonType.OK);
         alert.showAndWait();
      }
   }

   public void processRemoveFileMenu(ActionEvent event)
   {
      Alert alert = new Alert(AlertType.INFORMATION, 
                     "Remove file" , ButtonType.OK);
      alert.showAndWait();
   }

   public void processCompileMenu(ActionEvent event)
   {
      try
      {
         String result = prj.compile();
         compileRunArea.setText(result);
      }
      catch (Exception e)
      {
         Alert alert = new Alert(AlertType.INFORMATION, 
                     "Problem loading" , ButtonType.OK);
         alert.showAndWait();
      }
   }

   public void processRunMenu(ActionEvent event)
   {
      try
      {
        
         String result = prj.run();
         compileRunArea.setText(result);
      }
      catch (Exception e)
      {
         Alert alert = new Alert(AlertType.INFORMATION, 
                     "Problem loading" , ButtonType.OK);
         alert.showAndWait();
      }
   }
   
   
   public void processFileSelection(ActionEvent event)
   {
         int index = filesChoice.getSelectionModel().getSelectedIndex();
       JavaFile file = prj.getJavaFile(index);
         
    if (displayedFile == null || (codeArea.getText()).equals(displayedFile.getContent()))
    {
      try   
      {

         file.load();
      }
      catch (FileNotFoundException e)
      {
         Alert alert = new Alert(AlertType.INFORMATION, 
                     "Problem loading" , ButtonType.OK);
         alert.showAndWait();
      }
      codeArea.setText(file.getContent());
      displayedFile = file;
      }
      else 
      {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
         "Save the unsaved file before moving on?",
         ButtonType.YES, ButtonType.NO);
         alert.showAndWait();
         
         if (alert.getResult()== ButtonType.YES)
         {
            this.processSaveFileMenu(event);
            try
            {
               file.load();
            }
            catch (IOException e)
            {
               Alert alert1 = new Alert(AlertType.INFORMATION, "Problem saving", ButtonType.OK);
               alert1.showAndWait();
            }   
           codeArea.setText(file.getContent());
           displayedFile = file;
 
         }
         else if(alert.getResult() == ButtonType.NO)
         {
            try
            {
               file.load();
               }
               catch(IOException e)
               {
               Alert alert1 = new Alert(AlertType.INFORMATION, "Problem saving", ButtonType.OK);
               alert1.showAndWait();
               }
                codeArea.setText(file.getContent());
           displayedFile = file;

         }      
      }
   }
   

   public static void main(String[] args)
   {
      launch(args);
   }
}

