package hospital; 

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class HandlerLogin implements Initializable
{
    // JDBC 驱动名及数据库 URL
    static final String DB_URL = "jdbc:mysql://localhost:3306/hospital_data?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    static final String USER = "root";
    static final String PASS = "ydnydn910ZX";
    static Connection conn = null;
    static Statement stmt = null;
    
    ObservableList<String> ob_pat = FXCollections.observableArrayList();
    ObservableList<String> ob_doc = FXCollections.observableArrayList();
    Vector<String> pat_list,doc_list;
    
    @FXML
    private Button btn_exit,btn_login;
    @FXML
    private AnchorPane anchorpane_down;
    @FXML
    public ComboBox<String> combo_account,combo_type;
    @FXML
    private TextField text_pass;

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
    	pat_list = new Vector<>();
    	doc_list = new Vector<>();
    	combo_type.getItems().addAll("患者","医生");
    	combo_type.getSelectionModel().select(0);
		try
		{
  	         conn = DriverManager.getConnection(DB_URL,USER,PASS);
  	         stmt = conn.createStatement();
   	         String sql = "select pat_num,pat_name from patient_info";
   	         ResultSet rs = stmt.executeQuery(sql);
   	         String pat_num,pat_name;
   	         while(rs.next())
   	         {
   	        	 pat_num = rs.getString("pat_num");
   	             pat_name  = rs.getString("pat_name");
   	             ob_pat.add(pat_name);
   	             pat_list.add(pat_num);
   	         }
   	         sql = "select doc_num,doc_name from doctor_info";
   	         rs = stmt.executeQuery(sql);
   	         String doc_name,doc_num;
   	         while(rs.next())
   	         {
   	        	 doc_num = rs.getString("doc_num");
   	        	 doc_name = rs.getString("doc_name");
   	        	 ob_doc.add(doc_name);
   	        	 doc_list.add(doc_num);
   	         }
   	         rs.close();
   	         stmt.close();
   	         conn.close();
		}
    	catch(SQLException se){
	         	// 处理 JDBC 错误
	         	se.printStackTrace();
	    }
		combo_account.setItems(ob_pat);
		combo_account.getSelectionModel().select(0);
		combo_type.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)-> 
		{
        	int sel = combo_type.getSelectionModel().getSelectedIndex();
        	if(sel==0)
        	{
        		combo_account.setItems(ob_pat);
        	}
        	else if(sel==1)
        	{
        		combo_account.setItems(ob_doc);
        	}
        	combo_account.getSelectionModel().select(0);
	    });
		/*combo_type.getEditor().textProperty().addListener(new ChangeListener<String>()
    	{
 		    @Override
 		    public void changed(ObservableValue<? extends String> observable, 
 		                                    String oldValue, String newValue) 
 		    {
 		    	System.out.println(combo_type);
 		    	combo_type.show();
 		    }
 		});*/
    }

    @FXML
    private void on_btn_exit_clicked(ActionEvent event) throws SQLException
    {
    	Event.fireEvent(MainApp.getPrimaryStage(),
    			new WindowEvent(MainApp.getPrimaryStage(), WindowEvent.WINDOW_CLOSE_REQUEST ));
    }
    
    @FXML
    private void on_btn_login_clicked(ActionEvent event)
    {
    	if(combo_account.getValue() != null &&
            false == combo_account.getValue().toString().isEmpty())
    	{
        	int type = combo_type.getSelectionModel().getSelectedIndex();
    		String pass = text_pass.getText();
    		int sel_index = combo_account.getSelectionModel().getSelectedIndex();
    		String pat_doc_num = type==0?pat_list.elementAt(sel_index):doc_list.elementAt(sel_index);
    		if(pass.isEmpty())
    		{
    			JOptionPane.showMessageDialog(new JFrame().getContentPane(), 
              			"请输入密码！", "警告", JOptionPane.WARNING_MESSAGE);
    			return;
    		}
    		try
    		{
      	         conn = DriverManager.getConnection(DB_URL,USER,PASS);
      	         stmt = conn.createStatement();
       	         String sql = null;
       	         ResultSet rs = null;
       	         if(type==0)
       	         {
       	        	sql = "select login_cmd from patient_info "
           	         		+ "where pat_num = '"+pat_doc_num+"'";
       	         }
       	         else if(type==1)
       	         {
       	        	sql = "select login_cmd from doctor_info "
           	         		+ "where doc_num = '"+pat_doc_num+"'";
       	         }
       	         else return;
       	         rs = stmt.executeQuery(sql);
       	         if(rs.next())
       	         {
       	             String login_cmd  = rs.getString("login_cmd");
       	             if(login_cmd.equals(pass))
       	             {
       	            	MainApp.pat_doc_num = pat_doc_num;
       	            	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       	            	 if(type==0)
       	            	 {
       	            		sql = "update patient_info set last_login = "
         							+ "'"+df.format(new Date())+"' where pat_num= '"+pat_doc_num+"'";
       	            	 }
       	            	 else if(type==1)
       	            	 {
       	            		sql = "update doctor_info set last_login = "
         							+ "'"+df.format(new Date())+"' where doc_num= '"+pat_doc_num+"'";
       	            	 }
       	            	 else return;
       	            	stmt.executeUpdate(sql);
     					text_pass.clear();
     					System.out.println("登录成功。");
     					if(type==0)
     					{
     						MainApp.setRegUi();
     					}
     					else if(type==1)
     					{
     						MainApp.setDocUi();
     					}
       	             }
     				 else
     				 {
     					JOptionPane.showMessageDialog(new JFrame().getContentPane(), 
     	              			"密码错误！", "警告", JOptionPane.WARNING_MESSAGE);
     				 }
       	         }
       	         rs.close();
       	         stmt.close();
       	         conn.close();
    		}
        	catch(SQLException se){
   	         	// 处理 JDBC 错误
   	         	se.printStackTrace();
   	     	}
    	}
    }
}
