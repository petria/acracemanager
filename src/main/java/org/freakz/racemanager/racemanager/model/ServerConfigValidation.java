package org.freakz.racemanager.racemanager.model;

import java.io.Serializable;

public class ServerConfigValidation implements Serializable {

    private boolean basePathOk;

    private boolean acDirectoryOk;

    private boolean strackerDirectoryOk;

    public boolean isBasePathOk() {
        return basePathOk;
    }

    public void setBasePathOk(boolean basePathOk) {
        this.basePathOk = basePathOk;
    }

    public boolean isAcDirectoryOk() {
        return acDirectoryOk;
    }

    public void setAcDirectoryOk(boolean acDirectoryOk) {
        this.acDirectoryOk = acDirectoryOk;
    }

    public boolean isStrackerDirectoryOk() {
        return strackerDirectoryOk;
    }

    public void setStrackerDirectoryOk(boolean strackerDirectoryOk) {
        this.strackerDirectoryOk = strackerDirectoryOk;
    }
}
