package vu.lt.services;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import java.io.Serializable;
import java.util.Random;

@ApplicationScoped
public class PackageWeightGenerator implements Serializable {

    public Integer generateWeight(){
        try{
            Thread.sleep(3000);
        }
        catch (InterruptedException e){
        }
       Integer newWeight = new Random().nextInt(20);
        return newWeight;
    }
}
