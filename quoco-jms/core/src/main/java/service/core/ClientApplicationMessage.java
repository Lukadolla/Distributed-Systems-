package service.core;

import java.io.Serializable;
import java.util.LinkedList;

public class ClientApplicationMessage implements Serializable {
    public long id;
    public ClientInfo info;
    public LinkedList<Quotation> quotations;

    public ClientApplicationMessage(long id, ClientInfo clientInfo, LinkedList<Quotation> quotations) {
        this.id = id;
        this.info = clientInfo;
        this.quotations = quotations;
    }
}
