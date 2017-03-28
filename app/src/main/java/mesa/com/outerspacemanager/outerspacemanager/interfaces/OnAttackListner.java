package mesa.com.outerspacemanager.outerspacemanager.interfaces;

import mesa.com.outerspacemanager.outerspacemanager.model.Attack;

/**
 * Created by Lucas on 27/03/2017.
 */

public interface OnAttackListner {
    void onAttackClicked(Attack atk);
    void onAttackTimeEnded();
}
