package org.springframework.samples.petclinic.feeding;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FeedingService {
	
	FeedingRepository fr;
	
	@Autowired
	public FeedingService(FeedingRepository fr) 
	{
		this.fr = fr;
	}
	
    public List<Feeding> getAll(){
        return fr.findAll();
    }

    public List<FeedingType> getAllFeedingTypes(){
        return fr.findAllFeedingTypes();
    }

    public FeedingType getFeedingType(String typeName) {
        return fr.findFeedingType(typeName);
    }
    
    @Transactional(rollbackFor = UnfeasibleFeedingException.class)
    public Feeding save(Feeding p) throws UnfeasibleFeedingException {
    	if(p.getPet().getType() != p.getFeedingType().getPetType()) 
    	{
    		throw new UnfeasibleFeedingException();
    	}
        return fr.save(p);       
    }

    
}
