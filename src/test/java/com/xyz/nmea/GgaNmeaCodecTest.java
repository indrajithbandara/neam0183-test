package com.xyz.nmea;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by chin on 8/22/16.
 */
public class GgaNmeaCodecTest {

    @Test
    public void decodeValidMessage() {
        GgaNmeaCodec codec = new GgaNmeaCodec();
        codec.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println(arg);
            }
        });
        Observer mockObserver = mock(Observer.class);
        codec.addObserver(mockObserver);
        String content = "$GPGGA,092750.000,5321.6802,N,00630.3372,W,1,8,1.03,61.7,M,55.2,M,,*76\r\n";
        codec.decode(content);

        content = "$GPGGA,092751.000,5321.6802,N,00630.3371,W,1,8,1.03,61.7,M,55.3,M,,*75\r\n";
        codec.decode(content);

        content = "$GPGGA,092204.999,4250.5589,S,14718.5084,E,1,04,24.4,19.7,M,,,,0000*1F\r\n";
        codec.decode(content);
        //codec.deleteObservers();
        verify(mockObserver, times(3)).update(eq(codec), any());

    }

    @Test
    public void encode() {
        GgaNmeaCodec codec = new GgaNmeaCodec();
         String content = "$GPGGA,092750.000,5321.6802,N,00630.3372,W,1,8,1.03,61.7,M,55.2,M,,*76\r\n";

        codec.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                AbstractNmeaObject obj = (AbstractNmeaObject) arg;
                List<String> contents = codec.encode(obj);
                //assertThat(contents.get(0), equalTo(content));
                assertEquals(Arrays.asList(content), contents);
            }
        });
        codec.decode(content);
    }

}
