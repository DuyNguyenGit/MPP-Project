package business;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class Fine implements Serializable {
    @Serial
    private static final long serialVersionUID = 6395476423187707134L;
    private Date fineDate;
    private Double amount;

    public Fine(Date fineDate, Double amount) {
        this.fineDate = fineDate;
        this.amount = amount;
    }

    public Date getFineDate() {
        return fineDate;
    }

    public void setFineDate(Date fineDate) {
        this.fineDate = fineDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
