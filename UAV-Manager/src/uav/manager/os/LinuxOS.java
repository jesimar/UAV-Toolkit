package uav.manager.os;

/**
 * @author Jesimar S. Arantes
 * @see version 3.0.0
 */
public class LinuxOS implements ServicesOS{

    @Override
    public String startInternalTerminalCmd() {
        return "bash";
    }
    
    @Override
    public String cmdTestInstallationDronekit() {
        return "python dronekit-test.py";
    }
    
    @Override
    public String patternTestInstallationDronekit() {
        return "(.)*dronekit-ok(.)*";
    }

    @Override
    public String cmdTestInstallationPython() {
        return "python --version";
    }

    @Override
    public String patternTestInstallationPython() {
         return "(.)* 2\\.7\\.(.)*";
    }

    @Override
    public String cmdTestInstallationPip() {
        return "python -m pip --version";
    }

    @Override
    public String patternTestInstallationPip() {
        return  "pip (.)*\\.(.)*\\.(.)*";
    }
    
    @Override
    public String cmdTestInstallationDronekitSITL() {
        return "python dronekit-sitl-test.py";
    }

    @Override
    public String patternTestInstallationDronekitSITL() {
        return  "(.)*dronekit-sitl-ok(.)*";
    }

    @Override
    public String cmdTestInstallationMAVProxy() {
        return "python mavproxy-test.py";
    }

    @Override
    public String patternTestInstallationMAVProxy() {
        return  "(.)*mavproxy-ok(.)*";
    }
    
    @Override
    public String getBatchExtension() {
        return ".sh";
    }

    @Override
    public String startExternalTerminalCmd(String cmdToExec) {
        return "gnome-terminal -e " + cmdToExec;
    }
    
    @Override
    public String startExternalTerminalScript(String script) {
        return "gnome-terminal -e ./" + script;
    }
    
    @Override
    public String cmdPrintPath(){
        return "echo $PATH";
    }

    @Override
    public String cmdToExecOnDir(String appName) {
        return "./" + appName;
    }
    
}
