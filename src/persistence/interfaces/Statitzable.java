/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence.interfaces;

import persistence.State;


/**
 *
 * @author wida36528678
 */
public interface Statitzable {
    public void loadFromState();
    public void saveToState();
    public void updateFromState(State state);
    public State getState();
}
