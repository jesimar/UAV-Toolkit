/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.manager.check;

import java.util.function.Consumer;

/**
 *
 * @author Marcio
 */
public interface Check<T> {
    public void check(Consumer<T> consumer);
}
