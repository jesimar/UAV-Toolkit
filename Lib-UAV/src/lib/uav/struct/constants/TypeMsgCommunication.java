package lib.uav.struct.constants;

/**
 * The class models the types of communication messages available.
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public class TypeMsgCommunication {
    
    //Mensagem em que IFA diz ao MOSA o que ele deve fazer.
    public static final String IFA_MOSA_START             = "IFA->MOSA[START]";
    public static final String IFA_MOSA_STOP              = "IFA->MOSA[STOP]";    
    
    //Mensagem em que IFA diz ao MOSA alguma informação.
    public static final String IFA_MOSA_HOMELOCATION      = "IFA->MOSA[HOMELOCATION]";
        
    //Mensagem em que MOSA diz ao IFA o que ele fez.
    public static final String MOSA_IFA_INITIALIZED       = "MOSA->IFA[INITIALIZED]";
    public static final String MOSA_IFA_STARTED           = "MOSA->IFA[STARTED]";
    public static final String MOSA_IFA_STOPPED           = "MOSA->IFA[STOPPED]";
    public static final String MOSA_IFA_DISABLED          = "MOSA->IFA[DISABLED]";
//    public static final String MOSA_IFA_MISSION_COMPLETED = "MOSA->IFA[MISSION_COMPLETED]";
    
    //Mensagem em que IFA diz a GCS alguma informação.
    public static final String IFA_GCS_INFO               = "IFA->GCS[INFO]";
    public static final String IFA_GCS_REPLANNER          = "IFA->GCS[REPLANNER]";
    
    //Mensagem em que MOSA diz a GCS alguma informação.
    public static final String MOSA_GCS_PLANNER           = "MOSA->GCS[PLANNER]";
    
    //Mensagem generica usada por todo o sistema.
    public static final String UAV_ROUTE_FAILURE          = "uav-route-failure";
    
}
