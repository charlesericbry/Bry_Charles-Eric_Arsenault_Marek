package client;

import java.io.Serializable;

public class Commande implements Serializable {
    private String cmd;
    private String session;

    public Commande(String arg, String session){
        this.session=session;
        this.cmd =arg;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public void setCmd(String cmd){
        this.cmd = cmd;
    }

    public String getSession(){
        return this.session;
    }

    public String getCmd(){
        return this.cmd;
    }


    @Override
    public String toString(){
        return this.cmd +" "+this.session;
    }


}
