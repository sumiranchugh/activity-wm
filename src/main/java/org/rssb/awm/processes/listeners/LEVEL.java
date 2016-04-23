package org.rssb.awm.processes.listeners;

/**
 * Created by Sumiran Chugh on 3/28/2016.
 *
 * @copyright atlas
 */
public enum LEVEL {

    AREA, ZONE,CENTER;

    int getIndex(){
     return  this.ordinal();
    }

   static LEVEL getLevel(int ordinal){

        return LEVEL.values()[ordinal];
    }
}

