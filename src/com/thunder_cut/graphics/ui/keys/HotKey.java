/*
 * HotKey.java
 * Author : 나상혁
 * Created Date : 2020-02-11
 */
package com.thunder_cut.graphics.ui.keys;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum HotKey {

    REDO(KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_Z),
    UNDO(KeyEvent.VK_CONTROL, KeyEvent.VK_Z),
    BRUSH(KeyEvent.VK_B),
    ERASER(KeyEvent.VK_E),
    ;

    private Set<Integer> keyCodes;
    private Runnable action;

    /**
     * Register Default Key set for execute an action.
     * @param defaultKeyCode KeyCode from {@link KeyEvent#getKeyCode()}
     *                       or
     *                       Virtual key Constants in {@link KeyEvent}
     *                       like {@link KeyEvent#VK_0}
     * @see KeyEvent
     */
    HotKey(int... defaultKeyCode){
        this.keyCodes = new HashSet<>();
        for(int event : defaultKeyCode){
            this.keyCodes.add(event);
        }
        action = ()->{
            String sb = "Hot-key Function \"" + this.name() + "\" is not registered.\n" +
                    "Registered Key set is \"" +
                    keyCodes.stream()
                            .map(KeyEvent::getKeyText)
                            .collect(Collectors.joining(" + ")) +
                    "\".";
            System.err.println(sb);
        };
    }

    /**
     * Register action for execute when pressed key set.
     * @param action An action what you execute.
     * @see Runnable
     */
    public void setAction(Runnable action){
        this.action = action;
    }

    /**
     * Change Key set for execute an action.
     * @param keyCodes KeyCode from {@link KeyEvent#getKeyCode()}
     *                 or
     *                 Virtual key Constants in {@link KeyEvent}
     *                 like {@link KeyEvent#VK_0}
     * @see KeyEvent
     */
    public void setKeyCodes(int... keyCodes){
        this.keyCodes.clear();
        for(int event : keyCodes){
            this.keyCodes.add(event);
        }
    }

    boolean isMatched(final Set<Integer> keys){
        return keys.containsAll(this.keyCodes);
    }

    void execute(){
        this.action.run();
    }

}
