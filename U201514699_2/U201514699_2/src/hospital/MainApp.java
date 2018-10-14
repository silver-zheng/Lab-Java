package hospital;

import java.io.IOException;
import java.sql.*;
import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class MainApp extends Application 
{
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    
    static Stage primarystage = null;
    private SplitPane root_reg = null;
    private SplitPane root_login = null;
    private SplitPane root_doc = null;
    private static Scene scene_reg = null;
    private static Scene scene_login = null;
    private static Scene scene_doc = null;
    static String pat_doc_num;
    @Override
    public void start(Stage primaryStage) throws ClassNotFoundException, SQLException 
    {
        try 
        {
        	/*Date date = new Date();
        	Timestamp timestamp = new Timestamp(date.getTime()); //2013-01-14 22:45:36.484
        	System.out.println(timestamp);*/
            primaryStage.setTitle("华中科技大学医院挂号系统");
        	primarystage = primaryStage;
        	root_reg = FXMLLoader.load(getClass().getResource("view/register.fxml"));
        	root_login = FXMLLoader.load(getClass().getResource("view/login.fxml"));
        	root_doc = FXMLLoader.load(getClass().getResource("view/doctor.fxml"));
            scene_reg = new Scene(root_reg);
            scene_login = new Scene(root_login);
            scene_doc = new Scene(root_doc);
            setLoginUi();
            primarystage.show();
	         // 注册 JDBC 驱动
	        Class.forName(JDBC_DRIVER);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        catch(Exception e){
	         // 处理 Class.forName 错误
	         e.printStackTrace();
	     }
    }
    public void setPrimaryStage(Stage stage)
    {
    	primarystage = stage;
    }
    public static void setLoginUi()
    {
        primarystage.setScene(scene_login);
    }
    public static void setRegUi()
    {
    	primarystage.setScene(scene_reg);
    }
    public static void setDocUi()
    {
    	primarystage.setScene(scene_doc);
    }
    public static void main(String[] args) {
        launch(args);
    }
	public static Stage getPrimaryStage() {
		return primarystage;
	}
}