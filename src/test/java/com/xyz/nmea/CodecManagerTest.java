package com.xyz.nmea;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by wuf2 on 2/21/2015.
 */
public class CodecManagerTest {

    @Test
    public void decodeRmcMessage() throws Exception {
        String content = "$GPRMC,092751.000,A,5321.6802,N,00630.3371,W,0.06,31.66,280511,,,A*45\r\n";
        CodecManager manager = new CodecManager();
        manager.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println(arg);
            }
        });

        Observer mockObserver = mock(Observer.class);
        manager.addObserver(mockObserver);

        manager.decode(content);

        verify(mockObserver, times(1)).update(eq(manager), any());
    }

    @Test
    public void decodeConcatenatedMessage() throws Exception {
        CodecManager manager = new CodecManager();
        manager.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println(arg);
            }
        });

        Observer mockObserver = mock(Observer.class);
        manager.addObserver(mockObserver);

        manager.decode("$GPRMC,092751.000,A,5");
        manager.decode("321.6802,N,00630.3371,W,0.06,31.66,280511,,,A*45\r\n");

        verify(mockObserver, times(1)).update(eq(manager), any());
    }

    @Test
    public void decodeMultipleMessage() throws Exception {
        CodecManager manager = new CodecManager();
        Observer mockObserver = mock(Observer.class);
        manager.addObserver(mockObserver);

        String content = "$GPRMC,092751.000,A,5321.6802,N,00630.3371,W,0.06,31.66,280511,,,A*45\r\n$GPRMC,092751.000,A,5321.6802,N,00630.3371,W,0.06,31.66,280511,,,A*45\r\n";
        manager.decode(content);

        content = "$GPGGA,092750.000,5321.6802,N,00630.3372,W,1,8,1.03,61.7,M,55.2,M,,*76\r\n"
                + "$GPRMC,123519,A,4807.038,N,01131.000,E,022.4,084.4,230394,003.1,W*6A\r\n"
                + "$GPRMC,092751.000,A,5321.6802,N,00630.3371,W,0.06,31.66,280511,,,A*45\r\n"
                + "!AIVDM,1,1,,B,16:>>s5Oh08dLO8AsMAVqptj0@>p,0*67\r\n";
        manager.decode(content);

        verify(mockObserver, times(6)).update(eq(manager), any());
    }

    @Test(expected = ClassNotFoundException.class)
    public void decodeInvalidMessage() throws Exception {
        String content = "$GPAAM,A,A,0.10,N,WPTNME*32\r\n$GPRMC,092751.000,A,5321.6802,N,00630.3371,W,0.06,31.66,280511,,,A*45\r\n";
        CodecManager manager = new CodecManager();
        manager.decode(content);
    }

    @Test
    public void encode() {
        final GgaNmeaCodec codec = new GgaNmeaCodec();
        final String content = "$GPGGA,092750.000,5321.6802,N,00630.3372,W,1,8,1.03,61.7,M,55.2,M,,*76\r\n";
        codec.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                try {
                    CodecManager manager = new CodecManager();
                    AbstractNmeaObject obj = (AbstractNmeaObject) arg;
                    List<String> contents = manager.encode(obj);
                    assertEquals(Arrays.asList(content), contents);
                } catch (Exception e) {
                    assertTrue("Expect no exception " + e, false);
                }
            }
        });

        codec.decode(content);
    }
}
