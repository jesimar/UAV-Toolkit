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
public class WindowsOS implements ServicesOS{

    @Override
    public String startInternalTerminalCmd() {
        return "cmd.exe";
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
        return "dronekit-sitl --version";
    }

    @Override
    public String patternTestInstallationDronekitSITL() {
        return  "(.)*3\\.(.)*\\.(.)*";
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
        return ".bat";
    }

    @Override
    public String startExternalTerminalCmd(String cmdToExec) {
        return "start " + cmdToExec;
    }
   
    @Override
    public String startExternalTerminalScript(String script) {
        return "start " + script;
    }
    
    @Override
    public String cmdPrintPath(){
        return "PATH";
    }
    
    @Override
    public String cmdToExecOnDir(String appName) {
        return appName;
    }
   
}
