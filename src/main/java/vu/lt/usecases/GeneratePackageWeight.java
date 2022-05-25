package vu.lt.usecases;

import lombok.Getter;
import lombok.Setter;
import vu.lt.services.PackageWeightGenerator;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SessionScoped
@Named
public class GeneratePackageWeight implements Serializable{
    @Inject
    private PackageWeightGenerator packageWeightGenerator;

    private CompletableFuture<Integer> generateWeightTask = null;

    public String generateWeight(){
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        generateWeightTask = CompletableFuture.supplyAsync(() -> packageWeightGenerator.generateWeight());
        return "customer?faces-redirect=true&customerId=" + requestParameters.get("customerId");
    }

    public boolean isTaskRunning(){
        return generateWeightTask != null && !generateWeightTask.isDone();
    }

    public String getStatus() throws ExecutionException, InterruptedException {
        if(generateWeightTask == null){
            return null;
        }
        else if (isTaskRunning()){
            return "Weight generation in progress...";
        }

        return "Generated package weight : " + generateWeightTask.get();
    }
}
