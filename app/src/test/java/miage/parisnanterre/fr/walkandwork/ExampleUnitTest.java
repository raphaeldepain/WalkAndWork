package miage.parisnanterre.fr.walkandwork;

import android.content.Intent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.Collections;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(RobolectricTestRunner.class)
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void creation_Employeur(){
        try {
            assertEquals("1",new AddUser().execute("create", "test", "test", "test", "test", "1").get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void connection_User(){
        try {
            assertEquals("{\"id\":\"2\",\"nom\":\"Lazzaroli\",\"email\":\"Giuseppe\",\"phone\":\"065536833\",\"longitude\":\"2.8393162\",\"latitude\":\"48.8946681\",\"isEmployeur\":\"0\",\"data\":null}",new ConnexionApp().execute("connection","\"Giuseppe\"").get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addCoordonnee_User(){
        try {
            assertEquals("1",new UpdatePosition().execute("updatePosition","2",Double.toString(2.8393162),Double.toString(48.8946681)).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    

}