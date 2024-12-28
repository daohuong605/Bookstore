package dto;

public class dashboard {
    private String genrename;
    private Integer totalstock;
    private Integer totalsold;
    private Integer totalrevenue;


    public dashboard(String genrename, Integer totalstock, Integer totalsold, Integer totalrevenue) {
        this.genrename = genrename;
        this.totalstock = totalstock;
        this.totalsold = totalsold;
        this.totalrevenue = totalrevenue;
    }

    // Getter Methods
    public String getGenrename() {
        return genrename;
    }

    public Integer getTotalstock() {
        return totalstock;
    }

    public Integer getTotalsold() {
        return totalsold;
    }

    public Integer getTotalrevenue() {
        return totalrevenue;
    }
}
