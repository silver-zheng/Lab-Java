package hospital.module;

import javafx.beans.property.SimpleStringProperty;

public class PatientForList 
{  
    private final SimpleStringProperty regNum;  
    private final SimpleStringProperty patName;  
    private final SimpleStringProperty regTime;
    private final SimpleStringProperty regType;
    private final SimpleStringProperty valid;
   
    public PatientForList(String rNum, String pName, String rTime,String rType,String val) 
    {  
        this.regNum = new SimpleStringProperty(rNum);  
        this.patName = new SimpleStringProperty(pName);  
        this.regTime = new SimpleStringProperty(rTime);
        this.regType = new SimpleStringProperty(rType);
        this.valid = new SimpleStringProperty(val);
    }  
   
    public String getRegNum() {  
        return regNum.get();  
    }  
    public void setRegNum(String fName) {  
    	regNum.set(fName);  
    }  
          
    public String getPatName() {  
        return patName.get();  
    }  
    public void setPatName(String fName) {  
    	patName.set(fName);  
    }  
      
    public String getRegTime() {  
        return regTime.get();  
    }  
    public void setRegTime(String fName) {  
    	regTime.set(fName);  
    }
    
    public String getRegType() {  
        return regType.get();  
    }  
    public void setRegType(String fName) {  
    	regType.set(fName);  
    }
    public String getValid()
    {
    	return valid.get();
    }
    public void setValid(String val)
    {
    	valid.set(val);
    }
          
}  