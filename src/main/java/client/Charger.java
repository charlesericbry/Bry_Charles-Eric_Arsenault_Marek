package client;

import java.io.Serializable;

public class Charger implements Serializable {
    private final String arg = "CHARGER";
    private String session;

    public Charger(String session){
        this.session=session;
    }

    public void setSession(String session){
        this.session=session
    }

    public String getSession(){
        
    }


    @Override
    public String toString(){
        return this.arg+" "+this.session;
    }


}
