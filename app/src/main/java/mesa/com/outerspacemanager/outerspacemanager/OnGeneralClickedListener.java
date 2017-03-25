package mesa.com.outerspacemanager.outerspacemanager;

import mesa.com.outerspacemanager.outerspacemanager.model.Attack;
import mesa.com.outerspacemanager.outerspacemanager.model.Report.Report;

/**
 * Created by Lucas on 20/03/2017.
 */

public interface OnGeneralClickedListener {
     void onAttackClicked(Attack atk);
     void onReportClicked(Report report);
}
