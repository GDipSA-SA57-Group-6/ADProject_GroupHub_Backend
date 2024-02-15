package edu.nus.iss.gdipsa.grouphub.Controller.GroupHub;

import edu.nus.iss.gdipsa.grouphub.ModelLayer.GroupHub;
import edu.nus.iss.gdipsa.grouphub.ModelLayer.User;
import edu.nus.iss.gdipsa.grouphub.RepositoryLayer.GroupHubRepository;
import edu.nus.iss.gdipsa.grouphub.RepositoryLayer.UserRepository;
import edu.nus.iss.gdipsa.grouphub.ServiceLayer.GroupHub.GroupHubPublisherService;
import edu.nus.iss.gdipsa.grouphub.ServiceLayer.GroupHub.GroupHubSubscriberService;
import edu.nus.iss.gdipsa.grouphub.ServiceLayer.GroupHub.GroupHubValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/group-hub")
public class GroupHubController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupHubRepository groupHubRepository;

    @Autowired
    private GroupHubPublisherService groupHubPublisherService;

    @Autowired
    private GroupHubSubscriberService groupHubSubscriberService;

    // register GroupHub validator.
    @Autowired
    private GroupHubValidator groupHubValidator;
    @InitBinder
    private void initGroupHubBinder(WebDataBinder binder) {
        binder.addValidators((Validator)  groupHubValidator);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createGroupHub(@RequestParam Integer userId, @Valid @RequestBody GroupHub inGroupHub, BindingResult bindingResult) {
        User user = userRepository.findById(userId).get();
        if(user == null) return new ResponseEntity<>("User id does not exist", HttpStatus.NOT_FOUND);

        // There are some errors in binding object.
        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach( error -> {
                System.out.println(error.getDefaultMessage());
            });

            String errMsg = "error in binding Application";
            return new ResponseEntity<>(errMsg, HttpStatus.EXPECTATION_FAILED);
        }

        // When object reaches this level, part ot it is already legal.
        GroupHub newGroupHub = new GroupHub();
        newGroupHub.setName(inGroupHub.getName());
        newGroupHub.setQuantity(inGroupHub.getQuantity());
        newGroupHub.setInitialQuantity(inGroupHub.getQuantity());
        newGroupHub.setLatitude(inGroupHub.getLatitude());
        newGroupHub.setLongitude(inGroupHub.getLongitude());
        newGroupHub.setEndTime(inGroupHub.getEndTime());
        newGroupHub.setPublishedBy(user);
        newGroupHub.setStartTime(LocalDate.now());
        groupHubRepository.save(newGroupHub);

        return new ResponseEntity<>(newGroupHub, HttpStatus.CREATED);
    }

    @GetMapping("/subscribe")
    public ResponseEntity<?> eventSubscribe(@RequestParam Integer userId, @RequestParam Long groupId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<GroupHub> optionalGroupHub = groupHubRepository.findById(groupId);
            if(optionalGroupHub.isPresent()) {
                GroupHub groupHub = optionalGroupHub.get();
                if(groupHub.getPublishedBy().getUserId() == userId) {
                    return new ResponseEntity<>("You can't register your own group!", HttpStatus.EXPECTATION_FAILED);
                }
                if(groupHub.getQuantity() <= 0) {
                    return new ResponseEntity<>("There is no head count!", HttpStatus.EXPECTATION_FAILED);
                }
                if(LocalDate.now().isAfter(groupHub.getEndTime())) {
                    return new ResponseEntity<>("Exceed the deadline.", HttpStatus.EXPECTATION_FAILED);
                }

                if(!groupHub.getHasUsers().contains(user)) {
                    groupHubSubscriberService.eventConfirm(userId, groupId);
                    return new ResponseEntity<>("Success!", HttpStatus.OK);
                }else {
                    return new ResponseEntity<>("The user is already in that group!", HttpStatus.EXPECTATION_FAILED);
                }
            }else {
                return new ResponseEntity<>("Group Hub does not exist!", HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<>("User does not exist!", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cancel")
    public ResponseEntity<?> eventCancel(@RequestParam Integer userId, @RequestParam Long groupId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<GroupHub> optionalGroupHub = groupHubRepository.findById(groupId);
            if(optionalGroupHub.isPresent()) {
                GroupHub groupHub = optionalGroupHub.get();
                if(LocalDate.now().isAfter(groupHub.getEndTime())) {
                    return new ResponseEntity<>("Exceed the deadline.", HttpStatus.EXPECTATION_FAILED);
                }
                if(groupHub.getHasUsers().contains(user)) {
                    groupHubSubscriberService.eventCancel(userId, groupId);
                    return new ResponseEntity<>("Success!", HttpStatus.OK);
                }else{
                    return new ResponseEntity<>("You are not in that group yet.", HttpStatus.NOT_FOUND);
                }
            }else{
                return new ResponseEntity<>("Group Hub does not exist!", HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<>("User does not exist!", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllGroupHub() {
        return new ResponseEntity<>(groupHubRepository.findAll(), HttpStatus.OK);
    }
}
