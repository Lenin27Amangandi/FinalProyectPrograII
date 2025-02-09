package Framework; 
 
import javax.swing.JOptionPane; 
public abstract class RAConfig { 
 
    public static final void showMsg(String msg){ 
        JOptionPane.showMessageDialog(null, msg, "🎨 ArtVision", JOptionPane.INFORMATION_MESSAGE); 
    } 
    public static final void showMsgError(String msg){ 
        JOptionPane.showMessageDialog(null, msg, "☠️ ArtVision", JOptionPane.OK_OPTION); 
    } 
    public static final boolean showConfirmYesNo(String msg){ 
        return (JOptionPane.showConfirmDialog(null, msg, " 🎨 ArtVision", JOptionPane.YES_NO_OPTION) == 
                JOptionPane.YES_OPTION); 
    } 
}