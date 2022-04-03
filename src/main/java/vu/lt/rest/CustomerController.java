package vu.lt.rest;

import lombok.Getter;
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
    @Setter
    @Getter
    private CustomerDAO customerDAO;

    @Path("/create")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response create(CustomerDto customerData){
        Customer customer = new Customer();
        customer.setName(customerData.getName());
        customer.setSurname(customerData.getSurname());

        customerDAO.create(customer);
        return Response.ok().build();
    }


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
        customerDto.setId(customer.getId());
        customerDto.setSurname(customer.getSurname());

        return Response.ok(customerDto).build();
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(@PathParam("id") final Integer id, CustomerDto customerData){
        try{
            Customer existingCustomer = customerDAO.findById(id);
            if(existingCustomer == null){
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            existingCustomer.setName(customerData.getName());
            existingCustomer.setSurname(customerData.getName());

            return Response.ok().build();
        }
        catch (OptimisticLockException ole) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
}
