package obss.hris.business.concretes;

import lombok.AllArgsConstructor;
import obss.hris.business.abstracts.HumanResourceService;
import obss.hris.model.entity.HumanResource;
import obss.hris.repository.HumanResourceRepository;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class HumanResourceServiceImpl implements HumanResourceService {
    private HumanResourceRepository humanResourceRepository;


    @Override
    public HumanResource createHumanResource(HumanResource humanResource) {
        return humanResourceRepository.save(humanResource);
    }

    @Override
    public HumanResource getByUserName(String userName) {
        return humanResourceRepository.findByUserName(userName);
    }
}
