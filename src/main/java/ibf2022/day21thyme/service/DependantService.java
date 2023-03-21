package ibf2022.day21thyme.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf2022.day21thyme.model.Dependant;
import ibf2022.day21thyme.repository.DependantRepo;

@Service
public class DependantService {
    
    @Autowired
    DependantRepo depRepo;

    public List<Dependant> findAll() {
        return depRepo.findAll();
    }

    public Dependant findById(Integer id) {
        return depRepo.findById(id);
    }

    public Boolean save(Dependant dep) {
        return depRepo.save(dep);
    }
    
    public int update(Dependant dep) {
        return depRepo.update(dep);
    }

    public int delete(Integer id) {
        return depRepo.delete(id);
    }
}
