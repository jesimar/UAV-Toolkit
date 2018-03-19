/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.generic.struct.constants;

/**
 *
 * @author Jesimar S. Arantes
 */
public class TypeMsgCommunication {
    
    //Mensagem em que IFA diz ao MOSA o que ele deve fazer.
    public static final String IFA_MOSA_START           = "IFA->MOSA[START]";
    public static final String IFA_MOSA_STOP            = "IFA->MOSA[STOP]";    
    
    //Mensagem em que IFA diz ao MOSA alguma informação.
    public static final String IFA_MOSA_HOMELOCATION    = "IFA->MOSA[HOMELOCATION]";
        
    //Mensagem em que MOSA diz ao IFA o que ele fez.
    public static final String MOSA_IFA_INITIALIZED     = "MOSA->IFA[INITIALIZED]";
    public static final String MOSA_IFA_STARTED         = "MOSA->IFA[STARTED]";
    public static final String MOSA_IFA_STOPPED         = "MOSA->IFA[STOPPED]";
    public static final String MOSA_IFA_DISABLED        = "MOSA->IFA[DISABLED]";
    
}
