package ibf2022.day21thyme.repository;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import ibf2022.day21thyme.model.Dependant;

@Repository
public class DependantRepo {

    RestTemplate restTemplate = new RestTemplate();

    private static final String GET_DEPENDANTS_ENDPOINT_URL = "http://localhost:8080/api/dependants";
    private static final String GET_DEPENDANTBYID_ENDPOINT_URL =  "http://localhost:8080/api/dependants/{id}";
    private static final String CREATE_DEPENDANT_ENDPOINT_URL = "http://localhost:8080/api/dependants";
    private static final String UPDATE_DEPENDANT_ENDPOINT_URL = "http://localhost:8080/api/dependants";
    private static final String DELETE_DEPENDANT_ENDPOINT_URL = "http://localhost:8080/api/dependants/{id}";
    
    public List<Dependant> findAll() {
        ResponseEntity<List<Dependant>> result = restTemplate.exchange(GET_DEPENDANTS_ENDPOINT_URL, HttpMethod.GET, null, 
        new ParameterizedTypeReference<List<Dependant>>() { 
        }); // ParameterizedTypeReference specifies the type of the response body
        return result.getBody();
    }

    public Dependant findById(Integer id) {
        Dependant dep = restTemplate.getForObject(GET_DEPENDANTBYID_ENDPOINT_URL, Dependant.class, Map.of("id", id));
        return dep;
    }

    public Boolean save(Dependant dep) {
        Boolean bResult = restTemplate.postForObject(CREATE_DEPENDANT_ENDPOINT_URL, dep, Boolean.class);
        return bResult;
    }
    
    public int update(Dependant dep) {
        restTemplate.put(UPDATE_DEPENDANT_ENDPOINT_URL, dep);
        return 1;
    }

    public int delete(Integer id) {
        restTemplate.delete(DELETE_DEPENDANT_ENDPOINT_URL, Map.of("id", id));
        return 1;
    }

}
