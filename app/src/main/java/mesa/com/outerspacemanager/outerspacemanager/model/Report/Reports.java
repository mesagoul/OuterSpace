package mesa.com.outerspacemanager.outerspacemanager.model.Report;

import java.util.ArrayList;

/**
 * Created by Lucas on 21/03/2017.
 */

public class Reports {
    private ArrayList<Report> reports;
    private int size;

    public ArrayList<Report> getReports() {
        return reports;
    }

    public void setReports(ArrayList<Report> reports) {
        this.reports = reports;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
