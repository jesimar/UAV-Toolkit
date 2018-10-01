package uav.manager.os;

/**
 * @author Jesimar S. Arantes
 * @see version 3.0.0
 */
public interface ServicesOS {
    
    public String startInternalTerminalCmd();
    
    public String cmdTestInstallationPython();
    public String patternTestInstallationPython();
    
    public String cmdTestInstallationPip();
    public String patternTestInstallationPip();
    
    public String cmdTestInstallationDronekit();
    public String patternTestInstallationDronekit();
    
    public String cmdTestInstallationDronekitSITL();
    public String patternTestInstallationDronekitSITL();
    
    public String cmdTestInstallationMAVProxy();
    public String patternTestInstallationMAVProxy();

    public String getBatchExtension();

    public String startExternalTerminalCmd(String cmd_exec);
    public String startExternalTerminalScript(String script);

    public String cmdPrintPath();

    public String cmdToExecOnDir(String appName);
    
}
