package it.polimi.ingsw.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;


/**
 * This class is used to read the player command passed as input.
 */
public class ReadInput implements Callable<String> {
    private BufferedReader bufferedReader;

    public ReadInput() {
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public String call() throws IOException, InterruptedException {
        String in;
        while(!bufferedReader.ready()) {
            Thread.sleep(200);
        }
        in = bufferedReader.readLine();
        return in;
    }
}
