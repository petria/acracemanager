package org.freakz.racemanager.racemanager.service;

import org.freakz.racemanager.racemanager.model.HostOS;
import org.freakz.racemanager.racemanager.model.ServerConfig;
import org.freakz.racemanager.racemanager.model.ServerConfigValidation;
import org.freakz.racemanager.racemanager.model.ServerStartupPaths;
import org.freakz.racemanager.racemanager.util.HostOsDetector;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class ServerConfigManagerImpl implements ServerConfigManager {

    private final HostOS hostOS;
    private final Map<String, ServerConfig> serverConfigMap = new HashMap<>();

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

    @Override
    public ServerConfigValidation validateServerConfig(ServerConfig serverConfig) {
        ServerConfigValidation validation = new ServerConfigValidation();
        String basePath = serverConfig.getBasePath();

        validation.setBasePathOk(checkIfPathExists(basePath));
        validation.setAcDirectoryOk(checkIfPathExists(basePath + "/server/"));
        validation.setStrackerDirectoryOk(checkIfPathExists(basePath + "/stracker/"));

        return validation;
    }

    private boolean checkIfPathExists(String path) {
        File f = new File(path);
        return f.exists();
    }

    @Override
    public void setServerConfig(String serverId, ServerConfig serverConfig) {
        serverConfigMap.put(serverId, serverConfig);
    }

    @Override
    public ServerConfig getServerConfig(String serverId) {
        ServerConfig serverConfig = serverConfigMap.get(serverId);
        if (serverConfig == null) {
            serverConfig = new ServerConfig(serverId);
            serverConfigMap.put(serverId, serverConfig);
        }
        return serverConfig;
    }
}
