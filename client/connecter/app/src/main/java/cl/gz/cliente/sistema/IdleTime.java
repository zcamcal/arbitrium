package cl.gz.cliente.sistema;

import cl.gz.cliente.configuration.Configuration;
import cl.gz.rmi.Cliente;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;

/**
 * Utility method to retrieve the idle time on Windows and sample code to test
 * it. JNA shall be present in your classpath for this to work (and compile).
 *
 * @author ochafik
 */
public class IdleTime implements Runnable {


    /**
     * Get the amount of milliseconds that have elapsed since the last input
     * event (mouse or keyboard)
     *
     * @return idle time in milliseconds
     */
    public static int getIdleTimeMillisWin32() {
        User32.LASTINPUTINFO lastInputInfo = new User32.LASTINPUTINFO();
        User32.INSTANCE.GetLastInputInfo(lastInputInfo);
        return Kernel32.INSTANCE.GetTickCount() - lastInputInfo.dwTime;
    }

    enum State {
        LOCK, USED;
    };

    public void run() {
        String idleTime = Configuration.getInstance().getProperty("idleTime");
        if(idleTime.equals("1")){
            State state = State.USED;
            for (;;) {
                int idleSec = getIdleTimeMillisWin32() / 1000;
                State newState
                        = idleSec < 30 ? State.USED
                                : idleSec > 10 * 60 ? State.LOCK : State.USED;
                if (newState != state) {
                    state = newState;
                    Cliente.internLockComputer();
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ex) {
                    System.out.println("ERROR metodo run, CLASE idleTime"
                            + "ERROR: " + ex.getMessage());
                }
            }
        }else{
            //ENTONCES NO SE EJECUTA
            System.out.println("no entramos");
        }
    }
}
