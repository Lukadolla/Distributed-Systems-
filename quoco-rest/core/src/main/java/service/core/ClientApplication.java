package service.core;

import java.util.ArrayList;

public class ClientApplication {

    private ClientInfo info;
    private ArrayList<Quotation> listOfQuotations;
    private int applicationNum;

    public ClientApplication(ClientInfo info, ArrayList<Quotation> listOfQuotations, int applicationNum) {
        this.info = info;
        this.listOfQuotations = listOfQuotations;
        this.applicationNum = applicationNum;
    }

    public ClientApplication(){

    }

    public ClientInfo getInfo() {
        return info;
    }

    public void setInfo(ClientInfo info) {
        this.info = info;
    }

    public ArrayList<Quotation> getListOfQuotations() {
        return listOfQuotations;
    }

    public void setListOfQuotations(ArrayList<Quotation> listOfQuotations) {
        this.listOfQuotations = listOfQuotations;
    }

    public int getApplicationNum() {
        return applicationNum;
    }

    public void setApplicationNum(int applicationNum) {
        this.applicationNum = applicationNum;
    }
}
