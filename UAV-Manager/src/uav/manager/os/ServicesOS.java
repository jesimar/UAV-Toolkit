/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.manager.os;

/**
 *
 * @author jesimar
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
