package vu.lt.rest;

import lombok.Setter;
import vu.lt.entities.Customer;
import vu.lt.persistence.CustomerDAO;
import vu.lt.rest.contracts.CustomerDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/customers")
public class CustomerController {

    @Inject
    private CustomerDAO customerDAO;

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") final Integer id){
       Customer customer = customerDAO.findById(id);
       if(customer == null){
           return Response.status(Response.Status.NOT_FOUND).build();
       }
       CustomerDto customerDto = new CustomerDto();
       customerDto.setName(customer.getName());
       customerDto.setSurname(customer.getSurname());
       customerDto.setId(customer.getId());

       return Response.ok(customerDto).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createCustomer(CustomerDto customerData){
        if(customerData.getName() != null && customerData.getSurname() != null){
            Customer newCustomer = new Customer();
            newCustomer.setName(customerData.getName());
            newCustomer.setSurname(customerData.getSurname());

            customerDAO.create(newCustomer);
            return Response.ok(newCustomer.getId()).build();
        }
        else{
            return  Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateCustomer(@PathParam("id") final Integer id, CustomerDto CustomerData){

        Customer existingCustomer = customerDAO.findById(id);
        if(existingCustomer == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        existingCustomer.setName(CustomerData.getName());
        existingCustomer.setSurname(CustomerData.getSurname());

        customerDAO.update(existingCustomer);
        return Response.ok().build();
    }
}
