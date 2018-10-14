package hospital; 

import hospital.module.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class HandlerReg implements Initializable
{
    // JDBC 驱动名及数据库 URL
    static final String DB_URL = "jdbc:mysql://localhost:3306/hospital_data?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    static final String USER = "root";
    static final String PASS = "ydnydn910ZX";
    static Connection conn = null;
    static Statement stmt = null;
    private boolean flag_of_changedbysel = false;
    private boolean flag_of_changedbyreg = false;
    private boolean flag_of_changedbyunreg = false;
    boolean flag_off_cut = false;
    String sel_off_num,sel_doc_num,sel_reg_num;

    ObservableList<String> ob_office = FXCollections.observableArrayList();
    ObservableList<String> cut_ob_office = FXCollections.observableArrayList();
    ObservableList<String> ob_doc = FXCollections.observableArrayList();
    ObservableList<String> ob_regname = FXCollections.observableArrayList();
    ObservableList<PatientReg> ob_patreg = FXCollections.observableArrayList();
    ObservableList<PatientReg> ob_unreg = FXCollections.observableArrayList();
    Vector<String> office_list,cut_of_list,doc_list,reg_list;
    
    @FXML
    private Button btn_ok,btn_clear,btn_exit;
    @FXML
    private ListView<String> list_office,list_doc,list_regtype,list_regname;
    @FXML
    private ContextMenu context_office,context_doc,context_regtype,context_regname;
    @FXML
    private TextField text_office,text_doc,text_regtype,text_regname,text_cost,
    	text_pay,text_charge,text_regnum;
    @FXML
    private TableView<PatientReg> table_patreg;
    @FXML
    private TableColumn<?, ?>col_regnum,col_regname,col_docnum,col_docname,
    col_regcount,col_regcost,col_unreg,col_regtime;
    @FXML
    private Tab tab_reg,tab_unreg;

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
    	col_regnum.setCellValueFactory(new PropertyValueFactory<>("regNum"));  
    	col_regname.setCellValueFactory(new PropertyValueFactory<>("regName"));  
    	col_docnum.setCellValueFactory(new PropertyValueFactory<>("docNum")); 
    	col_docname.setCellValueFactory(new PropertyValueFactory<>("docName"));
    	
    	col_regcount.setCellValueFactory(new PropertyValueFactory<>("regCount"));  
    	col_regcost.setCellValueFactory(new PropertyValueFactory<>("regCost"));  
    	col_unreg.setCellValueFactory(new PropertyValueFactory<>("unReg")); 
    	col_regtime.setCellValueFactory(new PropertyValueFactory<>("regTime"));
    	table_patreg.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	
	   	 try
	   	 {
	   		office_list = new Vector<>();
	   		cut_of_list = new Vector<>();
	   		doc_list = new Vector<>();
	   		reg_list = new Vector<>();
	         conn = DriverManager.getConnection(DB_URL,USER,PASS);
	         stmt = conn.createStatement();
	         String sql = "select office_num,office_name from office_info";
	         ResultSet rs = stmt.executeQuery(sql);
	         String office_name,office_num;
	         while(rs.next())
	         {
	        	 office_num = rs.getString("office_num");
	             office_name = rs.getString("office_name");
	             ob_office.add(office_name);
	             office_list.add(office_num);
	         }
	         rs.close();
	         stmt.close();
	         conn.close();
	     }catch(SQLException se){
	         // 处理 JDBC 错误
	         se.printStackTrace();
	     }
	     
	   	 list_office.setItems(ob_office);
    	list_doc.getSelectionModel().selectedItemProperty().addListener
    	(
    	    (ObservableValue<? extends String> observable, 
    	    		String oldValue, String newValue) ->
    	    {
    	    	int index = list_doc.getSelectionModel().getSelectedIndex();
    	    	if(index>-1)
    	    	{
    	    		sel_doc_num = doc_list.elementAt(index);
    	    	}
    	    	
			    text_doc.setText(newValue);
			    context_doc.hide();
    	    }
    	);
    	list_regtype.getSelectionModel().selectedItemProperty().addListener
    	(
    	    (ObservableValue<? extends String> observable, 
    	    		String oldValue, String newValue) ->
    	    {
			    text_regtype.setText(newValue);
			    context_regtype.hide();
    	    }
    	);
    	list_regname.getSelectionModel().selectedItemProperty().addListener
    	(
    	    (ObservableValue<? extends String> observable, 
    	    		String oldValue, String newValue) ->
    	    {
    	    	int index = list_regname.getSelectionModel().getSelectedIndex();
    	    	if(index>-1)
    	    	{
    	    		sel_reg_num = reg_list.elementAt(index);
    	    	}
			    text_regname.setText(newValue);
			    context_regname.hide();
    	    }
    	);
    	text_office.textProperty().addListener(new ChangeListener<String>()
    		{
    			@Override
    			public void changed(ObservableValue<? extends String> observable, 
 		                              String oldValue, String newValue)
    			{
    				if(flag_of_changedbyreg==true)
    				{
    					flag_of_changedbyreg = false;
    					return;
    				}
    				if(flag_of_changedbysel==false)
    				{
        		    	context_office.show(MainApp.primarystage,
        		    			MainApp.primarystage.getX()+128.0+10,
        		    			MainApp.primarystage.getY()+33.0+130);
    				}
    				else
    					flag_of_changedbysel = false;
    			    Pattern pattern = Pattern.compile("[0-9a-zA-Z]*");
    			    if(newValue==null||newValue.isEmpty())
    			    {
    			    	list_office.setItems(ob_office);
    			    	flag_off_cut = false;
    			    }
    			    else if(pattern.matcher(newValue).matches())//非汉字
    			    {
    		    	   	 try
    		    	   	 {
    		    	         conn = DriverManager.getConnection(DB_URL,USER,PASS);
    		    	         stmt = conn.createStatement();
    		    	         String sql = "select office_num,office_name from office_info "
    		    	         		+ "where office_pinyin like '"+newValue+"%'";
    		    	         ResultSet rs = stmt.executeQuery(sql);
    		    	         cut_ob_office.clear();
    		    	         cut_of_list.clear();
    		    	         String office_name,office_num;
    		    	         while(rs.next())
    		    	         {
    		    	        	 office_num = rs.getString("office_num");
    		    	             office_name = rs.getString("office_name");
    		    	             cut_ob_office.add(office_name);
    		    	             cut_of_list.add(office_num);
    		    	         }
    		    	         list_office.setItems(cut_ob_office);
    		    	         flag_off_cut = true;
    		    	         rs.close();
    		    	         stmt.close();
    		    	         conn.close();
    		    	     }catch(SQLException se){
    		    	         // 处理 JDBC 错误
    		    	         se.printStackTrace();
    		    	     }
    			    }
    			}
    		}
    	);
    }
    
    @FXML
    private void tabunreg_sel_changed(Event event)
    {
    	boolean flag_unreg=false,expert=false;
    	String regnum,regname,docnum,docname,regcount,regcost,unreg,regtime;
		try
		{
 	         conn = DriverManager.getConnection(DB_URL,USER,PASS);
 	         stmt = conn.createStatement();
  	        String sql;
  	        ResultSet rs=null;
     		sql = "select count(*) as regcount from register_info " + 
 	    			"where pat_num='"+MainApp.pat_doc_num+"'";
     		rs = stmt.executeQuery(sql);
     		if(rs.next())
     		{
     			int count = rs.getInt("regcount");
     			int col_count = ob_patreg.size();
     			/*System.out.println("count="+count);
     			System.out.println("colcount="+col_count);*/
     			if(count!=col_count || flag_of_changedbyunreg)
     			{
     				ob_patreg.clear();
     				sql = "select reg1.register_num,reg2.reg_name,reg2.expert,reg1.doc_num,"
     	 	    			+ "doc.doc_name,reg1.pat_count,reg1.unreg,reg1.reg_cost,"
     	 	    			+ "reg1.reg_datetime from register_info reg1,doctor_info doc,"
     	 	    			+ "registration_info reg2 where reg1.pat_num='"+MainApp.pat_doc_num+"' "
     	 	    			+ "and doc.doc_num=reg1.doc_num and reg2.reg_num=reg1.registration_num";
     	   	         rs = stmt.executeQuery(sql);
     	   	         while(rs.next())
     	   	         {
     	   	        	 regnum = rs.getString("reg1.register_num");
     	   	             regname = rs.getString("reg2.reg_name");
     	   	             expert = rs.getBoolean("reg2.expert");
     	   	             regname = regname+"（"+(expert?"专家":"普通")+"号）";
     	   	             docnum = rs.getString("reg1.doc_num");
     	   	             docname = rs.getString("doc.doc_name");
     	   	             regcount = rs.getString("reg1.pat_count");
     	   	             regcost = rs.getString("reg1.reg_cost");
     	   	             flag_unreg = rs.getBoolean("reg1.unreg");
     	   	             unreg = flag_unreg?"是":"否";
     	   	             regtime = rs.getString("reg1.reg_datetime");
     	   	             ob_patreg.add(new PatientReg(regnum,regname,docnum,docname,regcount,
     	   	            		regcost,unreg,regtime));
     	   	         }
     	   	         table_patreg.setItems(ob_patreg);
     	   	         flag_of_changedbyunreg = false;
     			}
     		}
     		rs.close();
  	         
  	         stmt.close();
  	         conn.close();
  	     }catch(SQLException se){
  	         // 处理 JDBC 错误
  	         se.printStackTrace();
  	     }
    }
    
    @FXML
    private void on_clearsel_clicked(ActionEvent event)
    {
    	table_patreg.getSelectionModel().clearSelection();
    }
    
    @FXML
    private void on_btn_unreg_clicked(ActionEvent event)
    {
    	
    	ob_unreg = table_patreg.getSelectionModel().getSelectedItems();
    	PatientReg patreg;
    	
    	for(int i=0;i<ob_unreg.size();++i)
    	{
    		patreg = ob_unreg.get(i);
    		String str_unreg = patreg.getUnReg();
    		boolean flag_unreg = str_unreg.equals("是")?true:false;
    		if(flag_unreg)
    		{
    			JOptionPane.showMessageDialog(new JFrame().getContentPane(), 
              		"不可重复退号", "警告", JOptionPane.WARNING_MESSAGE);
    			continue;
    		}
    		flag_of_changedbyunreg = true;
    		try
    		{
      	         conn = DriverManager.getConnection(DB_URL,USER,PASS);
      	         stmt = conn.createStatement();
      	         String sql = "update register_info set unreg=1 where register_num"
      	         		+ "= '"+patreg.getRegNum()+"'";
      	         stmt.executeUpdate(sql);
      	         
      	         sql = "update register_info set pat_count=pat_count-1 where " + 
      	         		"registration_num in ( select reg3.registration_num from " + 
      	         		"(select registration_num from register_info " + 
      	         		"where register_num='"+patreg.getRegNum()+"') reg3)";
      	         stmt.executeUpdate(sql);
      	         stmt.close();
      	         conn.close();
      	     }catch(SQLException se){
      	         // 处理 JDBC 错误
      	         se.printStackTrace();
      	     }
    	}
    	tabunreg_sel_changed(new Event(null));
    }
    
    @FXML
    private void on_listoffice_clicked(Event event)
    {
    	String newoffice = list_office.getSelectionModel().getSelectedItem();
    	int index = list_office.getSelectionModel().getSelectedIndex();
    	flag_of_changedbysel = true;
    	if(index>-1)
    	{
    		sel_off_num = flag_off_cut ? cut_of_list.elementAt(index) : 
    			office_list.elementAt(index);
    	}
    	text_office.setText(newoffice);
    	context_office.hide();
    }
    @FXML
    private void on_text_office_clicked(Event event)
    {
        text_doc.clear();
        text_regtype.clear();
        text_regname.clear();
        text_cost.clear();
    	text_pay.clear();
    	text_charge.clear();
    	text_regnum.clear();
    	context_office.show(MainApp.primarystage,
    			MainApp.primarystage.getX()+128.0+10,
    			MainApp.primarystage.getY()+33.0+130);
    }
    
    @FXML
    private void on_text_doc_clicked(Event event)
    {
        text_regtype.clear();
        text_regname.clear();
        text_cost.clear();
    	text_pay.clear();
    	text_charge.clear();
    	text_regnum.clear();
    	String office = text_office.getText();
    	if(office==null||office.isEmpty())
    	{
    		context_doc.hide();
    		JOptionPane.showMessageDialog(new JFrame().getContentPane(), 
          			"请先选择科室", "警告", JOptionPane.WARNING_MESSAGE);
    	}
    	else
    	{
    		ob_doc.clear();
    	   	 try
    	   	 {
    	   		 doc_list.clear();
    	         conn = DriverManager.getConnection(DB_URL,USER,PASS);
    	         stmt = conn.createStatement();
    	         String sql = "select doc_num,doc_name from doctor_info where office_num ="
    	         		+ "'"+sel_off_num+"'";
    	         ResultSet rs = stmt.executeQuery(sql);
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
    	     }catch(SQLException se){
    	         // 处理 JDBC 错误
    	         se.printStackTrace();
    	     }
    	   	 list_doc.setItems(ob_doc);
    	     context_doc.show(MainApp.primarystage,
    	    	MainApp.primarystage.getX()+385.0+10,
    	    	MainApp.primarystage.getY()+33.0+130);
    	}
    }
    
    @FXML
    private void on_text_regtype_clicked(Event event)
    {
        text_regname.clear();
        text_cost.clear();
    	text_pay.clear();
    	text_charge.clear();
    	text_regnum.clear();
    	String doctor = text_doc.getText();
    	if(doctor==null||doctor.isEmpty())
    	{
    		context_doc.hide();
    		JOptionPane.showMessageDialog(new JFrame().getContentPane(), 
	          			"请先选择医生", "警告", JOptionPane.WARNING_MESSAGE);
    	}
    	else
    	{
    		list_regtype.getItems().clear();
    		try
    		{
      	         conn = DriverManager.getConnection(DB_URL,USER,PASS);
      	         stmt = conn.createStatement();
      	         String sql = "select expert from doctor_info where "
      	         		+ "doc_num = '"+sel_doc_num+"'";
      	         ResultSet rs = stmt.executeQuery(sql);
      	         if(rs.next())
      	         {
      	        	 boolean expert = rs.getBoolean("expert");
      	             if(expert)
      	             {
      	            	 list_regtype.getItems().add("普通号");
      	            	 list_regtype.getItems().add("专家号");
      	             }
      	             else
      	             {
      	            	 list_regtype.getItems().add("普通号");
      	             }
      	           context_regtype.show(MainApp.primarystage,
      	         	    MainApp.primarystage.getX()+128.0+10,
      	         	   	MainApp.primarystage.getY()+80.0+130);
      	         }
      	         else
      	         {
      	        	JOptionPane.showMessageDialog(new JFrame().getContentPane(), 
      	          			"该医生不可挂号", "警告", JOptionPane.WARNING_MESSAGE);
      	        	 return;
      	         }
      	         rs.close();
      	         stmt.close();
      	         conn.close();
      	     }catch(SQLException se){
      	         // 处理 JDBC 错误
      	         se.printStackTrace();
      	     }
    	}
    }
    
    @FXML
    private void on_text_regname_clicked(Event event)
    {
        text_cost.clear();
    	text_pay.clear();
    	text_charge.clear();
    	text_regnum.clear();
    	String office = text_office.getText();
    	String expertstr = text_regtype.getText();
    	if(office==null||office.isEmpty()
    		||expertstr==null||expertstr.isEmpty())
    	{
    		context_regname.hide();
    		JOptionPane.showMessageDialog(new JFrame().getContentPane(), 
          			"请先选择号种类别", "警告", JOptionPane.WARNING_MESSAGE);
    		return;
    	}
    	int expert = expertstr.equals("专家号") ? 1 :0;
    	ob_regname.clear();
    	reg_list.clear();
		try
		{
	         conn = DriverManager.getConnection(DB_URL,USER,PASS);
	         stmt = conn.createStatement();
	         String sql = "select reg_num,reg_name from registration_info where "
		         		+ "office_num = '"+sel_off_num+"' and "
		         		+ "expert = '"+expert+"'";
	         ResultSet rs = stmt.executeQuery(sql);
	         String reg_name,reg_num;
	         while(rs.next())
	         {
	        	 reg_num = rs.getString("reg_num");
	        	 reg_name = rs.getString("reg_name");
	        	 reg_list.add(reg_num);
	        	 ob_regname.add(reg_name);
	         }
	         rs.close();
	         stmt.close();
	         conn.close();
	         if(ob_regname.isEmpty())
	         {
	        	 JOptionPane.showMessageDialog(new JFrame().getContentPane(), 
 	          			"没有符合条件的号", "警告", JOptionPane.WARNING_MESSAGE);
	         }
	         else
	         {
	        	 list_regname.setItems(ob_regname);
	        	 context_regname.show(MainApp.primarystage,
	        		    	MainApp.primarystage.getX()+385.0+10,
	        		    	MainApp.primarystage.getY()+80.0+130);
	         }
	     }catch(SQLException se){
	         // 处理 JDBC 错误
	         se.printStackTrace();
	     }
    }
    
    @FXML
    private void on_text_cost_clicked(Event event)
    {
    	text_pay.clear();
    	text_charge.clear();
    	text_regnum.clear();
    	String regname = text_regname.getText();
    	if(regname==null||regname.isEmpty())
    	{
    		JOptionPane.showMessageDialog(new JFrame().getContentPane(), 
          			"请先选择号种类别", "警告", JOptionPane.WARNING_MESSAGE);
    	}
    	else
    	{
    		try
    		{
    			double reg_cost;
    	         conn = DriverManager.getConnection(DB_URL,USER,PASS);
    	         stmt = conn.createStatement();
    	         String sql = "select reg_cost from registration_info where "
    	         		+ "reg_num = '"+sel_reg_num+"'";
    	         ResultSet rs = stmt.executeQuery(sql);
    	         if(rs.next())
    	         {
    	        	 reg_cost = rs.getDouble("reg_cost");
    	             text_cost.setText(Double.toString(reg_cost));
    	         }
    	         else
    	         {
    	        	 JOptionPane.showMessageDialog(new JFrame().getContentPane(), 
    	          			"没有符合条件的号", "警告", JOptionPane.WARNING_MESSAGE);
    	        	 return;
    	         }
    	         sql = "select prestore_amount from patient_info where "
     	         		+ "pat_num = '"+MainApp.pat_doc_num+"'";
     	         rs = stmt.executeQuery(sql);
     	         if(rs.next())
     	         {
     	        	 double prestore = rs.getDouble("prestore_amount");
     	        	 if(prestore>=reg_cost)
     	        	 {
     	        		text_pay.setText(Double.toString(prestore));
     	        		text_pay.setEditable(false);
     	        		text_charge.setText(Double.toString(prestore-reg_cost));
     	        	 }
     	        	 else
     	        	 {
     	        		 text_pay.setPromptText("请缴纳至少"+reg_cost+"元");
     	        		 text_pay.setEditable(true);
     	        	 }
     	         }
    	         rs.close();
    	         stmt.close();
    	         conn.close();
    	     }catch(SQLException se){
    	         // 处理 JDBC 错误
    	         se.printStackTrace();
    	     }
    	}
    }
    
    @FXML
    private void on_text_pay_clicked(Event event)
    {
    	text_charge.clear();
    	text_regnum.clear();
    	text_pay.setPromptText("");
    }
    
    @FXML
    private void on_text_charge_clicked(Event event)
    {
    	text_regnum.clear();
    	String pays = text_pay.getText();
    	String costs = text_cost.getText();
    	if(pays==null||pays.isEmpty())
    	{
    		JOptionPane.showMessageDialog(new JFrame().getContentPane(), 
         			"请先输入交款金额！", "警告", JOptionPane.WARNING_MESSAGE);
    	}
    	else
    	{
    		double pay = Double.parseDouble(pays);
    		double cost = Double.parseDouble(costs);
    		if(pay>=cost)
    		{
    			text_charge.setText(Double.toString(pay-cost));
    		}
    		else
    		{
    			JOptionPane.showMessageDialog(new JFrame().getContentPane(), 
             			"交款金额不足！", "警告", JOptionPane.WARNING_MESSAGE);
    		}
    	}
    }
    
    @FXML
    private void on_btn_ok_clicked(ActionEvent event) 
    {
    	int max_pat = 0,regcount=0;
    	String regcost = text_cost.getText();
    	int registernum=0;
    	String charge_str = text_charge.getText();
    	if(charge_str==null||charge_str.isEmpty())
    	{
    		JOptionPane.showMessageDialog(new JFrame().getContentPane(), 
         			"请先填写信息！", "警告", JOptionPane.WARNING_MESSAGE);
    		return;
    	}
    	try
		{
    		String currtime=null;
    		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	         conn = DriverManager.getConnection(DB_URL,USER,PASS);
	         stmt = conn.createStatement();

	         String sql = "select max_patient from registration_info where "
	         		+ "reg_num='"+sel_reg_num+"'";
	         ResultSet rs = stmt.executeQuery(sql);
	         if(rs.next())
	         {
	        	 max_pat = rs.getInt("max_patient");
	         }
	         
	         sql = "select current_date as currtime";
	         rs = stmt.executeQuery(sql);
	         if(rs.next())
	         {
	        	 currtime = rs.getString("currtime");
	        	 currtime += " 00:00:00";
	         }
	         
	         sql = "select count(*) as regcount from register_info "
	         		+ "where registration_num ='"+sel_reg_num+"' and "
	         		+ "reg_datetime>='"+currtime+"' and unreg=0";
	         rs = stmt.executeQuery(sql);
	         if(rs.next())
	         {
	        	 regcount = rs.getInt("regcount");
	         }
	         if(regcount>=max_pat)
	          {
	         	JOptionPane.showMessageDialog(new JFrame().getContentPane(), 
	         			"该号种已经挂满！", "警告", JOptionPane.WARNING_MESSAGE);
	          }
	         else
	         {
	        	 sql = "select count(*) as regtotal from register_info";
		         rs = stmt.executeQuery(sql);
		         if(rs.next())
		         {
		        	 registernum = rs.getInt("regtotal");
		        	 registernum++;
		         }
		         text_regnum.setText(Integer.toString(registernum));
	        	 
		         sql = "insert into register_info (register_num,registration_num,"
		         		+ "doc_num,pat_num,pat_count,unreg,reg_datetime,reg_cost) "
		         		+ "values ('"+registernum+"','"+sel_reg_num+"','"+sel_doc_num+"',"
		         		+ "'"+MainApp.pat_doc_num+"','"+(1+regcount)+"','"+0+"','"+df.format(new Date())+"'"
		         		+ ",'"+regcost+"')";
		         stmt.executeUpdate(sql);
		         
		         double charge;
		         charge = Double.parseDouble(text_charge.getText());
		         if(text_pay.isEditable())
		         {
		        	 sql = "update patient_info set prestore_amount = "
								+ ""+charge+"+prestore_amount where "
								+ "pat_num= '"+MainApp.pat_doc_num+"'";
		         }
		         else
		         {
		        	 sql = "update patient_info set prestore_amount = "
								+ ""+charge+"where pat_num= '"+MainApp.pat_doc_num+"'";
		         }
		         stmt.executeUpdate(sql);
		         
		         sql = "update register_info set pat_count= '"+(1+regcount)+"' where "
		         		+ "registration_num='"+sel_reg_num+"' and reg_datetime>="
		         		+ "'"+currtime+"'";
		         stmt.executeUpdate(sql);
		         
		         flag_of_changedbyreg = true;
		         text_office.clear();
		         text_doc.clear();
		         text_regtype.clear();
		         text_regname.clear();
		         text_cost.clear();
		     	text_pay.clear();
		     	text_charge.clear();
		     	text_regnum.clear();
		     	JOptionPane.showMessageDialog(new JFrame().getContentPane(), 
	         			"挂号成功！\n挂号编号为"+registernum, "提示", JOptionPane.INFORMATION_MESSAGE);
	         }
	         rs.close();
	         stmt.close();
	         conn.close();
	     }catch(SQLException se){
	         // 处理 JDBC 错误
	         se.printStackTrace();
	     }
    }
    @FXML
    private void on_btn_exit_clicked(ActionEvent event)
    {
    	Event.fireEvent(MainApp.getPrimaryStage(),
    		new WindowEvent(MainApp.getPrimaryStage(), WindowEvent.WINDOW_CLOSE_REQUEST ));
    }
    @FXML
    private void on_btn_clear_clicked(ActionEvent event)
    {
        flag_of_changedbyreg = true;
        text_office.clear();
        text_doc.clear();
        text_regtype.clear();
        text_regname.clear();
        text_cost.clear();
    	text_pay.clear();
    	text_charge.clear();
    	text_regnum.clear();
    }
    @FXML
    private void on_btn_logout_clicked(ActionEvent event)
    {
    	MainApp.setLoginUi();
    }
}
