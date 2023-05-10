package CanVas.server.test;

import CanVas.server.src.main.java.org.canvas.server.SignalHandle;
import CanVas.server.src.main.java.org.canvas.server.TraceHandle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sun.misc.Signal;

import static org.junit.jupiter.api.Assertions.*;

public class SignalTest {

@Test
@DisplayName("Checked sample signal UUID exists")
public void checkSignalUUID(){
TraceHandle newTrace = new TraceHandle("test", "test@gmail.com");
SignalHandle newSignal = new SignalHandle(newTrace, "testSignal");
assertNotNull(newSignal.getUUID());

}
@Test
@DisplayName("Checked sample signal UUID string exists")
public void checkSignalUUIDString(){
TraceHandle newTrace = new TraceHandle("test", "test@gmail.com");
SignalHandle newSignal = new SignalHandle(newTrace, "testSignal");
assertNotNull(newSignal.getUUIDString());

}
@Test
@DisplayName("Checked sample signal data table name exists")
public void checkSignalTableName(){
TraceHandle newTrace = new TraceHandle("test", "test@gmail.com");
SignalHandle newSignal = new SignalHandle(newTrace, "testSignal");
assertNotNull(newSignal.getDataTableName());

}
}
 
