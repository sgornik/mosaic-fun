package Mosaic;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sgornik on 28.2.2015..
 * Package : com.example.learning
 * Project Name : Test
 */
public class RunMosaic {
    public static void main(String[] args) {

        final MosaicChaos mosaic = new MosaicChaos();

        mosaic.makeWorld();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                mosaic.moveDisturbance();
                mosaic.disturb();
                if (mosaic.isDone())
                    this.cancel();
            }
        };

        Timer timer = new Timer();

        timer.schedule(task, new Date(), 1);
    }
}