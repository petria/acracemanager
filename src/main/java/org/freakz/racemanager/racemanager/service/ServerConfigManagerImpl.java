package org.freakz.racemanager.racemanager.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.racemanager.racemanager.model.HostOS;
import org.freakz.racemanager.racemanager.model.ServerStartupPaths;
import org.freakz.racemanager.racemanager.util.HostOsDetector;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ServerConfigManagerImpl implements ServerConfigManager {

    private final HostOS hostOS;

    public ServerConfigManagerImpl() {
        HostOsDetector hostOsDetector = new HostOsDetector();
        hostOS = hostOsDetector.detectHostOs();
    }


    private ServerStartupPaths getStartUpPathsWindows() {
        ServerStartupPaths model = new ServerStartupPaths();
        model.setServerDirectory("C:\\AC\\server\\");
        model.setServerCommand("C:\\AC\\server\\acServer.exe");

        model.setStrackerDirectory("C:\\AC\\stracker\\");
//        model.setStrackerCommand("cmd.exe /C start-stracker.bat");
        model.setStrackerCommand("C:\\AC\\stracker\\stracker.exe --stracker_ini stracker.ini");
//        model.setStrackerCommand("start-stracker.cmd");

        return model;
    }

    private ServerStartupPaths getStartUpPathsLinux() {
        ServerStartupPaths model = new ServerStartupPaths();
        model.setServerDirectory("/home/petria/AC3/server");
        model.setServerCommand("/home/petria/AC3/server/acServer");

        model.setStrackerDirectory("/home/petria/AC3/stracker");
// TODO set linux exe        model.setStrackerCommand("/home/petria/AC3/stracker");

        return model;
    }


    @Override
    public ServerStartupPaths getDefaultConfig() {
        if (hostOS == HostOS.LINUX) {
            return getStartUpPathsLinux();
        } else {
            return getStartUpPathsWindows();
        }
    }

    @Override
    public HostOS getHostOS() {
        return this.hostOS;
    }

}
