package org.springframework.samples.petclinic.feeding;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class FeedingTypeFormatter implements Formatter<FeedingType>{
	
	private final FeedingService fs;
	
	@Autowired
	public FeedingTypeFormatter(FeedingService fs) 
	{
		this.fs = fs;
	}

    @Override
    public String print(FeedingType object, Locale locale) {
        return object.getName();
    }

    @Override
    public FeedingType parse(String text, Locale locale) throws ParseException {
    	FeedingType ft = fs.getFeedingType(text);
        if(ft != null) {return ft;}
        throw new ParseException("Feeding Type not found: " +  text,0);
    }
    
}
