package edu.nus.iss.gdipsa.grouphub.ServiceLayer.GroupHub;

import edu.nus.iss.gdipsa.grouphub.ModelLayer.GroupHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.wavefront.WavefrontProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class GroupHubValidator implements Validator {
    @Autowired
    GroupHubPublisherService groupHubPublisherService;

    @Override
    public boolean supports(Class<?> clazz) {
        return GroupHub.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        GroupHub groupHub = (GroupHub) target;

        // 对GroupHub这个对象进行一系列的检查

        // the quantity can't be a negative integer
        if(groupHub.getQuantity() < 0) {
            errors.rejectValue("quantity", "negativeValue", "The quantity cannot be negative.");
        }

        // latitude and longitude can't be a negative integer
        if(groupHub.getLongitude() < 0 || groupHub.getLatitude() < 0) {
            errors.rejectValue("locality", "negativeValue", "The locality cannot be negative.");
        }

        // valid time
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endTime", "The end time is required");

        if(LocalDate.now().isAfter(groupHub.getEndTime())) {
            errors.rejectValue("endTime", "wrongEndTime", "End time must be after start time.");
        }

        // The publisher must be declared.
        // ValidationUtils.rejectIfEmptyOrWhitespace(errors, "publishedBy", "errors.publishedBy", "publishedBy is required.");
    }
}
